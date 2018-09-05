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
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestCrossBrowser {
    private WebDriver driver;
    private CrossBrowserTestingApi cbApi;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        Map<String, String> env = System.getenv();
        //env.entrySet().stream().forEach(en -> System.out.println("Param: " + en.getKey() + "::" + en.getValue()));

        String username = env.get("CBT_USERNAME");
        String authkey = env.get("CBT_AUTHKEY");
        System.out.println("U: " + username + "// P:" + authkey);
        cbApi = new CrossBrowserTestingApi(username, authkey);
        DesiredCapabilities caps = new DesiredCapabilities();
        System.out.println(env.get("CBT_BUILD_NAME") + env.get("CBT_BUILD_NUMBER") + env.get("CBT_BROWSERNAME") + env.get("CBT_VERSION") + env.get("CBT_PLATFORM") + env.get("CBT_SCREENRESOLUTION"));
        caps.setCapability("name", env.get("CBT_BUILD_NAME").trim());
        caps.setCapability("build", env.get("CBT_BUILD_NUMBER"));
        caps.setCapability("browserName", env.get("CBT_BROWSERNAME"));
        caps.setCapability("version", "68x64");
        caps.setCapability("platform", env.get("CBT_PLATFORM"));
        caps.setCapability("screenResolution", env.get("CBT_SCREENRESOLUTION"));
        caps.setCapability("record_video", "true");
        caps.setCapability("takesScreenshot", true);
        if (username.contains("@")) {
            username = username.replace("@", "%40");
        }
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
