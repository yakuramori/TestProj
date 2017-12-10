import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestNgTest {
    private WebDriver driver;
    @Test
    public void testTitle() {
        driver.get("http://demo.guru99.com/selenium/guru99home/");
        String title = driver.getTitle();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(title.contains("Demo Guru99 Page"));
    }
    @BeforeTest
    public void beforeTest() {
        driver = new Driver("chrome").getWebDriver();
    }
    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
