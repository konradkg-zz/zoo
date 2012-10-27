package zoo.daroo.h2.mem.tryouts.csv;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.core.io.FileSystemResource;

public class SpringBatchTry {

	public static void main(String[] args) throws Exception {
		FlatFileItemReader<Record> itemReader = new FlatFileItemReader<Record>();
		itemReader.setEncoding("UTF-8");
		
		itemReader.setResource(new FileSystemResource("p:/Temp/h2_data/dump_lite.csv"));
		// DelimitedLineTokenizer defaults to comma as its delimiter
		DefaultLineMapper<Record> lineMapper = new DefaultLineMapper<Record>();
		
		lineMapper.setLineTokenizer(new DelimitedLineTokenizer(';'));
		lineMapper.setFieldSetMapper(new RecordFieldSetMapper());
		itemReader.setLineMapper(lineMapper);
		itemReader.open(new ExecutionContext());
		Record player = null;
		while((player = itemReader.read()) != null) {
			player.toString();
		}
		
	}
	
	public static class Record implements Serializable {
		Integer pexId;
		String pexCAseRefNo;
		Boolean pexCanPublishCreditorData;
		String pexEntitlementText;
		String pexEntitlementRefNo;
		Integer pexIcurId;
		BigDecimal pexAmountTotal;
		BigDecimal pexAmountOpen;
		Date pexDateDue;
		Date pexDateModified;
		String debtorFirstName;
		String debtorName;
		String debtorNip;
		String debtorPesel;
		String debtorRegon;
		String debtorStreet;
		String debtorHouseNo;
		String debtorFlatNo;
		String debtorZip;
		Integer debtorIcId;
		String creditorFirstName;
		String creditorName;
		String creditorNip;
		String creditorPesel;
		String creditorRegon;
		String creditorStreet;
		String creditorHouseNo;
		String creditorFlatNo;
		String creditorZip;
		Integer creditorIcId;
		public Integer getPexId() {
			return pexId;
		}
		public void setPexId(Integer pexId) {
			this.pexId = pexId;
		}
		public String getPexCAseRefNo() {
			return pexCAseRefNo;
		}
		public void setPexCAseRefNo(String pexCAseRefNo) {
			this.pexCAseRefNo = pexCAseRefNo;
		}
		public Boolean getPexCanPublishCreditorData() {
			return pexCanPublishCreditorData;
		}
		public void setPexCanPublishCreditorData(Boolean pexCanPublishCreditorData) {
			this.pexCanPublishCreditorData = pexCanPublishCreditorData;
		}
		public String getPexEntitlementText() {
			return pexEntitlementText;
		}
		public void setPexEntitlementText(String pexEntitlementText) {
			this.pexEntitlementText = pexEntitlementText;
		}
		public String getPexEntitlementRefNo() {
			return pexEntitlementRefNo;
		}
		public void setPexEntitlementRefNo(String pexEntitlementRefNo) {
			this.pexEntitlementRefNo = pexEntitlementRefNo;
		}
		public Integer getPexIcurId() {
			return pexIcurId;
		}
		public void setPexIcurId(Integer pexIcurId) {
			this.pexIcurId = pexIcurId;
		}
		public BigDecimal getPexAmountTotal() {
			return pexAmountTotal;
		}
		public void setPexAmountTotal(BigDecimal pexAmountTotal) {
			this.pexAmountTotal = pexAmountTotal;
		}
		public BigDecimal getPexAmountOpen() {
			return pexAmountOpen;
		}
		public void setPexAmountOpen(BigDecimal pexAmountOpen) {
			this.pexAmountOpen = pexAmountOpen;
		}
		public Date getPexDateDue() {
			return pexDateDue;
		}
		public void setPexDateDue(Date pexDateDue) {
			this.pexDateDue = pexDateDue;
		}
		public Date getPexDateModified() {
			return pexDateModified;
		}
		public void setPexDateModified(Date pexDateModified) {
			this.pexDateModified = pexDateModified;
		}
		public String getDebtorFirstName() {
			return debtorFirstName;
		}
		public void setDebtorFirstName(String debtorFirstName) {
			this.debtorFirstName = debtorFirstName;
		}
		public String getDebtorName() {
			return debtorName;
		}
		public void setDebtorName(String debtorName) {
			this.debtorName = debtorName;
		}
		public String getDebtorNip() {
			return debtorNip;
		}
		public void setDebtorNip(String debtorNip) {
			this.debtorNip = debtorNip;
		}
		public String getDebtorPesel() {
			return debtorPesel;
		}
		public void setDebtorPesel(String debtorPesel) {
			this.debtorPesel = debtorPesel;
		}
		public String getDebtorRegon() {
			return debtorRegon;
		}
		public void setDebtorRegon(String debtorRegon) {
			this.debtorRegon = debtorRegon;
		}
		public String getDebtorStreet() {
			return debtorStreet;
		}
		public void setDebtorStreet(String debtorStreet) {
			this.debtorStreet = debtorStreet;
		}
		public String getDebtorHouseNo() {
			return debtorHouseNo;
		}
		public void setDebtorHouseNo(String debtorHouseNo) {
			this.debtorHouseNo = debtorHouseNo;
		}
		public String getDebtorFlatNo() {
			return debtorFlatNo;
		}
		public void setDebtorFlatNo(String debtorFlatNo) {
			this.debtorFlatNo = debtorFlatNo;
		}
		public String getDebtorZip() {
			return debtorZip;
		}
		public void setDebtorZip(String debtorZip) {
			this.debtorZip = debtorZip;
		}
		public Integer getDebtorIcId() {
			return debtorIcId;
		}
		public void setDebtorIcId(Integer debtorIcId) {
			this.debtorIcId = debtorIcId;
		}
		public String getCreditorFirstName() {
			return creditorFirstName;
		}
		public void setCreditorFirstName(String creditorFirstName) {
			this.creditorFirstName = creditorFirstName;
		}
		public String getCreditorName() {
			return creditorName;
		}
		public void setCreditorName(String creditorName) {
			this.creditorName = creditorName;
		}
		public String getCreditorNip() {
			return creditorNip;
		}
		public void setCreditorNip(String creditorNip) {
			this.creditorNip = creditorNip;
		}
		public String getCreditorPesel() {
			return creditorPesel;
		}
		public void setCreditorPesel(String creditorPesel) {
			this.creditorPesel = creditorPesel;
		}
		public String getCreditorRegon() {
			return creditorRegon;
		}
		public void setCreditorRegon(String creditorRegon) {
			this.creditorRegon = creditorRegon;
		}
		public String getCreditorStreet() {
			return creditorStreet;
		}
		public void setCreditorStreet(String creditorStreet) {
			this.creditorStreet = creditorStreet;
		}
		public String getCreditorHouseNo() {
			return creditorHouseNo;
		}
		public void setCreditorHouseNo(String creditorHouseNo) {
			this.creditorHouseNo = creditorHouseNo;
		}
		public String getCreditorFlatNo() {
			return creditorFlatNo;
		}
		public void setCreditorFlatNo(String creditorFlatNo) {
			this.creditorFlatNo = creditorFlatNo;
		}
		public String getCreditorZip() {
			return creditorZip;
		}
		public void setCreditorZip(String creditorZip) {
			this.creditorZip = creditorZip;
		}
		public Integer getCreditorIcId() {
			return creditorIcId;
		}
		public void setCreditorIcId(Integer creditorIcId) {
			this.creditorIcId = creditorIcId;
		}
		
		
	}
	
	protected static class RecordFieldSetMapper implements FieldSetMapper<Record> {
	    public Record mapFieldSet(FieldSet fieldSet) {
	    	Record r = new Record();
	    	int index = 0;
	    	r.setPexId(fieldSet.readInt(index++));
	    	r.setPexCAseRefNo(fieldSet.readString(index++));
	    	r.setPexCanPublishCreditorData(fieldSet.readBoolean(index++));
	    	r.setPexEntitlementText(fieldSet.readString(index++));
	    	r.setPexEntitlementRefNo(fieldSet.readString(index++));
	    	r.setPexIcurId(fieldSet.readInt(index++));
	    	r.setPexAmountTotal(fieldSet.readBigDecimal(index++));
	    	r.setPexAmountOpen(fieldSet.readBigDecimal(index++));
	    	r.setPexDateDue(fieldSet.readDate(index++));
	    	r.setPexDateModified(fieldSet.readDate(index++));
	    	
	    	r.setDebtorFirstName(fieldSet.readString(index++));
	    	r.setDebtorName(fieldSet.readString(index++));
	    	r.setDebtorNip(fieldSet.readString(index++));
	    	r.setDebtorPesel(fieldSet.readString(index++));
	    	r.setDebtorRegon(fieldSet.readString(index++));
	    	r.setDebtorStreet(fieldSet.readString(index++));
	    	r.setDebtorHouseNo(fieldSet.readString(index++));
	    	r.setDebtorFlatNo(fieldSet.readString(index++));
	    	r.setDebtorZip(fieldSet.readString(index++));
	    	r.setDebtorIcId(fieldSet.readInt(index++));
	    	
	    	r.setCreditorFirstName(fieldSet.readString(index++));
	    	r.setCreditorName(fieldSet.readString(index++));
	    	r.setCreditorNip(fieldSet.readString(index++));
	    	r.setCreditorPesel(fieldSet.readString(index++));
	    	r.setCreditorRegon(fieldSet.readString(index++));
	    	r.setCreditorStreet(fieldSet.readString(index++));
	    	r.setCreditorHouseNo(fieldSet.readString(index++));
	    	r.setCreditorFlatNo(fieldSet.readString(index++));
	    	r.setCreditorZip(fieldSet.readString(index++));
	    	r.setCreditorIcId(fieldSet.readInt(index++));
	    	
	    	
	    	
	    	
	        return r;
	    }
	}   
	
	

}
