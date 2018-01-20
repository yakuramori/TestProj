import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

public abstract class AbstractWebPage {
    protected WebDriver driver;
    protected int defaultTimeout = 30;

    public AbstractWebPage(WebDriver webDriver) {
        driver = webDriver;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public abstract boolean isTitleCorrect();

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
        WebDriverWait wait = new WebDriverWait(driver, 2);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(byLocator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected WebElement waitForWebElementToBeDisplayed(By locator, int time) {
        WebElement webElement = (new WebDriverWait(driver, time))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        return webElement;
    }

    protected boolean isElementPresentAndDisplayed(By byLocator) {
        if (isElementPresent(byLocator)) {
            WebElement webElement = driver.findElement(byLocator);
            return webElement.isDisplayed();
        }
        return false;
    }

    protected void findAndClickWebElementBy(By locator) throws Exception {
        Actions action = new Actions(driver);
        try {
            waitForWebElementToBeDisplayed(locator, defaultTimeout);
            action.moveToElement(driver.findElement(locator)).pause(200).click(driver.findElement(locator)).perform();
        } catch (Exception e) {
            throw new Exception("Find and click action could not be performed for element: " + locator.toString());
        }
    }

    protected void findAndClickWebElementBy(WebElement webElement) throws Exception {
        Actions action = new Actions(driver);
        try {
            action.moveToElement(webElement).pause(200).build().perform();
            webElement.click();
        } catch (Exception e) {
            throw new Exception("Find and click action could not be performed for element: " + webElement.toString());
        }
    }
}
