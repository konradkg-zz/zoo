package zoo.pl.validator.visitor.domain;

import java.util.ArrayList;
import java.util.List;

import zoo.pl.validator.visitor.FinancialObligationElement;
import zoo.pl.validator.visitor.FinancialObligationElementsVisitor;

public class Creditor implements FinancialObligationElement {
	public Entity entity = new Entity();

	@SuppressWarnings("serial")
	public List<RelatedEntity> entities = new ArrayList<RelatedEntity>() {
		{
			add(new RelatedEntity());
			add(new RelatedEntity());
			add(new RelatedEntity());
		}
	};

	@Override
	public void accept(FinancialObligationElementsVisitor visitor) {
		entity.accept(visitor);
		for(RelatedEntity r : entities) {
			r.accept(visitor);
		}
		
		visitor.visit(this);
	}
}
