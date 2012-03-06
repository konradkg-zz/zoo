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
		if (creditor != null) {
			log("Creditor OK.");
		} else {
			log("Creditor missing.");
		}

	}

	@Override
	public void visit(Debtor debtor) {
		if (debtor != null) {
			log("Debtor OK.");
		} else {
			log("Debtor missing.");
		}

	}

	@Override
	public void visit(Entity entity) {
	}

	@Override
	public void visit(FinancialObligation financialObligation) {
	}

	@Override
	public void visit(Pex pex) {
		if (pex != null) {
			log("Pex OK.");
		} else {
			log("Pex missing.");
		}
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
