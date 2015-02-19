package pl.ubytes.meteo.server;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.ubytes.meteo.dao.impl.ReadingService;
import pl.ubytes.meteo.model.SensorReading;
import pl.ubytes.meteo.security.TokenChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by bajek on 2/14/15.
 */

@Controller
public class DataLogger {

    Logger logger = LoggerFactory.getLogger(DataLogger.class);

    @Autowired
    private TokenChecker tokenChecker;

    @Autowired
    private ReadingService readingService;


    @RequestMapping(value = "/get/{id}/{start}/{end}", method = RequestMethod.GET)

    public
    @ResponseBody
    Object getInTimeWindow(@PathVariable("id") Integer id, @PathVariable("start") Long start,
                           @PathVariable("end") Long end) {

        List<Object> seriesData = new ArrayList<>();
        List<SensorReading> sensorReadingList;

        logger.debug("ID " + id);
        sensorReadingList = readingService.getReadings(id, new DateTime(start), new DateTime(end));
        logger.debug("List " + sensorReadingList.size());

        for (SensorReading sensorReading : sensorReadingList) {
            ArrayList<Object> point = new ArrayList<>();
            point.add(sensorReading.getTime().getMillis());
            point.add(sensorReading.getValue());
            seriesData.add(point);
        }

        Map returnData = new HashMap<String, Object>();
        returnData.put("sensorId", id);
        returnData.put("data", seriesData);

        return returnData;

    }

    @RequestMapping(value = "/log/{token}/{id}/{value}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> log(@PathVariable("id") Integer id,
                               @PathVariable("value") Float value,
                               @PathVariable("token") String token) {


        if (!tokenChecker.checkToken(token)) {
            logger.debug("Incorrect token: " + token);
            return new ResponseEntity<>("Incorrect token", HttpStatus.UNAUTHORIZED);
        }

        logger.debug("Received for id: " + id + " value: " + value);

        //TODO handle result
        boolean result = readingService.insert(new SensorReading(id, value, new DateTime()));

        return new ResponseEntity<>("OK", HttpStatus.OK);


    }

}
