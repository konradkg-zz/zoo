package zoo.pl.validator.chain.impl;

import zoo.pl.validator.chain.ChainedValidator;
import zoo.pl.validator.visitor.domain.FinancialObligation;

public class Main {
	public static void main(String[] args) {
		ChainedValidator validator, validator1;
		validator1 = validator = new IdentifiersValidator();
		validator1 = validator1.setNext(new StructureCompletnesValidator());
		
		FinancialObligation fo = new FinancialObligation();
		validator.validate(fo);
	}

}
