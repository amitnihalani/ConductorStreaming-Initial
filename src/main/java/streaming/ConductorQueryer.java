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

    /**
     * Constructs a ConductorQueryer with an injected rxClient, useful for unit testing
     * @param rxClient
     */
    public ConductorQueryer(HttpClient<ByteBuf, ByteBuf> rxClient) {
        this.rxClient = rxClient;
        mapper = new ObjectMapper();
    }
//
//
//    /**
//     * Returns an observable stream of Location objects from the Foursquare endpoint.
//     *
//     * @param builder the query to construct
//     * @return an observable sequence of Location objects
//     */
//    public Observable<Location> getStream(final APIPathBuilder builder){
//        Observable createdObservable;
//        createdObservable = Observable.create(new Observable.OnSubscribe<Location>() {
//            @Override
//            public void call(final Subscriber<? super Location> subscriber) {
//                subscriber.onStart();
//
//                Location firstLocation = getLocationObject(builder, 0);
//                subscriber.onNext(firstLocation);
//
//                for (int i = 1; i < 10; i++) {
//                    subscriber.onNext(getLocationObject(builder, i));
//                }
//
//                subscriber.onCompleted();
//            }
//        });
//        return createdObservable;
//    }
//
//    /**
//     *
//     * @param builder the query to construct
//     * @param i the index to fetch
//     * @return the Location object representing the ith page
//     */
//    protected Location getLocationObject(final APIPathBuilder builder, final int i) {
//        return rxClient.submit(HttpClientRequest.createGet(builder.build()))
//                .flatMap(new Func1<HttpClientResponse<ByteBuf>, Observable<ByteBuf>>() {
//                    @Override
//                    public Observable<ByteBuf> call(HttpClientResponse<ByteBuf> response) {
//                        return response.getContent();
//                    }
//                })
//                .map(new Func1<ByteBuf, Location>() {
//                    @Override
//                    public Location call(ByteBuf data) {
//                        try {
//                            return mapper.readValue(data.toString(Charset.forName("UTF8")), Location.class);
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                })
//                .toBlocking()
//                .first();
//    }
//
////    public static void main(String[] args){
////        HttpClient httpclient = new Http
////        // specify the host, protocol, and port
////        HttpHost target = new HttpHost("api.cpnductor.com", 80, "https");
////
////        //URLs
////        final String baseUrl = "https://api.conductor.com";
////        final String apiKey = "spahamol3dutfzucdgxm4k0j2e721b1q3gi381hw";
////        final String sharedSecret = "18u41qj1x2o9j60qwnbwxkhfl2pho2lqnwj93tdh";
////        String endpoint = "/v3/locations";
////        try {
////
////            String serviceUrl = new APIPathBuilder("https://api.conductor.com", "locations").build();
////
////            // specify the get request
////            HttpGet getRequest = new HttpGet(serviceUrl);
////
////            System.out.println("executing request to " + target);
////
////            //HttpResponse httpResponse = httpclient.execute(target, getRequest);
////            HttpResponse httpResponse = httpclient.execute(getRequest);
////            HttpEntity entity = httpResponse.getEntity();
////            InputStream instream = entity.getContent();
////            String result = convertStreamToString(instream);
////
////            //Converting json text to java objects
////            List<Location> location = null;
////            ObjectMapper obMap = new ObjectMapper();
////            int j=0;
////
////        }
}
