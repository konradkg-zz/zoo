package zoo.pl.validator.visitor.domain;

import zoo.pl.validator.visitor.FinancialObligationElement;
import zoo.pl.validator.visitor.FinancialObligationElementsVisitor;

public class FinancialObligation implements FinancialObligationElement{
	
	public Reference reference = new Reference();
	public Debtor debtor = new Debtor();
	public Creditor creditor = new Creditor();
	public Pex pex = new Pex();
	
	@Override
	public void accept(FinancialObligationElementsVisitor visitor) {
		reference.accept(visitor);
		if(debtor != null) {
			debtor.accept(visitor);
		}
		creditor.accept(visitor);
		pex.accept(visitor);
		
		visitor.visit(this);
	}

}
