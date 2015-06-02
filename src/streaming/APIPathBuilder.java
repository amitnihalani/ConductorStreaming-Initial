    package streaming;


    import utils.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

    //import org.json.JSONObject;

    /**
     * Created by anihalani on 5/31/15.
     */
    public class APIPathBuilder {
        private String baseUrl;
        private String endpointParameter;
        //URLs
        private final String apiKey = "spahamol3dutfzucdgxm4k0j2e721b1q3gi381hw";
        private final String sharedSecret = "18u41qj1x2o9j60qwnbwxkhfl2pho2lqnwj93tdh";

        private APIPathBuilder(String baseUrl) {
            this.baseUrl = baseUrl;
            //urlParameters = new TreeMap<String, NameValuePair>();
        }

        public APIPathBuilder(String baseUrl, String endpoint) {
            this.baseUrl = baseUrl;
            this.endpointParameter = endpoint;
            //urlParameters = new TreeMap<String, NameValuePair>();
        }


 /*      public static void main(String[] args) {
            HttpClient httpclient = HttpClientBuilder.create().build();
            // specify the host, protocol, and port
            HttpHost target = new HttpHost("api.cpnductor.com", 80, "https");

            //URLs
            final String baseUrl = "https://api.conductor.com";
            final String apiKey = "spahamol3dutfzucdgxm4k0j2e721b1q3gi381hw";
            final String sharedSecret = "18u41qj1x2o9j60qwnbwxkhfl2pho2lqnwj93tdh";
            String endpoint = "/v3/locations";
            try {

                String serviceUrl = Util.generateRecoServiceUrl(baseUrl+endpoint, apiKey, sharedSecret);

                // specify the get request
                HttpGet getRequest = new HttpGet(serviceUrl);

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

                try {
                    location =  obMap.readValue(result, new TypeReference<List<Location>>(){});
                }catch (IOException ex) {
                    ex.getMessage();
                }



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
        } */



        /**
         * Used to set the enpoint for API query.
         *
         * @param endpoint
         * @return
         */
        public APIPathBuilder setEndpoint(String endpoint) {
            this.endpointParameter = endpoint;
            return this;
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

        public String build() {
            StringBuilder builder = new StringBuilder(baseUrl);
            builder.append("/v3/");
            builder.append(endpointParameter);

            return Util.generateRecoServiceUrl(builder.toString(), apiKey, sharedSecret);
        }

    }
