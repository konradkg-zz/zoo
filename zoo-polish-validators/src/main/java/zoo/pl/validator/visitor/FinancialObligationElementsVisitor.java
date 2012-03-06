package zoo.pl.validator.visitor;

import zoo.pl.validator.visitor.domain.Creditor;
import zoo.pl.validator.visitor.domain.Debtor;
import zoo.pl.validator.visitor.domain.Entity;
import zoo.pl.validator.visitor.domain.FinancialObligation;
import zoo.pl.validator.visitor.domain.Pex;
import zoo.pl.validator.visitor.domain.Reference;
import zoo.pl.validator.visitor.domain.RelatedEntity;

public interface FinancialObligationElementsVisitor {
	
	void visit(Creditor creditor);
	void visit(Debtor debtor);
	void visit(Entity entity);
	void visit(FinancialObligation financialObligation);
	void visit(Pex pex);
	void visit(Reference reference);
	void visit(RelatedEntity relatedEntity);
	
}
