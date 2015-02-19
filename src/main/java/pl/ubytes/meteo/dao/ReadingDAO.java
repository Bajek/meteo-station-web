package pl.ubytes.meteo.dao;

import org.joda.time.DateTime;
import pl.ubytes.meteo.model.SensorReading;
import pl.ubytes.meteo.model.ReadingPeriod;

import java.util.List;

/**
 * Created by bajek on 2/17/15.
 */
public interface ReadingDAO {

    public boolean insert(SensorReading sensorReading);
    public List<SensorReading> getReadings(int sensorId, DateTime start, DateTime end);
    public List<SensorReading> getReadings(int sensorId, ReadingPeriod period);
    public SensorReading getLastReading(int sensorId);
}
