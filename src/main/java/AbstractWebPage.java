import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

public abstract class AbstractWebPage {
    public String pageTitle;
    protected WebDriver driver;

    public AbstractWebPage(WebDriver webDriver) {
        driver = webDriver;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    protected abstract boolean isTitleCorrect();

    public Screenshot getWebElementScreenshot(WebElement webElement) {
        Screenshot screenshot = new AShot().takeScreenshot(driver, webElement);
        return screenshot;
    }

    public Screenshot getWebElementScreenshot(By elementLocator) {
        Screenshot screenshot = new AShot().takeScreenshot(driver, driver.findElement(elementLocator));
        return screenshot;
    }

    public Screenshot getPageScreenshot() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AShot().takeScreenshot(driver);
    }
}
