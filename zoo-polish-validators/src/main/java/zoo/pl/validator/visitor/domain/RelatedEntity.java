package zoo.pl.validator.visitor.domain;

import zoo.pl.validator.visitor.FinancialObligationElement;
import zoo.pl.validator.visitor.FinancialObligationElementsVisitor;

public class RelatedEntity implements FinancialObligationElement{
	public Entity entity = new Entity();
	
	@Override
	public void accept(FinancialObligationElementsVisitor visitor) {
		entity.accept(visitor);
		
		visitor.visit(this);
	}
}
