package pl.ubytes.meteo.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * Created by bajek on 2/18/15.
 */
public class CustomDateSerializer extends JsonSerializer<DateTime> {

    private static DateTimeFormatter formatter =
            DateTimeFormat.forPattern("dd-MM-yyyy hh:mm:ss");

    @Override
    public void serialize(DateTime value, JsonGenerator gen, SerializerProvider sp) throws IOException {
        gen.writeString(formatter.print(value));
    }
}
