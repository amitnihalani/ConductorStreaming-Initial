package test;

        //        import com.google.common.collect.Lists;
//        import com.springapp.demo.model.FoursquarePathBuilder;
//        import com.springapp.demo.model.generated.Explore;
//        import com.springapp.demo.model.generated.Group;

public class ConductorQueryerTest {
//    private ConductorQueryer queryer;
//
//    private static HttpServer<ByteBuf, ByteBuf> server;
//    private static int port;
//    private static ObjectMapper objectMapper;
//
//    @BeforeClass
//    public static void init() {
//        objectMapper = new ObjectMapper();
//    }
//
//    @Before
//    public void setUp() throws Exception {
//        // Create an in-memory RxNetty server
//        HttpServerBuilder<ByteBuf, ByteBuf> builder
//                = new HttpServerBuilder<ByteBuf, ByteBuf>(new ServerBootstrap().group(new NioEventLoopGroup(10, new RxServerThreadFactory())), port, new RequestHandler<ByteBuf, ByteBuf>() {
//            private int i = 0;
//            @Override
//            public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
//                i++;
//                return response.writeStringAndFlush(getJsonContents(String.format("page%d.json", i)));
//            }
//        });
//        server = builder.enableWireLogging(LogLevel.ERROR).build();
//        server.start(); // Start accepting connections
//        port = server.getServerPort(); // store the port for safekeeping, so we can use our client against it
//
//        HttpClient<ByteBuf, ByteBuf> client = RxNetty.createHttpClient("localhost", port);
//        queryer =  new ConductorQueryer(client);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        server.shutdown(); // Kill after each test
//    }
//
//
//    @Test
//    public void filterStreamOnlyInNewYork() {
//        final Observable<Explore> stream = queryer.getStream(FoursquarePathBuilder.fromExplorerEndpoint().setNear("New York"));
//
//        final AtomicReference<List<Explore>> resultItems = new AtomicReference<List<Explore>>();
//
//        stream.filter(new Func1<Explore, Boolean>() {
//            public Boolean call(Explore explore) {
//                List<Group> list = explore.getResponse().getGroups();
//                String s = list.get(0).getItems().get(0).getVenue().getLocation().getCity();
//
//                return (s.contains("New York"));
//            }
//        }).toList().subscribe(new Subscriber<List<Explore>>() {
//            public void onCompleted() {
//                // Not yet implemented
//                assertNotNull(resultItems.get());
//                assertEquals(2, resultItems.get().size());
//                assertEquals("New York", resultItems.get().get(0).getResponse().getGroups().get(0).getItems().get(0).getVenue().getLocation().getCity());
//            }
//
//            public void onError(Throwable throwable) {
//                throw new RuntimeException("Test case not hit!");
//            }
//
//            public void onNext(List<Explore> explore) {
//                System.out.println("filterStreamOnlyInNewYork Test Data: " + explore);
//                resultItems.get().add((Explore) explore);
//            }
//        });
//    }
//
//
//
//    @Test
//    public void testGetStream() throws Exception {
//        Observable<Location> stream = queryer.getStream(new APIPathBuilder("","locations"));
//
//        List<Location> events = Lists.newArrayList(stream.toBlocking().toIterable());
//        assertEquals(10, events.size());
//
//        Explore event1 = events.get(0);
//        Explore event2 = events.get(1);
//        Explore event3 = events.get(2);
//
//        // Assert what's the same between each response
//        assertTrue(event1.getResponse().getHeaderFullLocation().equals(event2.getResponse().getHeaderFullLocation()) && event2.getResponse().getHeaderFullLocation().equals(event3.getResponse().getHeaderFullLocation()));
//        assertTrue(event1.getResponse().getHeaderLocation().equals(event2.getResponse().getHeaderLocation()) && event2.getResponse().getHeaderLocation().equals(event3.getResponse().getHeaderLocation()));
//        assertTrue(event1.getResponse().getHeaderLocationGranularity().equals(event2.getResponse().getHeaderLocationGranularity()) && event2.getResponse().getHeaderLocationGranularity().equals(event3.getResponse().getHeaderLocationGranularity()));
//        assertTrue(event1.getResponse().getSuggestedBounds().equals(event2.getResponse().getSuggestedBounds()) && event2.getResponse().getSuggestedBounds().equals(event3.getResponse().getSuggestedBounds()));
//        assertTrue(event1.getResponse().getSuggestedFilters().equals(event2.getResponse().getSuggestedFilters()) && event2.getResponse().getSuggestedFilters().equals(event3.getResponse().getSuggestedFilters()));
//        assertTrue(event1.getResponse().getSuggestedRadius().equals(event2.getResponse().getSuggestedRadius()) && event2.getResponse().getSuggestedRadius().equals(event3.getResponse().getSuggestedRadius()));
//        assertTrue(event1.getResponse().getTotalResults().equals(event2.getResponse().getTotalResults()) && event2.getResponse().getTotalResults().equals(event3.getResponse().getTotalResults()));
//        assertEquals(event1.getResponse().getTotalResults(), Long.valueOf(10));
//        assertTrue(event1.getNotifications().equals(event2.getNotifications()) && event2.getNotifications().equals(event3.getNotifications()));
//
//        // Assert the contents match and are in the correct order
//        for (int i = 0; i < 10; i++) {
//            assertJsonContentsEqual(events.get(i), String.format("page%d.json", i+1));
//        }
//    }


}
