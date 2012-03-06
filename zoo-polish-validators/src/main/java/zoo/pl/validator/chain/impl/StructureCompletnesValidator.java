package zoo.pl.validator.chain.impl;

import zoo.pl.validator.chain.AbstractChainedValidator;
import zoo.pl.validator.visitor.domain.Creditor;
import zoo.pl.validator.visitor.domain.Debtor;
import zoo.pl.validator.visitor.domain.FinancialObligation;

public class StructureCompletnesValidator extends AbstractChainedValidator{

	@Override
	public void validate(Creditor creditor) {
		log("creditor.entity " + ((creditor.entity != null) ? "OK" : "missing"));
		super.validate(creditor);
	}

	@Override
	public void validate(Debtor debtor) {
		log("debtor.entity " + ((debtor.entity != null) ? "OK" : "missing"));
		super.validate(debtor);
	}

	@Override
	public void validate(FinancialObligation fo) {
		String debtor = "debtor=" + ((fo.debtor != null) ? "OK" : "missing");
		String creditor = "; creditor=" + ((fo.creditor != null) ? "OK" : "missing");
		String pex = "; pex=" + ((fo.pex != null) ? "OK" : "missing");
		
		log(debtor + creditor + pex);
		
		super.validate(fo);
	}

	

}
