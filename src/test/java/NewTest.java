import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.junit.Test;
import org.junit.Before;


public class NewTest {
    private WebDriver driver;
    @Test
    public void testEasy() {
        driver.get("http://demo.guru99.com/selenium/guru99home/");
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("Demo Guru99 Page"));
    }
    @Before
    public void beforeTest() {
        driver = new Driver("firefox").getWebDriver();
    }
    @After
    public void afterTest() {
        driver.quit();
    }
}
