import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;


public class ecom_MyGp {

    public AndroidDriver<MobileElement> driver;
    public LocalDateTime otpStartTime;
    public LocalDateTime otpEndTime;
    public long otpResponseTimegp;

    public WebDriverWait wait;
    public long waitTime = 60;

    public String gpotpMessageone = "";

    @BeforeTest
    public void setupGP() throws MalformedURLException {

        DesiredCapabilities dcGp = new DesiredCapabilities();
        dcGp.setCapability("automationName", "Appium"); //Automation name
        dcGp.setCapability("deviceName", "Pixel"); //Your device name
        dcGp.setCapability("platformName", "Android"); //Your device OS name
        dcGp.setCapability("platformVersion", "12"); //Your OS version
        dcGp.setCapability("udid", "abcd1234"); //Your device ID [adb devices]
        dcGp.setCapability("appPackage", "com.portonics.mygp"); //App package name, can get it from APK Info app
        dcGp.setCapability("appActivity", "com.portonics.mygp.ui.login.LoginActivity"); //App activity name, can get it from APK Info app

        dcGp.setCapability("autoGrantPermissions", true);
        dcGp.setCapability("noReset", true);
        dcGp.setCapability("autoAcceptAlerts", true);

        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), dcGp); //Appium server URL and Port
        wait = new WebDriverWait(driver, waitTime);

    }

    ecom_TelegramNotification telegramNotification = new ecom_TelegramNotification();


    //Clear all notifications
    public void clearNotification() throws InterruptedException {
        driver.openNotifications();
        Thread.sleep(2000);
        String notificationClearBtnPath = "com.android.systemui:id/dismiss_view";
        if (driver.findElements(By.id(notificationClearBtnPath)).size() > 0) {
            WebElement notificationClearBtn = driver.findElement(By.id("com.android.systemui:id/dismiss_view"));
            if (notificationClearBtn.isEnabled()) {
                notificationClearBtn.click();
                System.out.println("Clicked notification clear button");
            } else {
                System.out.println("No notifications to clear, going back");
                driver.pressKey(new KeyEvent(AndroidKey.BACK));
            }
        } else {
            System.out.println("No notifications to clear, going back");
            driver.pressKey(new KeyEvent(AndroidKey.BACK));
        }

    }


    //GP

    //Click sign in button
//    @Test
//    public void ecomgp() throws InterruptedException, IOException {
//
//        //clear Notification
//        clearNotification();
//
//        try {
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Sign in']"))).click();
////            driver.findElement(By.xpath("//android.widget.TextView[@text='Sign in']")).click();
//            System.out.println("Clicked Sign in button.");
//
//        } catch (Exception e) {
//            gpotpMessageone = "Unable to open MyGP app within " + waitTime + "seconds";
//            System.out.println(gpotpMessageone);
//            telegramNotification.sendMessageToTelegramgp(gpotpMessageone);
//
//        }
//        Thread.sleep(2000);
//    }

    //Click Pay Bill button
    @Test
    public void ecomgp() throws InterruptedException, IOException {

//        //clear Notification
//        clearNotification();

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.portonics.mygp:id/btnPostpaidPayBill")));
//            driver.findElement(By.xpath("//android.widget.TextView[@text='Sign in']")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("com.portonics.mygp:id/btnPostpaidPayBill")).click();

            System.out.println("Clicked Pay Bill button.");

        } catch (Exception e) {
//            gpotpMessageone = "Unable to click Pay Bill within " + waitTime + "seconds";
            gpotpMessageone = "E-Com OTP Response Time from MyGP:  Failed";
            System.out.println(gpotpMessageone);
            telegramNotification.sendMessageToTelegramgp(gpotpMessageone);
            Assert.fail();

        }
        Thread.sleep(2000);
    }


    //Click Recharge Button
//    @Test(dependsOnMethods = {"ecomgp"})
//    public void clickRecharge() throws InterruptedException, IOException {
//
//        try {
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.RelativeLayout[@index='3']"))).click();
//
////            driver.findElement(By.xpath("//android.widget.RelativeLayout[@index='3']")).click();
//            System.out.println("Clicked recharge button.");
//
//        } catch (Exception e) {
//
//            gpotpMessageone = "Unable to load Recharge page for MyGP " + waitTime + "seconds";
//            System.out.println(gpotpMessageone);
//            telegramNotification.sendMessageToTelegramgp(gpotpMessageone);
//            Assert.fail();
//
//        }
//        Thread.sleep(2000);
//    }

    //Click Continue button
    @Test(dependsOnMethods = {"ecomgp"})
    public void clickContinuegp() throws InterruptedException, IOException {

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@text='Continue']")));

//            driver.findElement(By.xpath("//android.widget.Button[@text='Continue']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//android.widget.Button[@text='Continue']")).click();

            System.out.println("Clicked continue button.");

        } catch (Exception e) {

//            gpotpMessageone = "Continue button not found for MyGP " + waitTime + "seconds";
            gpotpMessageone = "E-Com OTP Response Time from MyGP:  Failed";
            System.out.println(gpotpMessageone);
            telegramNotification.sendMessageToTelegramgp(gpotpMessageone);
            Assert.fail();

        }
        Thread.sleep(2000);
    }

    //Click on Nagad
    @Test(dependsOnMethods = {"clickContinuegp"})
    public void clickNagadgp() throws InterruptedException, IOException {

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.view.View[@index='5']")));

//            driver.findElement(By.xpath("//android.view.View[@index='5']")).click();

            Thread.sleep(2000);
            driver.findElement(By.xpath("//android.view.View[@index='5']")).click();

            System.out.println("Clicked Nagad button.");

        } catch (Exception e) {

            gpotpMessageone = "E-Com OTP Response Time from MyGP:  Failed";
            System.out.println(gpotpMessageone);
            telegramNotification.sendMessageToTelegramgp(gpotpMessageone);
            Assert.fail();

        }
        Thread.sleep(2000);
    }

    //Click Continue button in Nagad page
    @Test(dependsOnMethods = {"clickNagadgp"})
    public void clickContinueNagadgp() throws InterruptedException, IOException {

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@text='Continue']")));
//            driver.findElement(By.xpath("//android.widget.Button[@text='Continue']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//android.widget.Button[@text='Continue']")).click();

            System.out.println("Clicked continue button.");

        } catch (Exception e) {

            gpotpMessageone = "E-Com OTP Response Time from MyGP:  Failed";
            System.out.println(gpotpMessageone);
            telegramNotification.sendMessageToTelegramgp(gpotpMessageone);
            Assert.fail();

        }
        Thread.sleep(2000);
    }

    //Enter Nagad number
    @Test(dependsOnMethods = {"clickContinueNagadgp"})
    public void enterNumbergp() throws InterruptedException, IOException {

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@index='1']")));
//            driver.findElement(By.xpath("//android.widget.EditText[@index='1']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//android.widget.EditText[@index='1']")).click();

            //enter your number
            driver.pressKey((new KeyEvent(AndroidKey.DIGIT_0)));
            Thread.sleep(1000);
            driver.pressKey((new KeyEvent(AndroidKey.DIGIT_1)));
            Thread.sleep(500);
            driver.pressKey((new KeyEvent(AndroidKey.DIGIT_2)));
            Thread.sleep(500);
            driver.pressKey((new KeyEvent(AndroidKey.DIGIT_3)));
            Thread.sleep(500);
            driver.pressKey((new KeyEvent(AndroidKey.DIGIT_4)));
            Thread.sleep(500);
            driver.pressKey((new KeyEvent(AndroidKey.DIGIT_5)));
            Thread.sleep(500);
            driver.pressKey((new KeyEvent(AndroidKey.DIGIT_6)));
            Thread.sleep(500);
            driver.pressKey((new KeyEvent(AndroidKey.DIGIT_7)));
            Thread.sleep(500);
            driver.pressKey((new KeyEvent(AndroidKey.DIGIT_8)));
            Thread.sleep(500);
            driver.pressKey((new KeyEvent(AndroidKey.DIGIT_9)));
            Thread.sleep(500);
            driver.pressKey((new KeyEvent(AndroidKey.DIGIT_0)));

            System.out.println("Input nagad wallet number.");

            //hide keyboard
            driver.hideKeyboard();
            Thread.sleep(1000);

        } catch (Exception e) {

            gpotpMessageone = "E-Com OTP Response Time from MyGP:  Failed";
            System.out.println(gpotpMessageone);
            telegramNotification.sendMessageToTelegramgp(gpotpMessageone);
            Assert.fail();

        }
        Thread.sleep(2000);
    }


    //Click proceed button
    @Test(dependsOnMethods = {"enterNumbergp"})
    public void clickProceedgp() throws IOException, InterruptedException {

        //clear Notification
        clearNotification();

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@index='15']")));
//            driver.findElement(By.xpath("//android.widget.Button[@index='15']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//android.widget.Button[@index='15']")).click();

            System.out.println("Click proceed and Waiting for OTP.");

            otpStartTime = LocalDateTime.now();
            driver.openNotifications();

        } catch (Exception e) {

            gpotpMessageone = "E-Com OTP Response Time from MyGP:  Failed";
            System.out.println(gpotpMessageone);
            telegramNotification.sendMessageToTelegramgp(gpotpMessageone);
            Assert.fail();
        }
    }


    //Check for OTP arrival in notification panel
    @Test(dependsOnMethods = {"clickProceedgp"})
    public void checkForOtpNotification() throws InterruptedException, IOException {

        long waitTimegp = 120; //wait for 120 seconds for OTP
        WebDriverWait waitgp = new WebDriverWait(driver, waitTimegp);

        try {
            String otpMsg = "Your One Time Password (OTP) for Nagad ECOM is";
//                MobileElement gpotpMessage = driver.findElement(By.xpath("//android.widget.TextView[@text='NAGAD']"));

            Boolean gpotpMessage = waitgp.withTimeout(Duration.ofSeconds(120)).until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//android.widget.TextView"), "NAGAD"));
//            wait.withTimeout(Duration.ofSeconds(30)).until(ExpectedConditions.textToBePresentInElementLocated(By.id("android:id/message_text"), otpMsg));


            if (gpotpMessage.equals(true)) {

                otpEndTime = LocalDateTime.now();
                otpResponseTimegp = Duration.between(otpStartTime, otpEndTime).toSeconds();
                gpotpMessageone = "E-Com OTP Response Time from MyGP : " + otpResponseTimegp + " Seconds";
                System.out.println(gpotpMessageone);

                telegramNotification.sendMessageToTelegramgp(gpotpMessageone);
            } else {
                gpotpMessageone = "Otp did not arrived or delayed";
                System.out.println(gpotpMessageone);
                telegramNotification.sendMessageToTelegramgp(gpotpMessageone);
            }
        } catch (Exception e) {

            gpotpMessageone = "OTP not arrived from MyGP within " + waitTimegp + "seconds";
            System.out.println(gpotpMessageone);
            telegramNotification.sendMessageToTelegramgp(gpotpMessageone);
//            Assert.fail();
        }

        Thread.sleep(1000);

        //Clear notification
        clearNotification();

        System.out.println("GP completed");

        //press Home button
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
    }


    //    teardowngp
    @AfterClass
    public void teardown() {
        //close the app
        driver.quit();
    }

}

