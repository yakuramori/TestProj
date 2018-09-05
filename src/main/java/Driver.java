import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
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
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addPreference("security.sandbox.content.level", "4");
            firefoxOptions.addPreference("webdriver.firefox.marionette", true);
            firefoxOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
            FirefoxProfile profile = new FirefoxProfile();
            profile.setAcceptUntrustedCertificates(true);
            profile.setPreference("browser.download.dir", "C:\\temp");
            profile.setPreference("browser.download.folderList", 2);
            profile.setPreference("browser.download.manager.showWhenStarting", false);
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf,application/x-pdf");
            profile.setPreference("pdfjs.disabled", true);
            firefoxOptions.setProfile(profile);
            webDriver = new FirefoxDriver(firefoxOptions);
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
        } else {
            throw new IllegalArgumentException("SetUp has FAILED.");
        }
        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
        webDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        return webDriver;
    }
}
