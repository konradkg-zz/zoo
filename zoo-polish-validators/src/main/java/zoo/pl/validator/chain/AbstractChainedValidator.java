package zoo.pl.validator.chain;

import zoo.pl.validator.visitor.domain.Creditor;
import zoo.pl.validator.visitor.domain.Debtor;
import zoo.pl.validator.visitor.domain.Entity;
import zoo.pl.validator.visitor.domain.FinancialObligation;
import zoo.pl.validator.visitor.domain.Pex;
import zoo.pl.validator.visitor.domain.RelatedEntity;

public abstract class AbstractChainedValidator implements ChainedValidator {

	protected ChainedValidator next;

	public ChainedValidator setNext(ChainedValidator validator) {
		next = validator;
		return validator;
	}

	public void validate(Entity entity) {
		if (next != null)
			next.validate(entity);
	}

	public void validate(Creditor creditor) {
		if (creditor.entity != null)
			validate(creditor.entity);

		if (creditor.entities != null) {
			for (RelatedEntity r : creditor.entities) {
				validate(r);
			}
		}

		if (next != null)
			next.validate(creditor);
	}

	public void validate(Debtor debtor) {
		if (debtor.entity != null)
			validate(debtor.entity);

		if (debtor.entities != null) {
			for (RelatedEntity r : debtor.entities) {
				validate(r);
			}
		}
		if (next != null)
			next.validate(debtor);
	}

	public void validate(Pex pex) {
		if (next != null)
			next.validate(pex);
	}

	public void validate(RelatedEntity relatedEntity) {
		if (relatedEntity.entity != null)
			validate(relatedEntity.entity);

		if (next != null)
			next.validate(relatedEntity);
	}

	public void validate(FinancialObligation financialObligation) {
		if(financialObligation.creditor != null)
			validate(financialObligation.creditor);
		
		if(financialObligation.debtor != null)
			validate(financialObligation.debtor);
		
		if(financialObligation.pex != null)
			validate(financialObligation.pex);

		if (next != null)
			next.validate(financialObligation);
	}

	protected void log(String log) {
		System.out.println(getClass().getSimpleName() + ": " + log);
	}

}
