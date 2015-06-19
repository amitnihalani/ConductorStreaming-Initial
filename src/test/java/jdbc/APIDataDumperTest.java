package jdbc;

import dao.DAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import streaming.APIPathBuilder;
import streaming.StreamBuilder;

import java.io.InputStream;

import static org.mockito.Mockito.*;

/**
 * Created by anihalani on 6/10/15.
 */
public class APIDataDumperTest {

    @Mock
    DAO dao;
    @Mock
    StreamBuilder strmBuilder;
    @Mock
    InputStream ipStream;
    @Mock
    APIDataDumper dataDumper;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        dataDumper.setDao(dao);
        dataDumper.setStreamBuilder(strmBuilder);

        //create method stub
        when(strmBuilder.buildInStream(anyString())).thenReturn(ipStream);
    }

    @Test
    public void verifyInteractionWithPathBuilder(){
        APIPathBuilder pathBuilderMock = mock(APIPathBuilder.class);
        dataDumper.setPathBuilder(pathBuilderMock);
        pathBuilderMock.build("locations");
        verify(pathBuilderMock).build("locations");

    }

    @Test
    public void verifyInteractionsWithDataMethods(){
        dataDumper.getLocationData();
        dataDumper.getDeviceData();
        dataDumper.getRankSourceData();
        dataDumper.getWebPropertiesData();
        dataDumper.getWebPropertyRankReport();


        verify(dataDumper, times(1)).getLocationData();
        verify(dataDumper, times(1)).getDeviceData();
        verify(dataDumper, times(1)).getRankSourceData();
        verify(dataDumper, times(1)).getWebPropertiesData();
        verify(dataDumper, times(1)).getWebPropertyRankReport();


    }


}