package com.example.numequationapp;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;
import java.util.List;

import io.appium.java_client.android.AndroidDriver;

public class RegressionAppiumTests {
    public  AndroidDriver androidDriver;
    public  WebDriverWait wait;
    double acc1 = 0.001;
    String equationhistory = "sin(x) + x^4";

    By equationField, solveButton, accuracyField, radioBisection, radioNewton, a_bisection, b_bisection, dfField, x0Field, resultField;
    By historyMenu;
    By firstElementHistory;
    @Before
    public void setup(){
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "meizu X8");
        caps.setCapability("udid", "852HLEU423UQR");
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "8.1.0");
        caps.setCapability("appPackage", "com.example.numequationapp");
        caps.setCapability("appActivity", "com.example.numequationapp.MainActivity");
        caps.setCapability("noReset", "true");

        try {
            androidDriver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), caps);
        } catch (Exception e){}

        equationField = By.id("com.example.numequationapp:id/equationField");
        accuracyField = By.id("com.example.numequationapp:id/accuracyField");
        radioBisection = By.id("com.example.numequationapp:id/bisection_button");
        radioNewton = By.id("com.example.numequationapp:id/newton_button");
        a_bisection = By.id("com.example.numequationapp:id/bisect_a_par");
        b_bisection = By.id("com.example.numequationapp:id/bisect_b_par");
        dfField = By.id("com.example.numequationapp:id/newton_df");
        x0Field = By.id("com.example.numequationapp:id/newton_x0");
        solveButton = By.id("com.example.numequationapp:id/solveButton");
        resultField = By.id("com.example.numequationapp:id/result_root");
        historyMenu = By.id("com.example.numequationapp:id/history_button");
        firstElementHistory = By.id("com.example.numequationapp:id/equation_text");

        wait = new WebDriverWait(androidDriver, Duration.ofSeconds(10));
    }

    @Test
    public void testBisection(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(equationField));
        WebElement equation1 = androidDriver.findElement(equationField);
        WebElement accuracy1 = androidDriver.findElement(accuracyField);
        WebElement bisect_button = androidDriver.findElement(radioBisection);
        String eq_str = "4*x + 2";
        equation1.clear();
        equation1.sendKeys(eq_str);
        accuracy1.clear();
        accuracy1.sendKeys(String.valueOf(acc1));
        bisect_button.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(a_bisection));
        WebElement a1 = androidDriver.findElement(a_bisection);
        WebElement b1 = androidDriver.findElement(b_bisection);
        a1.clear();
        b1.clear();
        a1.sendKeys(" -8");
        b1.sendKeys("10");
        WebElement button = androidDriver.findElement(solveButton);
        button.click();
        WebElement result1 = androidDriver.findElement(resultField);
        double actual = Double.valueOf(result1.getText().substring(4));

        double expected = -0.5;
        boolean res = false;

        if(Math.abs(expected - actual) < acc1)
            res = true;

        Assert.assertTrue(res);
    }

    @Test
    public void testNewton(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(equationField));
        WebElement equation1 = androidDriver.findElement(equationField);
        WebElement accuracy1 = androidDriver.findElement(accuracyField);
        equation1.clear();
        String eq_str = "x^2 - 9";
        equation1.sendKeys(eq_str);
        accuracy1.clear();
        accuracy1.sendKeys(String.valueOf(acc1));
        WebElement newton_button = androidDriver.findElement(radioNewton);
        newton_button.click();
        WebElement df = androidDriver.findElement(dfField);
        String derivative = "2 * x";
        df.clear();
        df.sendKeys(derivative);
        WebElement x0 = androidDriver.findElement(x0Field);
        x0.clear();
        x0.sendKeys("100");
        double expected = 3.0;
        WebElement button = androidDriver.findElement(solveButton);
        button.click();
        WebElement result1 = androidDriver.findElement(resultField);
        double actual = Double.valueOf(result1.getText().substring(4));

        boolean result = false;

        if(Math.abs(expected - actual) < acc1)
            result = true;

        Assert.assertTrue(result);
    }

    @Test
    public void testHistory(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(equationField));
        WebElement equation1 = androidDriver.findElement(equationField);
        WebElement accuracy1 = androidDriver.findElement(accuracyField);
        WebElement newton_button = androidDriver.findElement(radioNewton);
        equation1.clear();
        equation1.sendKeys(equationhistory);
        accuracy1.clear();
        accuracy1.sendKeys(String.valueOf(acc1));
        newton_button.click();
        WebElement df = androidDriver.findElement(dfField);
        String derivative = "cos(x) + 4*x^3";
        df.clear();
        df.sendKeys(derivative);
        WebElement x0 = androidDriver.findElement(x0Field);
        x0.clear();
        x0.sendKeys("150");
        WebElement button = androidDriver.findElement(solveButton);
        button.click();

        WebElement hist = androidDriver.findElement(historyMenu);
        hist.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(firstElementHistory));
        List<WebElement> history = androidDriver.findElements(firstElementHistory);

        String latest_text = history.get(0).getText();

        Assert.assertEquals(equationhistory, latest_text);
    }

}
