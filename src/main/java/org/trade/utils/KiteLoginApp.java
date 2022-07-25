package org.trade.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.Objects;

@Slf4j
public class KiteLoginApp {
    private static final String CHROME_DRIVER_PATH = KiteLoginApp.class.getClassLoader().getResource("chromedriver.exe").getPath();

    public static String getRequestToken(String userName, String password, String totpKey) {

        String kiteLoginUrl = "https://kite.trade/connect/login?api_key=pv2830q1vbrhu1eu";

        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        WebDriver driver = new ChromeDriver();
        driver.get(kiteLoginUrl);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.findElement(By.id("userid")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.id("totp")).sendKeys(TOTPUtil.getTOTPCode(totpKey));
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String currentUrl = driver.getCurrentUrl();
        driver.quit();
        String tempString = "request_token=";
        return Objects.isNull(StringUtils.substringBetween(currentUrl, tempString, "&"))
                ? currentUrl.substring(currentUrl.indexOf(tempString) + tempString.length())
                : StringUtils.substringBetween(currentUrl, tempString, "&");
    }
}
