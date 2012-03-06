package zoo.pl.validator.chain.impl;

import zoo.pl.validator.chain.AbstractChainedValidator;
import zoo.pl.validator.visitor.domain.Entity;

public class IdentifiersValidator extends AbstractChainedValidator{

	@Override
	public void validate(Entity entity) {
		if(entity.identifier != null) {
			log("entity.identifier OK");
		} else {
			log("entity.identifier missing");
		}
		
		super.validate(entity);
	}

}
