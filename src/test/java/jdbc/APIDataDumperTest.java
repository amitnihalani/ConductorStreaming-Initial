package jdbc;

import beans.*;
import dao.DAO;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import streaming.APIPathBuilder;
import streaming.StreamBuilder;

import java.io.InputStream;

import static org.mockito.Matchers.anyString;
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
    APIPathBuilder pathBuilderMock;

    APIDataDumper dataDumper;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        dataDumper = new APIDataDumper("https://api.conductor.com");
        dataDumper.setDao(dao);
        dataDumper.setStreamBuilder(strmBuilder);

    }

    /**
     * Verify interaction with Path Builder
     */
    @Test
    public void verifyInteractionWithPathBuilder() {
        dataDumper.setPathBuilder(pathBuilderMock);
        pathBuilderMock.build("locations");
        verify(pathBuilderMock).build("locations");
    }

    /**
     * Verify interactions with getLocation method
     * @throws Exception - SQL Exception while writing to database
     */
    @Test
    public void verifyInteractionsWithGetLocationData() throws Exception {
        // create method stub
        when(strmBuilder.buildInStream(anyString())).thenReturn(
                IOUtils.toInputStream("[{\n" + "    \"locationId\": \"1\",\n"
                        + "    \"description\": \"United States\"\n" + "}, {\n" + "    \"locationId\": \"2\",\n"
                        + "    \"description\": \"Canada\"\n" + "}]"));
        dataDumper.getLocationData();
        ArgumentCaptor<Location> arg = ArgumentCaptor.forClass(Location.class);
        verify(dao, times(2)).writeToDatabase(arg.capture());
        Assert.assertEquals(arg.getAllValues().get(0).getLocationId(), 1);
        Assert.assertEquals(arg.getAllValues().get(1).getLocationId(), 2);
    }

    /**
     * Verify interactions with getDevice method
     * @throws Exception - SQL Exception while writing to database
     */
    @Test
    public void verifyInteractionsWithGetDeviceData() throws Exception {
        // create method stub
        when(strmBuilder.buildInStream(anyString())).thenReturn(
                IOUtils.toInputStream("[{\n" + "    \"description\": \"Desktop\",\n" + "    \"deviceId\": \"1\"\n"
                        + "}, {\n" + "    \"description\": \"Smartphone\",\n" + "    \"deviceId\": \"2\"\n" + "}, {\n"
                        + "    \"description\": \"Tablet\",\n" + "    \"deviceId\": \"3\"\n" + "}]"));
        dataDumper.getDeviceData();

        ArgumentCaptor<Device> arg = ArgumentCaptor.forClass(Device.class);
        verify(dao, times(3)).writeToDatabase(arg.capture());
        Assert.assertEquals(arg.getAllValues().get(0).getDeviceId(), 1);
        Assert.assertEquals(arg.getAllValues().get(1).getDeviceId(), 2);
        Assert.assertEquals(arg.getAllValues().get(2).getDescription(), "Tablet");

    }

    /**
     * Verify interactions with getRankSource method
     * @throws Exception - SQL Exception while writing to database
     */
    @Test
    public void verifyInteractionsWithGetRankSourceData() throws Exception {
        // create method stub
        when(strmBuilder.buildInStream(anyString())).thenReturn(
                IOUtils.toInputStream("[{\n" + "    \"baseDomain\": \"google.com\",\n"
                        + "    \"description\": \"Google (US / English)\",\n" + "    \"rankSourceId\": \"1\",\n"
                        + "    \"name\": \"GOOGLE_EN_US\"\n" + "}, {\n" + "    \"baseDomain\": \"google.ca\",\n"
                        + "    \"description\": \"Google (Canada / English)\",\n" + "    \"rankSourceId\": \"4\",\n"
                        + "    \"name\": \"GOOGLE_EN_CA\"\n" + "}, {\n" + "    \"baseDomain\": \"google.co.uk\",\n"
                        + "    \"description\": \"Google (United Kingdom / English)\",\n"
                        + "    \"rankSourceId\": \"5\",\n" + "    \"name\": \"GOOGLE_EN_UK\"\n" + "}, {\n"
                        + "    \"baseDomain\": \"google.com.au\",\n"
                        + "    \"description\": \"Google (Australia / English)\",\n" + "    \"rankSourceId\": \"6\",\n"
                        + "    \"name\": \"GOOGLE_EN_AU\"\n" + "}]"));
        dataDumper.getRankSourceData();

        ArgumentCaptor<RankSource> arg = ArgumentCaptor.forClass(RankSource.class);
        verify(dao, times(4)).writeToDatabase(arg.capture());
        Assert.assertEquals(arg.getAllValues().size(), 4);
        Assert.assertEquals(arg.getAllValues().get(0).getRankSourceId(), 1);
        Assert.assertEquals(arg.getAllValues().get(3).getBaseDomain(), "google.com.au");

    }

    /**
     * Verify interactions with getWebProperty method
     * @throws Exception - SQL Exception while writing to database
     */
    @Test
    public void verifyInteractionsWithGetWebProperty() throws Exception {
        // create method stub
        when(strmBuilder.buildInStream(contains("web-properties?apiKey")))
                .thenReturn(
                        IOUtils.toInputStream("[{\n" +
                                "    \"isActive\": true,\n" +
                                "    \"rankSourceInfo\": [],\n" +
                                "    \"webPropertyId\": \"7\",\n" +
                                "    \"trackedSearchList\": \"https://api.conductor.com/v3/accounts/3/web-properties/7/tracked-searches\",\n" +
                                "    \"name\": \"adirondackchairs.com\"\n" +
                                "}]"));

        when(strmBuilder.buildInStream(contains("tracked-searches"))).thenReturn(
                IOUtils.toInputStream("[{\n" +
                        "    \"isActive\": true,\n" +
                        "    \"trackedSearchId\": \"4576447\",\n" +
                        "    \"preferredUrl\": null,\n" +
                        "    \"queryPhrase\": \"ADIRONDACK CHAIRS\",\n" +
                        "    \"locationId\": \"1\",\n" +
                        "    \"rankSourceId\": \"1\",\n" +
                        "    \"deviceId\": \"1\"\n" +
                        "}, {\n" +
                        "    \"isActive\": true,\n" +
                        "    \"trackedSearchId\": \"4576448\",\n" +
                        "    \"preferredUrl\": null,\n" +
                        "    \"queryPhrase\": \"ADIRONDACK CHAIR\",\n" +
                        "    \"locationId\": \"1\",\n" +
                        "    \"rankSourceId\": \"1\",\n" +
                        "    \"deviceId\": \"1\"\n" +
                        "}, {\n" +
                        "    \"isActive\": true,\n" +
                        "    \"trackedSearchId\": \"4576449\",\n" +
                        "    \"preferredUrl\": null,\n" +
                        "    \"queryPhrase\": \"ADIRONDACK\",\n" +
                        "    \"locationId\": \"1\",\n" +
                        "    \"rankSourceId\": \"1\",\n" +
                        "    \"deviceId\": \"1\"\n" +
                        "}, {\n" +
                        "    \"isActive\": true,\n" +
                        "    \"trackedSearchId\": \"4576450\",\n" +
                        "    \"preferredUrl\": null,\n" +
                        "    \"queryPhrase\": \"ADIRONDACK FURNITURE\",\n" +
                        "    \"locationId\": \"1\",\n" +
                        "    \"rankSourceId\": \"1\",\n" +
                        "    \"deviceId\": \"1\"\n" +
                        "}]"));
        dataDumper.getWebPropertiesData();
        // verify if correct web property data is returned
        ArgumentCaptor<WebProperty> arg = ArgumentCaptor.forClass(WebProperty.class);
        verify(dao, times(5)).writeToDatabase(arg.capture());
        Assert.assertEquals(arg.getAllValues().get(0).getWebPropertyId(), 7);

        // Verify if correct tracked search data is returned
        ArgumentCaptor<TrackedSearch> argTrackedSearch = ArgumentCaptor.forClass(TrackedSearch.class);
        verify(dao, times(5)).writeToDatabase(argTrackedSearch.capture());
        Assert.assertEquals(argTrackedSearch.getAllValues().get(1).getTrackedSearchId(), 4576447);

    }

    /**
     * Verify interactions with getWebPropertyRankReport method
     * @throws Exception - SQL Exception while writing to database
     */
    @Test
    public void verifyInteractionsWithGetWebPropertyRankReport() throws Exception {
        // check if Rank Source Id data is retrieved from database
        dataDumper.getWebPropertyRankReport();
        verify(dao, times(1)).getRankSourceIdsFromTrackedSearch();
    }

}