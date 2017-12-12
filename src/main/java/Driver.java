import org.openqa.selenium.Dimension;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;


public class Driver {
    private WebDriver webDriver;
    private String browser;

    public Driver(String browser){
        this.browser = browser;
    }

    public WebDriver getWebDriver() {
        if (browser.equalsIgnoreCase("firefox")) {
            //String username = System.getProperty("user.name");
            System.setProperty("webdriver.gecko.driver", TestConfig.getPropertyValue("firefox.driver"));
            DesiredCapabilities cap = DesiredCapabilities.firefox();
            cap.setCapability("security.sandbox.content.level", "4");
            FirefoxProfile profile = new FirefoxProfile();
            profile.setAcceptUntrustedCertificates(true);
            profile.setPreference("webdriver.firefox.marionette", true);
            //profile.setPreference("browser.download.defaultFolder", "C:\\Users\\" + username + "\\Downloads");
            //profile.setPreference("browser.download.folderList", 1);
            cap.setJavascriptEnabled(true);
            webDriver = new FirefoxDriver(new FirefoxOptions(cap));
            webDriver.manage().window().maximize();
        } else if (browser.equalsIgnoreCase("ie")) {
            DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
            System.setProperty("webdriver.ie.driver", TestConfig.getPropertyValue("ie.driver"));
            caps.setCapability(InternetExplorerDriver.SILENT, true);
            caps.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, UnexpectedAlertBehaviour.ACCEPT);
            caps.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
            caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
            caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
            caps.setCapability(InternetExplorerDriver.BROWSER_ATTACH_TIMEOUT, 15000);
            caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
            caps.setJavascriptEnabled(true);
            webDriver = new InternetExplorerDriver(new InternetExplorerOptions(caps));
        } else if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", TestConfig.getPropertyValue("chrome.driver"));
            ChromeOptions optionsChrome = new ChromeOptions();
            //optionsChrome.setBinary(new File(getPropertyValue("chrome.driver")));
            optionsChrome.addArguments("--start-maximized");
            optionsChrome.addArguments("test-type");
            webDriver = new ChromeDriver(optionsChrome);
        } else if (browser.equalsIgnoreCase("ghost")){
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                    TestConfig.getPropertyValue("ghost.driver"));
            caps.setCapability("takesScreenshot", true);
            caps.setJavascriptEnabled(true);
            caps.setCapability("unhandledPromptBehavior", "accept");
            caps.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Windows NT 5.1; rv:22.0) Gecko/20100101 Firefox/52.0");
            webDriver = new PhantomJSDriver(caps);
            webDriver.manage().window().setSize(new Dimension(1920, 1080));
        } else {
            throw new IllegalArgumentException("SetUp has FAILED.");
        }
        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        webDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        return webDriver;
    }
}
