package zoo.pl.validator.visitor.impl;

import zoo.pl.validator.visitor.FinancialObligationElementsVisitor;
import zoo.pl.validator.visitor.domain.Creditor;
import zoo.pl.validator.visitor.domain.Debtor;
import zoo.pl.validator.visitor.domain.Entity;
import zoo.pl.validator.visitor.domain.FinancialObligation;
import zoo.pl.validator.visitor.domain.Pex;
import zoo.pl.validator.visitor.domain.Reference;
import zoo.pl.validator.visitor.domain.RelatedEntity;

public class IdentityChecksumVisitor implements FinancialObligationElementsVisitor {
	
	@Override
	public void visit(Creditor creditor) {
		log("Nothing to do with creditor");
	}

	@Override
	public void visit(Debtor debtor) {
		log("Nothing to do with debtor");
	}

	@Override
	public void visit(Entity entity) {
		if(entity.identifier != null) {
			log("entity.identifier exists. ");
		}
	}

	@Override
	public void visit(FinancialObligation financialObligation) {
		log("Nothing to do with financialObligation");
	}

	@Override
	public void visit(Pex pex) {
		log("Nothing to do with pex");
	}

	@Override
	public void visit(Reference reference) {
		if(reference.userID != null) {
			log("Reference id exists. ");
		}
		
	}

	@Override
	public void visit(RelatedEntity relatedEntity) {
		log("Nothing to do with relatedEntity");
	}
	
	private void log(String log) {
		System.out.println(getClass().getSimpleName() + ": " + log);
	}

}
