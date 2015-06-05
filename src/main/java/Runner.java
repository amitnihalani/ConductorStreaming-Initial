import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import generated.Location;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import streaming.APIPathBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Created by anihalani on 6/4/15.
 */
public class Runner {

    public static void main(String[] args) {
        String url = new APIPathBuilder("https://api.conductor.com", "locations").build();
        InputStream instream = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        try {
                HttpClient httpClient = HttpClientBuilder.create().build();
                HttpGet getRequest = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(getRequest);
                HttpEntity entity = httpResponse.getEntity();
                instream = entity.getContent();
                JsonFactory jsonFactory = new JsonFactory();
                JsonParser jParser = jsonFactory.createJsonParser(instream);
                ObjectMapper objectMapper = new ObjectMapper();
                // Read the json objects from stream one at a time
                while (jParser.nextToken() != JsonToken.END_ARRAY) {
                    if (jParser.getCurrentToken() == JsonToken.START_ARRAY)
                        continue;
                    // Read a Location and write it to Database
                    Location l = objectMapper.readValue(jParser, Location.class);
                    l.writeToDatabase();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                instream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
