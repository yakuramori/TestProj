import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

public abstract class AbstractWebPage {
    protected String pageTitle;
    protected WebDriver driver;
    protected int defaultTimeout = 30;

    public AbstractWebPage(WebDriver webDriver) {
        driver = webDriver;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    protected abstract boolean isTitleCorrect();

    public Screenshot getWebElementScreenshot(WebElement webElement) {
        new WebDriverWait(driver, defaultTimeout).until(ExpectedConditions.visibilityOf(webElement));
        Screenshot screenshot = new AShot().takeScreenshot(driver, webElement);
        return screenshot;
    }

    public Screenshot getWebElementScreenshot(By elementLocator) {
        WebElement webElement = (new WebDriverWait(driver, defaultTimeout))
                .until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
        Screenshot screenshot = new AShot().takeScreenshot(driver, webElement);
        return screenshot;
    }

    public Screenshot getPageScreenshot() {
        waitForPageToLoad(driver);
        sleepFor(2000);
        return new AShot().takeScreenshot(driver);
    }

    public void waitForPageToLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = driver1 -> ((JavascriptExecutor) driver1).executeScript("return document.readyState").equals("complete");
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(pageLoadCondition);
    }

    protected void waitForPageTitleIs(String pageTitle) {
        new WebDriverWait(driver, defaultTimeout).until(ExpectedConditions.titleIs(pageTitle));
    }

    protected void sleepFor(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected boolean isElementPresent(By byLocator) {
        try {
            WebElement webElement = (new WebDriverWait(driver, 1))
                    .until(ExpectedConditions.presenceOfElementLocated(byLocator));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected boolean isElementPresentAndDisplayed(By byLocator) {
        if (isElementPresent(byLocator)) {
            WebElement webElement = (new WebDriverWait(driver, 1))
                    .until(ExpectedConditions.visibilityOfElementLocated(byLocator));
            return webElement.isDisplayed();
        }
        return false;
    }
}
