package jdbc;

import dao.DAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import streaming.APIPathBuilder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by anihalani on 6/10/15.
 */
public class APIDataDumperTest {

    @Mock
    DAO dao;

    @Before
    public void setup() throws Exception {

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void getUrl(){
        APIDataDumper dataDumper = new APIDataDumper("https://api.conductor.com");

        APIPathBuilder pathBuilderMock = mock(APIPathBuilder.class);
        dataDumper.setPathBuilder(pathBuilderMock);
        pathBuilderMock.build("locations");

        verify(pathBuilderMock).build("locations");

        dataDumper.getLocationData();




        // .. validate the interaction with the mock
        // ... using an ArgumentCaptor
    }
}