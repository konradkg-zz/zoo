package zoo.pl.validator.visitor.domain;

import zoo.pl.validator.visitor.FinancialObligationElement;
import zoo.pl.validator.visitor.FinancialObligationElementsVisitor;

public class Reference implements FinancialObligationElement{
	public Integer userID = 88;
	
	@Override
	public void accept(FinancialObligationElementsVisitor visitor) {
		visitor.visit(this);
		
	}
}
