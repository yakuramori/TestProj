import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TestCrossBrowser {
    private WebDriver driver;
    private CrossBrowserTestingApi cbApi;

    @BeforeTest
    public void setup() throws MalformedURLException {
        String username = "yakuramori%40gmail.com";
        String authkey = "u70391857b26d23c";
        cbApi = new CrossBrowserTestingApi(username, authkey);
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("name", "Basic Test Example");
        caps.setCapability("build", "1.0");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("version", "68x64");
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("screenResolution", "1366x768");
        caps.setCapability("record_video", "true");
        caps.setCapability("takesScreenshot", true);

        driver = new RemoteWebDriver(new URL("http://" + username + ":" + authkey + "@hub.crossbrowsertesting.com:80/wd/hub"), caps);
        cbApi.setSessionId(((RemoteWebDriver) driver).getSessionId().toString());
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        System.out.println("Maximizing window");
        driver.manage().window().maximize();
    }

    @Test
    public void testSignIn() throws Exception {
        System.out.println("Loading Url");
        driver.get("https://www.solesociety.com/");
        SoleSocietyLandingPage page = new SoleSocietyLandingPage(driver);
        page.closeBestDeal();
        cbApi.takeSnapshot();
        page.openSignInPage();
        cbApi.takeSnapshot();
        Assert.assertEquals(driver.findElement(By.id("block-customer-login-heading")).getText(),
                "RETURNING CUSTOMERS");
        System.out.println("TestFinished");
        cbApi.setScore("pass");
    }

    @Test
    public void testSignInFail() throws Exception {
        System.out.println("Loading Url");
        driver.get("https://www.solesociety.com/");
        SoleSocietyLandingPage page = new SoleSocietyLandingPage(driver);
        page.closeBestDeal();
        cbApi.takeSnapshot();
        page.openSignInPage();
        cbApi.takeSnapshot();
        Assert.assertEquals(driver.findElement(By.id("block-customer-login-heading")).getText(),
                "Returning Customers");
        System.out.println("TestFinished");
        cbApi.setScore("fail");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
