package streaming;


import io.reactivex.netty.protocol.http.client.HttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import io.netty.buffer.ByteBuf;

/**
 * Created by anihalani on 6/1/15.
 */
public class ConductorQueryer {

    public static final String CONDUCTOR_HOST = "api.conductor.com";
    public static final int CONDUCTOR_PORT = 80;

    private final ObjectMapper mapper;
    private final HttpClient<ByteBuf, ByteBuf> rxClient;

    /**
     * Constructs a ConductorQueryer with a default http client, which should fulfil non-testing
     * use cases
     */
    public ConductorQueryer() {
        this.rxClient = null;
        mapper = new ObjectMapper();
      //  this(RxNetty.newHttpClientBuilder(CONDUCTOR_HOST, CONDUCTOR_PORT));
//        this(RxNetty.<~>newHttpClientBuilder(CONDUCTOR_HOST, CONDUCTOR_PORT)
//                .withSslEngineFactory(DefaultFactories.trustAll())
//                .enableWireLogging(LogLevel.DEBUG)
//                .build());
    }


}
