
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        try {
            call_me("VTD_Dashboard");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void call_me(String projectKey) throws Exception {
        String url = "http://14.225.5.246:9000/api/qualitygates/project_status?projectKey=" + projectKey;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String
        System.out.println(response.toString());
        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response.toString());
        System.out.println("result after Reading JSON Response");
        System.out.println(myResponse.getJSONObject("projectStatus").get("status"));

        String statusOfSonarqube=myResponse.getJSONObject("projectStatus").getString("status");

        if (statusOfSonarqube.equals("ERROR")) {
            throw new RuntimeException("The project hasn't passed the quality gate!");
        }


    }
}



