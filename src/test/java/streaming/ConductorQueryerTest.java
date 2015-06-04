package streaming;


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
import sql.JDBCConnection;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//import io.reactivex.netty.protocol.http.client.HttpClient;
//import io.reactivex.netty.protocol.http.client.HttpClientPipelineConfigurator;


public class ConductorQueryerTest {
    private ConductorQueryer queryer;

    public static  void main(String[] args){
     /*   HttpClient<ByteBuf, ByteBuf> httpClient = RxNetty.<ByteBuf, ByteBuf>newHttpClientBuilder("api.conductor.com", 80)
                .pipelineConfigurator(new HttpClientPipelineConfigurator<ByteBuf, ByteBuf>())
                .withSslEngineFactory(DefaultFactories.trustAll())
                .build();*/

        String uri = new APIPathBuilder("https://api.conductor.com", "locations").build();
        URL url;

        InputStream instream = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        try {

            HttpClient httpClient1 = HttpClientBuilder.create().build();
            HttpGet getRequest = new HttpGet(uri);
            HttpResponse httpResponse = httpClient1.execute(getRequest);
            HttpEntity entity = httpResponse.getEntity();
             instream = entity.getContent();

            // url = new URL(uri);
            JsonFactory jsonFactory = new JsonFactory();
            JsonParser jParser = jsonFactory.createJsonParser(instream);
            ObjectMapper objectMapper = new ObjectMapper();

            List<Location> locations = new ArrayList<Location>();

            while(jParser.nextToken() != JsonToken.END_ARRAY){

                    //locations.add(objectMapper.readValue(jParser, Location.class));
                JDBCConnection.WriteToTable("locale", objectMapper.readValue(jParser, Location.class));
                    if(locations.size() == 460){
                        System.out.println("");
                    }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                instream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


//        final Observable<HttpClientResponse<ByteBuf>> responses =
//                httpClient.submit(HttpClientRequest.createGet(uri.replace("https://api.conductor.com", "")));
//
//        final Observable byteBufs =
//                responses.flatMap(AbstractHttpContentHolder::getContent);
//
//        final Observable chunks = byteBufs.map(content -> {
//            return content.toString().getBytes(StandardCharsets.UTF_8).toString();
//        });
//
//        Observable<String> jsonChunks;
//        jsonChunks = null;




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
