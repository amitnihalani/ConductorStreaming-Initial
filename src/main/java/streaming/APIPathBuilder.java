package streaming;

import utils.Util;

// import org.json.JSONObject;

/**
 * Created by anihalani on 5/31/15.
 */
public class APIPathBuilder {
    private String baseUrl;
    private String endpointParameter;
    private final String apiKey = "obwijyt9sjplrzwk8c9nrmb276tk5cyym9orp1l4";
    private final String sharedSecret = "fgoser9zll8luygcahg5tdb8ngqur682z0mdk4gx";

    private APIPathBuilder(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public APIPathBuilder(String baseUrl, String endpoint) {
        this.baseUrl = baseUrl;
        this.endpointParameter = endpoint;
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

    public String build() {
        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append("/v3/");
        builder.append(endpointParameter);
        return Util.generateRecoServiceUrl(builder.toString(), apiKey, sharedSecret);

    }

}
