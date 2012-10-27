package zoo.daroo.h2.mem.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;


public class PexOnline implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private Integer pexId;
	private String pexCaseRefNo;
	private Boolean pexCanPublishCreditorData;
	private String pexEntitlementText;
	private String pexEntitlementRefNo;
	private Integer pexIcurId;
	private BigDecimal pexAmountTotal;
	private BigDecimal pexAmountOpen;
	private Date pexDateDue;
	private Date pexDateModified;
	private String debtorFirstName;
	private String debtorName;
	private String debtorNip;
	private String debtorPesel;
	private String debtorRegon;
	private String debtorStreet;
	private String debtorHouseNo;
	private String debtorFlatNo;
	private String debtorZip;
	private Integer debtorIcId;
	private String creditorFirstName;
	private String creditorName;
	private String creditorNip;
	private String creditorPesel;
	private String creditorRegon;
	private String creditorStreet;
	private String creditorHouseNo;
	private String creditorFlatNo;
	private String creditorZip;
	private Integer creditorIcId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	//---------------------
	public Integer getPexId() {
		return pexId;
	}
	public void setPexId(Integer pexId) {
		this.pexId = pexId;
	}
	public String getPexCaseRefNo() {
		return pexCaseRefNo;
	}
	public void setPexCaseRefNo(String pexCaseRefNo) {
		this.pexCaseRefNo = pexCaseRefNo;
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
	
	public static class PexOnlineFieldSetMapper implements FieldSetMapper<PexOnline> {

		@Override
		public PexOnline mapFieldSet(FieldSet fieldSet) throws BindException {
			PexOnline r = new PexOnline();
	    	int index = 0;
	    	r.setPexId(fieldSet.readInt(index++));
	    	r.setPexCaseRefNo(fieldSet.readString(index++));
	    	r.setPexCanPublishCreditorData(fieldSet.readBoolean(index++));
	    	r.setPexEntitlementText(fieldSet.readString(index++));
	    	r.setPexEntitlementRefNo(fieldSet.readString(index++));
	    	r.setPexIcurId(fieldSet.readInt(index++));
	    	r.setPexAmountTotal(fieldSet.readBigDecimal(index++));
	    	r.setPexAmountOpen(fieldSet.readBigDecimal(index++));
	    	r.setPexDateDue(fieldSet.readDate(index++));
	    	r.setPexDateModified(fieldSet.readDate(index++, "yyyy-MM-dd HH:mm:ss.SSS"));
	    	
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
