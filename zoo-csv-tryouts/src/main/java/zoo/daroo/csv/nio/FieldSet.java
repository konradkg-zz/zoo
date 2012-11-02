package zoo.daroo.csv.nio;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FieldSet {
	
	private final List<String> tokens;
	
	private final static DateFormat DefaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private final static DateFormat DefaultDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	FieldSet(List<String> tokens) {
		this.tokens = tokens;
	}
	
	String token(int index) {
		final String result = tokens.get(index);
		return (result == null || "".equals(result)) ? null : result;
	}
	
	public String readString(int index) {
		return token(index);
	}

	public Integer readInt(int index) {
		final String token = token(index);
		if(token == null)
			return null;
		
		return Integer.valueOf(token);
	}
	
	public Long readLong(int index) {
		final String token = token(index);
		if(token == null)
			return null;
		
		return Long.valueOf(token);
	}
	
	public BigDecimal readBigDecimal(int index) {
		final String token = token(index);
		if(token == null)
			return null;
		
		return new BigDecimal(token);
	}
	
	public Date readDate(int index) throws ParseException {
		final String token = token(index);
		if(token == null)
			return null;
		
		return DefaultDateFormat.parse(token);
	}
	
	public Date readDateTime(int index) throws ParseException {
		final String token = token(index);
		if(token == null)
			return null;
		
		return DefaultDateTimeFormat.parse(token);
	}
}
