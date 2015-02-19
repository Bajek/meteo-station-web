package pl.ubytes.meteo.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;

/**
 * Created by bajek on 2/17/15.
 */
public class SensorReading {

    private int sensorId;
    private float value;
    private DateTime time;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int id) {
        this.sensorId = id;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public SensorReading(int sensorId, float value, DateTime time) {
        this.sensorId = sensorId;
        this.value = value;
        this.time = time;

    }


}
