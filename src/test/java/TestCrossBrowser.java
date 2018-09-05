import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TestCrossBrowser {
    private WebDriver driver;
    private CrossBrowserTestingApi cbApi;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        String username = "yakuramori%40gmail.com";
        String authkey = "u70391857b26d23c";
        cbApi = new CrossBrowserTestingApi(username, authkey);
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("name", "Basic Test Example");
        caps.setCapability("build", "1.2");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("version", "68x64");
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("screenResolution", "1366x768");
        caps.setCapability("record_video", "true");
        caps.setCapability("takesScreenshot", true);

        driver = new RemoteWebDriver(new URL("http://" + username + ":" + authkey + "@hub.crossbrowsertesting.com:80/wd/hub"), caps);
        String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
        System.out.println("SessionId: " + sessionId);
        cbApi.setSessionId(sessionId);
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
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        System.out.println("TestFinished");
        if (result.getStatus() == ITestResult.SUCCESS) {
            cbApi.setScore("pass");
        } else {
            cbApi.setScore("fail");
        }
        driver.quit();
    }
}
