package streaming;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by anihalani on 6/3/15.
 */
public class APIPathBuilderTest {

//    @Test(
//            expected = IllegalArgumentException.class
//    )
//    private void testBuildURL() throws  IllegalArgumentException{
//        APIPathBuilder path = new APIPathBuilder("/123", "qwerty");
//        path.build();
//    }

    @Test
    public void testBaseUrl(){
        APIPathBuilder path = new APIPathBuilder("api.conductor.com", "locations");
        String url = path.build();
        assertEquals(true, url.contains("api.conductor.com/v3/locations?apiKey=spahamol3dutfzucdgxm4k0j2e721b1q3gi381hw&sig="));
    }

}