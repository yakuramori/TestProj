import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.InvalidArgumentException;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CrossBrowserTestingApi {
    private String sessionId, username, authkey;
    private String apiUrl = "crossbrowsertesting.com/api/v3/selenium";

    public CrossBrowserTestingApi(String username, String authkey) {
        // your email, let's replace that character
        if (username.contains("@")) {
            username = username.replace("@", "%40");
        }
        this.username = username;
        this.authkey = authkey;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setScore(String score) {
        String url = "https://" + apiUrl + "/" + this.sessionId;
        String payload = "{\"action\": \"set_score\", \"score\": \"" + score + "\"}";
        makeRequest("PUT", url, payload);
    }

    public void takeSnapshot() {
        if (this.sessionId != null) {
            String url = "https://" + apiUrl + "/" + this.sessionId + "/snapshots";
            String payload = "{\"selenium_test_id\": \"" + this.sessionId + "\"}";
            makeRequest("POST", url, payload);
        } else {
            throw new InvalidArgumentException("No session id.");
        }
    }

    public void setDescription(String desc) {
        String url = "https://" + apiUrl + "/" + this.sessionId;
        String payload = "{\"action\": \"set_description\", \"description\": \"" + desc + "\"}";
        makeRequest("PUT", url, payload);
    }

    private void makeRequest(String requestMethod, String apiUrl, String payload) {
        URL url;
        String auth = "";

        if (username != null && authkey != null) {
            auth = "Basic " + Base64.encodeBase64String((username + ":" + authkey).getBytes());
        }
        try {
            url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", auth);
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            osw.write(payload);
            osw.flush();
            osw.close();
            conn.getResponseMessage();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public URL getHubUrl() {
        URL hubUrl = null;
        try {
            hubUrl = new URL("http://" + username + ":" + authkey + "@hub.crossbrowsertesting.com:80/wd/hub");
        } catch (MalformedURLException e) {
            System.out.println("Invalid HUB URL");
        }
        return hubUrl;
    }
}
