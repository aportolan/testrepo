package hr.aportolan.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class IsoDateSerializer extends StdSerializer<Date> {

	private static final long serialVersionUID = 1L;

	public IsoDateSerializer() {
		this(Date.class);
	}

	protected IsoDateSerializer(Class<Date> t) {
		super(t);

	}

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException {

		gen.writeString(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(value));

	}

}