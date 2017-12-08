import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {
    private TestConfig(){}
    public static String getPropertyValue(String property){
        Properties prop = new Properties();
        InputStream input = null;

        try {
            String propFileName = "config.properties";
            input = new FileInputStream("src\\main\\resources\\"+propFileName);
            if(null == input){
                System.out.println("Sorry, unable to find " + propFileName);
                return null;
            }
            prop.load(input);
            return prop.getProperty(property);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
