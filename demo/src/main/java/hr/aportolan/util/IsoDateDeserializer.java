package hr.aportolan.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class IsoDateDeserializer extends StdDeserializer<Date> {

	private static final long serialVersionUID = 1L;

	public IsoDateDeserializer() {
		this(Date.class);
	}

	protected IsoDateDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

		String time = jp.readValueAs(String.class);

		try {
			try {
				if (time.equals(""))
					return null;
				return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(time);
			} catch (Exception e) {
				return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(time);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unparsable time:" + time);
		}
	}

}