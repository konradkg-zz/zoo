package zoo.pl.validator.visitor.domain;

import zoo.pl.validator.visitor.FinancialObligationElement;
import zoo.pl.validator.visitor.FinancialObligationElementsVisitor;

public class Entity implements FinancialObligationElement{
	public String name = "name";
	public String identifier = "997766";
	
	@Override
	public void accept(FinancialObligationElementsVisitor visitor) {
		visitor.visit(this);
	}
}
