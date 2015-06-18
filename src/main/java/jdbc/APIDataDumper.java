package jdbc;

import beans.*;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DAO;
import streaming.APIPathBuilder;
import streaming.StreamBuilder;
import utils.Util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by anihalani on 6/9/15.
 */
public class APIDataDumper {

    private String baseUrl;
    private static final String ENDPOINT_ACCOUNTS = "accounts";
    private static final String ENDPOINT_LOCATIONS = "locations";
    private static final String ENDPOINT_RANK_SOURCES = "rank-sources";
    private static final String ENDPOINT_DEVICES = "devices";
    private static final String ENDPOINT_WEB_PROPERTY = "accounts/<<accountNumber>>/web-properties";

    private APIPathBuilder pathBuilder;

    /**
     * Constructor to create an instance of API Path Builder
     * 
     * @param url
     *            - The Conductor API baseUrl
     */
    public APIDataDumper(String url) {
        this.baseUrl = url;
        pathBuilder = new APIPathBuilder(url);
    }

    /**
     * Sets the APIPathBuilder property for the class
     * 
     * @param pathBuilder
     *            - The APIPathBuilder instance
     */
    public void setPathBuilder(APIPathBuilder pathBuilder) {
        this.pathBuilder = pathBuilder;
    }

    /**
     * Writes the location data returned from the API endpoint to the local database
     *
     */
    public void getLocationData() {
        String locationsUrl = pathBuilder.addKeyAndSignature(pathBuilder.build(ENDPOINT_LOCATIONS));
        writeObjects(locationsUrl, Location.class);
    }

    /**
     * Writes the rank_source data returned from the API endpoint to the local database
     *
     */
    public void getRankSourceData() {
        String rankSourcesUrl = pathBuilder.addKeyAndSignature(pathBuilder.build(ENDPOINT_RANK_SOURCES));
        writeObjects(rankSourcesUrl, RankSource.class);
    }

    /**
     * Writes the device data returned from the API endpoint to the local database
     */
    public void getDeviceData() {
        String devicesUrl = pathBuilder.addKeyAndSignature(pathBuilder.build(ENDPOINT_DEVICES));
        writeObjects(devicesUrl, Device.class);
    }

    /**
     * Writes the WebProperties data returned from the API endpoint to the local database
     */
    public void getWebPropertiesData() throws InterruptedException {

        Properties properties = Util.readProperties(Util.PROPS_FILE);
        String[] accounts = properties.getProperty("ACCOUNTS").split(",");

        for (String account : accounts) {
            String webPropertyUrl = pathBuilder.addKeyAndSignature(pathBuilder.build(ENDPOINT_WEB_PROPERTY.replace(
                    "<<accountNumber>>", account)));
            Thread.sleep(2000);
            writeObjects(webPropertyUrl, WebProperty.class);
        }
    }

    /**
     * Gets the Tracked Search data returned from the API endpoint
     */
    public void getTrackedSearchData(String urlWithoutSig) {
        String trackedSearchUrl = pathBuilder.addKeyAndSignature(urlWithoutSig);
        writeObjects(trackedSearchUrl, TrackedSearch.class);
    }

    /**
     * Generates URl by getting Account id, Web-Property id and Rank Source id from database and gets the Web Property
     * Rank Report data from API endpoints.
     */
    public void getWebPropertyRankReport() {
        DAO dao = new DAO();
        ResultSet rs = dao.getRankSourceIdsFromTrackedSearch();

        try {
            if (rs != null) {
                rs.beforeFirst();

                while (rs.next()) {
                    Thread.sleep(2000);
                    String webPropertyRankReportUrl = pathBuilder.build(Integer.toString(rs.getInt("account_id")));

                    webPropertyRankReportUrl = pathBuilder.addEndPointWithValue(webPropertyRankReportUrl,
                            "web-properties", Integer.toString(rs.getInt("web_property_id")));

                    webPropertyRankReportUrl = pathBuilder.addEndPointWithValue(webPropertyRankReportUrl,
                            "rank-sources", Integer.toString(rs.getInt("rank_source_id")));

                    webPropertyRankReportUrl = webPropertyRankReportUrl.concat("/tp/CURRENT/serp-items");

                    webPropertyRankReportUrl = pathBuilder.addKeyAndSignature(webPropertyRankReportUrl);

                    writeObjects(webPropertyRankReportUrl, ClientWebPropertyRankReport.class);

                }
            }
        } catch (SQLException e) {
            System.out.println("Error in APIDataDumper.getTrackedSearchData");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs.getStatement().close();
                }
                dao.closeConnection();
            } catch (SQLException e) {
                System.out.println("Error while closing ResultSet/Statement in getWebPropertyDataReport");
                e.printStackTrace();
            }
        }
    }

    /**
     * Streams data from an endpoint one object at a time and writes it to the local database
     * 
     * @param url
     *            - the complete url with the endpoint parameter, the API key and the signature
     * @param cl
     *            - the class of the objects expected to be returned in the Json
     */
    private void writeObjects(String url, Class cl) {
        InputStream instream = null;
        DAO dao = new DAO();
        try {
            instream = new StreamBuilder(url).getInstream();
            JsonFactory jsonFactory = new JsonFactory();
            JsonParser jParser = jsonFactory.createJsonParser(instream);
            ObjectMapper objectMapper = new ObjectMapper();
            // Read the json objects from stream one at a time
            while (jParser.nextToken() != JsonToken.END_ARRAY) {
                if (jParser.getCurrentToken() == JsonToken.START_ARRAY) {
                    continue;
                }
                // Read an object and write it to Database
                Object object = objectMapper.readValue(jParser, cl);

                // Write the comparison web properties and the tracked search data for each web property
                if (object instanceof WebProperty) {
                    ((WebProperty) object).setAccountId(getAccountIdFromUrl(url));
                    dao.writeToDatabase(object);
                    Thread.sleep(2000);
                    writeComparisionWebProperties((WebProperty) object);
                    Thread.sleep(2000);
                    getTrackedSearchData(((WebProperty) object).getTrackedSearchList());
                } else if (object instanceof TrackedSearch) {
                    ((TrackedSearch) object).setWebPropertyId(getWebPropertyIdFromUrl(url));
                    dao.writeToDatabase(object);
                } else {
                    dao.writeToDatabase(object);
                }
            }
        } catch (Exception e) {
            System.out.println("Error in writeObjects");
            System.out.println(url);
            e.printStackTrace();
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
                dao.closeConnection();
            } catch (IOException e) {
                System.out.println("Error while closing stream in writeObjects");
                System.out.println(url);
                e.printStackTrace();
            }
        }

    }

    /**
     * Writes the comparison web properties for a given web property
     * 
     * @param webProperty
     *            - the WebProperty object whose comparison Web Properties are to be written to the database
     * @throws Exception
     *             - if connection is null and Exception is thrown from DAO.writeToDatabase
     */
    private void writeComparisionWebProperties(WebProperty webProperty) throws Exception {
        DAO dao = new DAO();
        for (RankSourceInfo rankSourceInfo : webProperty.getRankSourceInfo()) {
            for (ComparisonWebProperty comparisonWebProperty : rankSourceInfo.getComparisonWebProperties()) {
                dao.writeToDatabase(comparisonWebProperty);
            }
        }
        dao.closeConnection();
    }

    /**
     * Extracts and returns the account-id used in the web-property endpoint Url
     * @param url - The comple URL for Web-Property endpoint
     * @return
     */
    private int getAccountIdFromUrl(String url) {
        String[] tokens = url.split("/");
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("accounts")) {
                return Integer.parseInt(tokens[i + 1]);
            }
        }
        return 0;
    }

    /**
     * Extracts
     * @param url - The complete url for tracked-search endpoint
     * @return
     */
    private int getWebPropertyIdFromUrl(String url) {
        String[] tokens = url.split("/");
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("web-properties")) {
                return Integer.parseInt(tokens[i + 1]);
            }
        }
        return 0;
    }
}
