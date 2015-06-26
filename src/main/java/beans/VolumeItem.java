package beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// Class to contain Volume Items' data
@JsonPropertyOrder({ "volume", "month", "year" })
public class VolumeItem {

    private long volume;
    private int month;
    private int year;

    private long trackedSearchId;

    /**
     *
     * @return The volume
     */
    @JsonProperty("volume")
    public long getVolume() {
        return volume;
    }

    /**
     *
     * @param volume
     *            The volume
     */
    public void setVolume(long volume) {
        this.volume = volume;
    }

    /**
     *
     * @return The month
     */
    @JsonProperty("month")
    public int getMonth() {
        return month;
    }

    /**
     *
     * @param month
     *            The month
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     *
     * @return The year
     */
    @JsonProperty("year")
    public int getYear() {
        return year;
    }

    /**
     *
     * @param year
     *            The year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * 
     * @return trackedSearchId - The TrackedSearch ID
     */
    public long getTrackedSearchId() {
        return trackedSearchId;
    }

    /**
     * 
     * @param trackedSearchId
     *            - the TrackedSearch ID
     */
    public void setTrackedSearchId(long trackedSearchId) {
        this.trackedSearchId = trackedSearchId;
    }

}