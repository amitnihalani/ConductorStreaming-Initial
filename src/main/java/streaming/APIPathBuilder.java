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
//        private final String apiKey = "obwijyt9sjplrzwk8c9nrmb276tk5cyym9orp1l";
//        private final String sharedSecret = "fgoser9zll8luygcahg5tdb8ngqur682z0mdk4gx";
         private final String apiKey = "obwijyt9sjplrzwk8c9nrmb276tk5cyym9orp1l4";
         private final String sharedSecret = "fgoser9zll8luygcahg5tdb8ngqur682z0mdk4gx";

        private APIPathBuilder(String baseUrl) {
            this.baseUrl = baseUrl;
            //urlParameters = new TreeMap<String, NameValuePair>();
        }

        public APIPathBuilder(String baseUrl, String endpoint) {
            this.baseUrl = baseUrl;
            this.endpointParameter = endpoint;
            //urlParameters = new TreeMap<String, NameValuePair>();
        }


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
