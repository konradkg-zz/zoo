package zoo.pl.validator.chain.impl;

import zoo.pl.validator.chain.ChainedValidator;
import zoo.pl.validator.visitor.domain.FinancialObligation;

public class Main {
	public static void main(String[] args) {
		ChainedValidator validator, validator1;
		validator1 = validator = new StructureCompletnesValidator();
		validator1 = validator1.setNext(new IdentifiersValidator());
		
		FinancialObligation fo = new FinancialObligation();
		validator.validate(fo);
		validator.reset();
		
		System.out.println("-------------------");
		validator.validate(fo.debtor);
		validator.reset();
		
	
		System.out.println("-------------------");
		fo.debtor = null;
		validator.validate(fo);
		validator.reset();
		
	}

}
