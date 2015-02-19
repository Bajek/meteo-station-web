package pl.ubytes.meteo.dao.impl;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.ubytes.meteo.dao.ReadingDAO;
import pl.ubytes.meteo.model.SensorReading;
import pl.ubytes.meteo.model.ReadingPeriod;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bajek on 2/17/15.
 */
public class ReadingService implements ReadingDAO {

    Logger logger = LoggerFactory.getLogger(ReadingService.class);
    private DataSource dataSource;


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean insert(SensorReading sensorReading) {



        String sql = "INSERT INTO READINGS " +
                "(TIME, SENSOR_ID, VALUE) VALUES (?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, sensorReading.getTime().getMillis());
            ps.setInt(2, sensorReading.getSensorId());
            ps.setFloat(3, sensorReading.getValue());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            logger.debug(e.getMessage());
            return false;

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.debug(e.getMessage());
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public List<SensorReading> getReadings(int sensorId, DateTime start, DateTime end) {
        List<SensorReading> sensorReadingList = new ArrayList();

        String sql = "SELECT * FROM READINGS WHERE SENSOR_ID = ? AND TIME >= ? AND TIME <= ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, sensorId);
            ps.setLong(2, start.getMillis());
            ps.setLong(3, end.getMillis());

            SensorReading sensorReading = null;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sensorReading = new SensorReading(
                        rs.getInt("SENSOR_ID"),
                        rs.getFloat("VALUE"),
                        new DateTime(rs.getLong("TIME"))
                );
                sensorReadingList.add(sensorReading);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }

        return sensorReadingList;
    }

    @Override
    public List<SensorReading> getReadings(int sensorId, ReadingPeriod period) {
        return null;
    }

    @Override
    public SensorReading getLastReading(int sensorId) {
        return null;
    }
}
