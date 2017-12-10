import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

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
    @Test
    public void testTitle2() {
        driver.get("http://demo.guru99.com/selenium/guru99home/");
        String title = driver.getTitle();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(title.equalsIgnoreCase("Demo Guru99"));
    }

    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }

    @BeforeClass
    @Parameters({"browser"})
    public void setUp(String browser) throws Exception {
        driver = new Driver(browser).getWebDriver();
    }
}
