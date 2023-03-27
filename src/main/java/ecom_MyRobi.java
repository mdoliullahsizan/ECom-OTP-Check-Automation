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


public class ecom_MyRobi {

    public static AndroidDriver<MobileElement> driver;
    public static LocalDateTime otpStartTime;
    public static LocalDateTime otpEndTime;
    public static long otpResponseTimerb;

    public static WebDriverWait wait;
    public static long waitTime = 60;
    public static String rbotpMessageone = "";


    @BeforeTest
    public void setupRobi() throws MalformedURLException {

        DesiredCapabilities dcRb = new DesiredCapabilities();
        dcRb.setCapability("automationName", "Appium"); //Automation name
        dcRb.setCapability("deviceName", "Pixel"); //Your device name
        dcRb.setCapability("platformName", "Android"); //Your device OS name
        dcRb.setCapability("platformVersion", "12"); //Your OS version
        dcRb.setCapability("udid", "abcd1234"); //Your device ID [adb devices]
        dcRb.setCapability("appPackage", "net.omobio.robisc"); //App package name, can get it from APK Info app
        dcRb.setCapability("appActivity", "net.omobio.robisc.ui.splash.SplashActivity"); //App activity name, can get it from APK Info app

        dcRb.setCapability("autoGrantPermissions", true);
        dcRb.setCapability("noReset", false);
        dcRb.setCapability("autoAcceptAlerts", true);

        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), dcRb); //Appium server URL and Port
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


    //Robi
    @Test
    public void ecomrobi() throws InterruptedException, IOException {

        //Click Recharge
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Recharge']")));
            Thread.sleep(2000);
            driver.findElement(By.xpath("//android.widget.TextView[@text='Recharge']")).click();

            System.out.println("Clicked Recharge section.");

        } catch (Exception e) {
//            rbotpMessageone= "Recharge section not found within "+ waitTime + " seconds";
            rbotpMessageone= "E-Com OTP Response Time from MyRobi :  Failed";
            System.out.println(rbotpMessageone);
            telegramNotification.sendMessageToTelegramrb(rbotpMessageone);
            Assert.fail();

        }
        Thread.sleep(2000);
    }


    //Input Number and Amount

    @Test (dependsOnMethods = {"ecomrobi"})
    public void clickRecharge() throws InterruptedException, IOException {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@text='Enter A Valid Robi Number']")));
            Thread.sleep(2000);
            driver.findElement(By.xpath("//android.widget.EditText[@text='Enter A Valid Robi Number']")).click();

            Thread.sleep(1000);

            String cusNumber= "01234567890"; //Enter number

            driver.findElement(By.xpath("//android.widget.EditText[@text='Enter A Valid Robi Number']")).sendKeys(cusNumber);
            System.out.println("Number input");

            Thread.sleep(1000);

            String amount= "20"; //Enter recharge amount
            driver.findElement(By.xpath("//android.widget.EditText[@text='Enter Recharge Amount']")).sendKeys(amount);
            System.out.println("Amount input");

            //hide kewboard
            driver.hideKeyboard();

        } catch (Exception e) {

//            rbotpMessageone = "Number/amount input failed in MyRobi within "+ waitTime + " seconds";
            rbotpMessageone = "E-Com OTP Response Time from MyRobi :  Failed";
            System.out.println(rbotpMessageone);
            telegramNotification.sendMessageToTelegramrb(rbotpMessageone);
            Assert.fail();

        }
        Thread.sleep(1000);
    }


    //Click Make Payment button
    @Test (dependsOnMethods = {"clickRecharge"})
    public void makePaymentrb() throws IOException, InterruptedException {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("net.omobio.robisc:id/btn_make_payment")));
            Thread.sleep(2000);
            driver.findElement(By.id("net.omobio.robisc:id/btn_make_payment")).click();

            System.out.println("Click Make payment button");

        } catch (Exception e) {

//            rbotpMessageone = "Make payment button not clickable in MyRobi within"+ waitTime + " seconds";
            rbotpMessageone = "E-Com OTP Response Time from MyRobi :  Failed";
            System.out.println(rbotpMessageone);
            telegramNotification.sendMessageToTelegramrb(rbotpMessageone);
            Assert.fail();
        }
        Thread.sleep(2000);
    }


    //Select Payment method
    @Test (dependsOnMethods = {"makePaymentrb"})
    public void selectNagadrb() throws IOException, InterruptedException {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.view.ViewGroup[@bounds='[263,736][457,961]']")));
            Thread.sleep(2000);
            driver.findElement(By.xpath("//android.view.ViewGroup[@bounds='[263,736][457,961]']")).click();

            System.out.println("Click on Nagad payment");

        } catch (Exception e) {

//            rbotpMessageone = "Nagad payment not found in MyRobi within "+ waitTime + " seconds";
            rbotpMessageone = "E-Com OTP Response Time from MyRobi :  Failed";
            System.out.println(rbotpMessageone);
            telegramNotification.sendMessageToTelegramrb(rbotpMessageone);
            Assert.fail();
        }
        Thread.sleep(2000);
    }


    //Click Recharge button
    @Test(dependsOnMethods = {"selectNagadrb"})
    public void clickRechargeButtonrb() throws IOException, InterruptedException {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("net.omobio.robisc:id/buttonRecharge")));
            Thread.sleep(2000);
            driver.findElement(By.id("net.omobio.robisc:id/buttonRecharge")).click();

            System.out.println("Click Recharge button");

        } catch (Exception e) {

//            rbotpMessageone = "Recharge button not found in MyRobi within "+ waitTime + " seconds";
            rbotpMessageone = "E-Com OTP Response Time from MyRobi :  Failed";
            System.out.println(rbotpMessageone);
            telegramNotification.sendMessageToTelegramrb(rbotpMessageone);
            Assert.fail();
        }
        Thread.sleep(2000);
    }


    //Click Pay Now button
    @Test(dependsOnMethods = {"clickRechargeButtonrb"})
    public void clickPayNowrb() throws IOException, InterruptedException {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@text='PAY NOW']")));
//            driver.findElement(By.id("portwallet_mfs-submit")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//android.widget.Button[@text='PAY NOW']")).click();

            System.out.println("Click Pay Now button");

        } catch (Exception e) {

//            rbotpMessageone = "Pay Now button not clickable in MyRobi within "+ waitTime + " seconds";
            rbotpMessageone = "E-Com OTP Response Time from MyRobi :  Failed";
            System.out.println(rbotpMessageone);
            telegramNotification.sendMessageToTelegramrb(rbotpMessageone);
            Assert.fail();
        }
        Thread.sleep(2000);
    }


    //Enter Number
    @Test(dependsOnMethods = {"clickPayNowrb"})
    public void enterNumberrb() throws IOException, InterruptedException {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@index='1']")));
//            driver.findElement(By.xpath("//android.widget.EditText[@index='1']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//android.widget.EditText[@index='1']")).click();

            //Enter your number

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

            rbotpMessageone = "E-Com OTP Response Time from MyRobi:  Failed";
            System.out.println(rbotpMessageone);
            telegramNotification.sendMessageToTelegramgp(rbotpMessageone);
            Assert.fail();

        }
        Thread.sleep(2000);
    }


    //Click Proceed button
    @Test(dependsOnMethods = {"enterNumberrb"})
    public void clickProceedrb() throws IOException, InterruptedException {

        //clear Notification
        clearNotification();

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@index='15']")));
            Thread.sleep(2000);
            driver.findElement(By.xpath("//android.widget.Button[@index='15']")).click();

            System.out.println("Click proceed and Waiting for OTP.");

            otpStartTime = LocalDateTime.now();
            driver.openNotifications();

        } catch (Exception e) {

            rbotpMessageone = "E-Com OTP Response Time from MyRobi :  Failed";
            System.out.println(rbotpMessageone);
            telegramNotification.sendMessageToTelegramrb(rbotpMessageone);
            Assert.fail();
        }
    }

    //Check for OTP arrival in notification panel
    @Test(dependsOnMethods = {"clickProceedrb"})
    public void checkForOtpNotification() throws InterruptedException, IOException {

        long waitTimerobi = 120; //wait for 120 seconds for OTP
        WebDriverWait waitTime = new WebDriverWait(driver, waitTimerobi);

        try {
            String otpMsg = "Your One Time Password (OTP) for Nagad ECOM is";
//                MobileElement gpotpMessage = driver.findElement(By.xpath("//android.widget.TextView[@text='NAGAD']"));

            Boolean rbotpMessage = waitTime.withTimeout(Duration.ofSeconds(120)).until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//android.widget.TextView"), "NAGAD"));
//            wait.withTimeout(Duration.ofSeconds(30)).until(ExpectedConditions.textToBePresentInElementLocated(By.id("android:id/message_text"), otpMsg));


            if (rbotpMessage.equals(true)) {

                otpEndTime = LocalDateTime.now();
                otpResponseTimerb = Duration.between(otpStartTime, otpEndTime).toSeconds();
                rbotpMessageone = "E-Com OTP Response Time from MyRobi : " + otpResponseTimerb + " Seconds";
                System.out.println(rbotpMessageone);

                telegramNotification.sendMessageToTelegramrb(rbotpMessageone);
            } else {
                rbotpMessageone = "Otp did not arrived or delayed";
                System.out.println(rbotpMessageone);
                telegramNotification.sendMessageToTelegramrb(rbotpMessageone);
            }
        } catch (Exception e) {

            rbotpMessageone = "OTP not arrived from MyRobi within " + waitTimerobi + " Seconds";
            System.out.println(rbotpMessageone);
            telegramNotification.sendMessageToTelegramrb(rbotpMessageone);
//            Assert.fail();
        }

        Thread.sleep(1000);

        //Clear notification
        clearNotification();

        System.out.println("ROBI completed.");

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
