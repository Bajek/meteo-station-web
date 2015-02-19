package pl.ubytes.meteo.model;

/**
 * Created by bajek on 2/17/15.
 */
public enum ReadingPeriod {
    LAST_24("last24"),
    LAST_WEEK("lastWeek"),
    LAST_MONTH("LastMonth");

    private String period;

    private ReadingPeriod(String period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return this.period;
    }
}
