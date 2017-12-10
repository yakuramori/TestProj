import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestNgTest {
    private WebDriver driver;

    @Test
    @Parameters({"browser"})
    public void testTitle(String browser) throws Exception {
        driver.get("http://demo.guru99.com/selenium/guru99home/");
        String title = driver.getTitle();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Browser is: " + browser);
        System.out.println("Page Title is: " + driver.getTitle());
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
