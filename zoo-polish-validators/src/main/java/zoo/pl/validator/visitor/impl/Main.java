package zoo.pl.validator.visitor.impl;

import zoo.pl.validator.visitor.domain.FinancialObligation;

public class Main {
	
	public static void main(String[] args) {
		
		final FinancialObligation fo = new FinancialObligation();
		fo.accept(new IdentityChecksumVisitor());
		fo.accept(new StructureCompletnesVisitor());
		
		fo.debtor = null;
		
		System.out.println("-------------------");
		fo.accept(new IdentityChecksumVisitor());
		fo.accept(new StructureCompletnesVisitor());
		
	}

}
