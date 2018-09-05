import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SoleSocietyLandingPage extends AbstractWebPage {
    private By closeBtn = new By.ByXPath("//button[@data-click='close']");
    private By newArrivalsLink = new By.ByXPath("//nav[@class='mobile-additional-menu']//a//span[.='New Arrivals']/..");
    private By accessoriesLink = new By.ByXPath("//nav[@class='mobile-additional-menu']//a//span[.='Accessories']/..");
    private By toggleMenu = new By.ByXPath("//span[@class='action nav-toggle']");
    private By signInLink = new By.ByXPath("//a[.='Sign In']");

    public SoleSocietyLandingPage(WebDriver dr) {
        super(dr);
    }

    public SoleSocietyLandingPage closeBestDeal() {
        System.out.println("Close best deal");
        try {
            waitForWebElementToBeDisplayed(closeBtn, 5).click();
        } catch (Exception ex) {
            System.out.println("Close best deal was not present.");
        }
        return this;
    }

    public SoleSocietyLandingPage toggleMenu() throws Exception {
        System.out.println("toggle Menu");
        findAndClickWebElementBy(toggleMenu);
        return this;
    }

    public void openSignInPage() throws Exception {
        System.out.println("open Sign In Page");
        findAndClickWebElementBy(signInLink);
    }

    public SoleSocietyLandingPage openNewArrivals() throws Exception {
        System.out.println("Open New Arrivals");
        if (isElementPresentAndDisplayed(newArrivalsLink)) {
            findAndClickWebElementBy(newArrivalsLink);

        }
        return this;
    }

    public SoleSocietyLandingPage openAccessories() throws Exception {
        System.out.println("Open Accessories");
        if (isElementPresentAndDisplayed(accessoriesLink)) {
            driver.findElements(newArrivalsLink).stream().findFirst().get().click();
        }
        return this;
    }

    @Override
    public boolean isTitleCorrect() {
        return false;
    }
}
