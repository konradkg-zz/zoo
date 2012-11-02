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

	public Integer readInt(int index) {
		return Integer.valueOf(tokens.get(index));
	}
	
	public Long readLong(int index) {
		return Long.valueOf(tokens.get(index));
	}
	
	public BigDecimal readBigDecimal(int index) {
		return new BigDecimal(tokens.get(index));
	}
	
	public Date readDate(int index) throws ParseException {
		return DefaultDateFormat.parse(tokens.get(index));
	}
	
	public Date readDateTime(int index) throws ParseException {
		return DefaultDateTimeFormat.parse(tokens.get(index));
	}
	
}
