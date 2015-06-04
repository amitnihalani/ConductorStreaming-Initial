import com.fasterxml.jackson.databind.ObjectMapper;
import generated.Location;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import utils.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by anihalani on 6/4/15.
 */
public class main {
    public static void main(String[] args){
        HttpClient httpclient = HttpClientBuilder.create().build();
        // specify the host, protocol, and port
        HttpHost target = new HttpHost("api.cpnductor.com", 80, "https");
        //URLs
        final String baseUrl = "https://api.conductor.com";
        final String apiKey = "obwijyt9sjplrzwk8c9nrmb276tk5cyym9orp1l4";
        final String sharedSecret = "fgoser9zll8luygcahg5tdb8ngqur682z0mdk4gx";
        String endpoint = "/v3/locations";
        try {
            String serviceUrl = Util.generateRecoServiceUrl(baseUrl + endpoint, apiKey, sharedSecret);
            // specify the get request
            HttpGet getRequest = new HttpGet(serviceUrl);
            //https://api.conductor.com/v3/locations?apiKey=obwijyt9sjplrzwk8c9nrmb276tk5cyym9orp1l4&sig=46646717147fc49695fee5c9c3de2107
            System.out.println("executing request to " + target);
            //HttpResponse httpResponse = httpclient.execute(target, getRequest);
            HttpResponse httpResponse = httpclient.execute(getRequest);
            HttpEntity entity = httpResponse.getEntity();
            InputStream instream = entity.getContent();
            String result = convertStreamToString(instream);
            //Converting json text to java objects
            List<Location> location = null;
            ObjectMapper obMap = new ObjectMapper();
            int j=0;
            // location =  obMap.readValue(result, new TypeReference(List<Location>){});
            //  JSONObject myObject = new JSONObject(result);
            System.out.println("----------------------------------------");
            System.out.println(httpResponse.getStatusLine());
            Header[] headers = httpResponse.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                System.out.println(headers[i]);
            }
            System.out.println("----------------------------------------");
            if (entity != null) {
                System.out.println(EntityUtils.toString(entity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }

    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
