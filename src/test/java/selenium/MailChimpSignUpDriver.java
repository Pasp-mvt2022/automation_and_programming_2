package selenium;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;

import org.openqa.selenium.chrome.*;
import org.openqa.selenium.edge.*;

import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class MailChimpSignUpDriver {

    WebDriver driver;
    public MailChimpSignUpDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\peras\\Selenium\\chromedriver.exe");
        System.setProperty("webdriver.edge.driver", "C:\\Users\\peras\\Selenium\\msedgedriver.exe");
    }

    @Test
    public void setDriver(String browser) {
        if (browser.equals("chrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(chromeOptions);
        } else {
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("--remote-allow-origins=*");
            driver = new EdgeDriver(edgeOptions);
        }
    }

    @Test
    public void openPage() {
        driver.get("https://login.mailchimp.com/signup/");
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    @Test
    public String setEmail() {
        WebElement emailField = driver.findElement(By.id("email"));
        String email = generateRandomEmail();
        emailField.sendKeys(email);
        return email;
    }

    private String generateRandomEmail() {
        Random random = new Random();
        return "peras" + (random.nextInt(1000)*1000) + "@mailchimp.com";
    }

    public String getEmail() {
        WebElement email = driver.findElement(By.id("email"));
        return email.getAttribute("value");
    }

    @Test
    public String setUsername(String random, String tooLong) {
        String userName;
        if (random.equals("random")) {
            userName = generateRandomUserName(tooLong);
        } else {
            userName = getFixedUserName();
        }
        WebElement userNameField = driver.findElement(By.name("username"));
        userNameField.click();
        userNameField.clear();
        userNameField.sendKeys(userName);
        return userName;
    }

    private String generateRandomUserName(String tooLong) {
        String baseUserName = "peras";
        if (tooLong.equals("true")) {
            baseUserName += RandomStringUtils.random(96, true, true);
        } else {
            baseUserName += RandomStringUtils.random(27, true, true);
        }
        
        return baseUserName;
    }

    private String getFixedUserName() {
        return "peras123456789";
    }

    public String getUsername() {
        WebElement userName = driver.findElement(By.name("username"));
        return userName.getAttribute("value");
    }

    @Test
    public void clickSignUp() {
        WebElement buttonSignup = driver.findElement(By.id("create-account-enabled"));
        WebElement marketingCheckbox = driver.findElement(By.name("marketing_newsletter"));
        marketingCheckbox.click();
        buttonSignup.click();
    }

    @Test
    public void setPassword(String password) {
        WebElement passWordField = driver.findElement(By.id("new_password"));
        passWordField.sendKeys(password);
    }

    public String getPassword() {
        WebElement passWordField = driver.findElement(By.id("new_password"));
        return passWordField.getAttribute("value");
    }

    @Test
    public String getCreated() {
        waitForCaptchaInputOrFailure();
        boolean isCreated = driver.getTitle().equals("Success | Mailchimp");
        return Boolean.toString(isCreated);
    }

    private void waitForCaptchaInputOrFailure() {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.or(
                ExpectedConditions.titleIs("Success | Mailchimp"),
                ExpectedConditions.presenceOfElementLocated(By.id("av-flash-errors")),
                ExpectedConditions.presenceOfElementLocated(By.className("invalid-error"))));
    }
}
