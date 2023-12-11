package com.mycompany.mavenproject2;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Mavenproject2 {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @Test
    public void verifyLoginPageTitle() {
        driver.get("http://localhost/Voting-Management-System/login.php");
        String title = driver.getTitle();
        Assert.assertEquals(title, "Admin | Voting System", "Title does not match");
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        login("Admin", "123");
        Assert.assertTrue(driver.getCurrentUrl().contains("http://localhost/Voting-Management-System/login.php"),
                "Invalid credentials login failed");
    }

    @Test(dependsOnMethods = "testLoginWithInvalidCredentials")
    public void testLoginWithInvalidCredentials2() {
        login("Sfddsn", "password");
        Assert.assertTrue(driver.getCurrentUrl().contains("http://localhost/Voting-Management-System/login.php"),
                "Invalid credentials login failed");
    }

    @Test(dependsOnMethods = "testLoginWithInvalidCredentials2")
    public void testLoginWithValidCredentials() {
        login("Admin", "password");
        //Assert.assertTrue(driver.getCurrentUrl().contains("http://localhost/Voting-Management-System/index.php?page=home"),
        //        "Valid credentials login failed");
    }

    @Test(dependsOnMethods = "testLoginWithValidCredentials")
    public void testCategoryNavigation() {
        navigateToCategoryPage();
        //Assert.assertTrue(driver.getCurrentUrl().contains("http://localhost/Voting-Management-System/index.php?page=categories"),
        //        "Category navigation failed");
    }

    @Test(dependsOnMethods = "testCategoryNavigation")
    public void testUserNavigation() {
        navigateToUserPage();
        //Assert.assertTrue(driver.getCurrentUrl().contains("http://localhost/Voting-Management-System/index.php?page=users"),
        //        "User navigation failed");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void login(String username, String password) {
        driver.get("http://localhost/Voting-Management-System/login.php");
        WebElement usernameInput = driver.findElement(By.name("username"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(),'Login')]"));

        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
    }

    private void navigateToCategoryPage() {
        WebElement categoryButton = driver.findElement(By.className("nav-item nav-categories"));
        categoryButton.click();
    }

    private void navigateToUserPage() {
        WebElement userButton = driver.findElement(By.className("nav-item nav-users"));
        userButton.click();
    }

    public static void main(String[] args) {
        Mavenproject2 test = new Mavenproject2();

        test.setUp();
        
        test.verifyLoginPageTitle();
        test.testLoginWithInvalidCredentials();
        test.testLoginWithInvalidCredentials2();
        test.testLoginWithValidCredentials();
        test.navigateToCategoryPage();
        test.navigateToUserPage();
        
        test.tearDown();
    }
}
