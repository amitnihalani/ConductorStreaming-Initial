package test;

import junit.framework.TestCase;
import org.junit.Test;
import streaming.APIPathBuilder;

/**
 * Created by anihalani on 6/2/15.
 */
public class APIPathBuilderTest extends TestCase {

    @Test(
            expected = IllegalArgumentException.class
    )
    public void testLatLonOrNearRequiredFromBaseUrl() {
        APIPathBuilder path = new APIPathBuilder("/123", "qwerty");
        path.build();
    }


}