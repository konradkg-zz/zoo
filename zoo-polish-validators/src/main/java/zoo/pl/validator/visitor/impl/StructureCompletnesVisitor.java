package zoo.pl.validator.visitor.impl;

import zoo.pl.validator.visitor.FinancialObligationElementsVisitor;
import zoo.pl.validator.visitor.domain.Creditor;
import zoo.pl.validator.visitor.domain.Debtor;
import zoo.pl.validator.visitor.domain.Entity;
import zoo.pl.validator.visitor.domain.FinancialObligation;
import zoo.pl.validator.visitor.domain.Pex;
import zoo.pl.validator.visitor.domain.Reference;
import zoo.pl.validator.visitor.domain.RelatedEntity;

public class StructureCompletnesVisitor implements FinancialObligationElementsVisitor {

	@Override
	public void visit(Creditor creditor) {
	}

	@Override
	public void visit(Debtor debtor) {

	}

	@Override
	public void visit(Entity entity) {
	}

	@Override
	public void visit(FinancialObligation fo) {
		String debtor = "debtor=" + ((fo.debtor != null) ? "OK" : "missing");
		String creditor = "; creditor=" + ((fo.creditor != null) ? "OK" : "missing");
		String pex = "; pex=" + ((fo.pex != null) ? "OK" : "missing");
		
		log(debtor + creditor + pex);
	}

	@Override
	public void visit(Pex pex) {
	}

	@Override
	public void visit(Reference reference) {
	}

	@Override
	public void visit(RelatedEntity relatedEntity) {
	}

	private void log(String log) {
		System.out.println(getClass().getSimpleName() + ": " + log);
	}

}
