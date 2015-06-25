package beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({ "averageVolume", "trackedSearchId", "volumeItems" })
public class ClientWebPropertySearchVolumeReport {

    private long averageVolume;
    private long trackedSearchId;
    private List<VolumeItem> volumeItems = new ArrayList<VolumeItem>();

    /**
     *
     * @return The averageVolume
     */
    @JsonProperty("averageVolume")
    public long getAverageVolume() {
        return averageVolume;
    }

    /**
     *
     * @param averageVolume
     *            The averageVolume
     */
    public void setAverageVolume(long averageVolume) {
        this.averageVolume = averageVolume;
    }

    /**
     *
     * @return The trackedSearchId
     */
    @JsonProperty("trackedSearchId")
    public long getTrackedSearchId() {
        return trackedSearchId;
    }

    /**
     *
     * @param trackedSearchId
     *            The trackedSearchId
     */
    public void setTrackedSearchId(long trackedSearchId) {
        this.trackedSearchId = trackedSearchId;
    }

    /**
     *
     * @return The volumeItems
     */
    @JsonProperty("volumeItems")
    public List<VolumeItem> getVolumeItems() {
        return volumeItems;
    }

    /**
     *
     * @param volumeItems
     *            The volumeItems
     */
    public void setVolumeItems(List<VolumeItem> volumeItems) {
        this.volumeItems = volumeItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ClientWebPropertySearchVolumeReport that = (ClientWebPropertySearchVolumeReport) o;

        if (averageVolume != that.averageVolume)
            return false;
        if (trackedSearchId != that.trackedSearchId)
            return false;
        return !(volumeItems != null ? !volumeItems.equals(that.volumeItems) : that.volumeItems != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (averageVolume ^ (averageVolume >>> 32));
        result = 31 * result + (int) (trackedSearchId ^ (trackedSearchId >>> 32));
        result = 31 * result + (volumeItems != null ? volumeItems.hashCode() : 0);
        return result;
    }

    // Class to contain Volume Items' data
    @JsonPropertyOrder({ "volume", "month", "year" })
    public class VolumeItem {

        private Object volume;
        private long month;
        private long year;

        /**
         *
         * @return The volume
         */
        @JsonProperty("volume")
        public Object getVolume() {
            return volume;
        }

        /**
         *
         * @param volume
         *            The volume
         */
        public void setVolume(Object volume) {
            this.volume = volume;
        }

        /**
         *
         * @return The month
         */
        @JsonProperty("month")
        public long getMonth() {
            return month;
        }

        /**
         *
         * @param month
         *            The month
         */
        public void setMonth(long month) {
            this.month = month;
        }

        /**
         *
         * @return The year
         */
        @JsonProperty("year")
        public long getYear() {
            return year;
        }

        /**
         *
         * @param year
         *            The year
         */
        public void setYear(long year) {
            this.year = year;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            VolumeItem that = (VolumeItem) o;

            if (month != that.month)
                return false;
            if (year != that.year)
                return false;
            return !(volume != null ? !volume.equals(that.volume) : that.volume != null);

        }

        @Override
        public int hashCode() {
            int result = volume != null ? volume.hashCode() : 0;
            result = 31 * result + (int) (month ^ (month >>> 32));
            result = 31 * result + (int) (year ^ (year >>> 32));
            return result;
        }
    }
}