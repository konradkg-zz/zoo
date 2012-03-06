package zoo.pl.validator.chain;

import zoo.pl.validator.visitor.domain.Creditor;
import zoo.pl.validator.visitor.domain.Debtor;
import zoo.pl.validator.visitor.domain.Entity;
import zoo.pl.validator.visitor.domain.FinancialObligation;
import zoo.pl.validator.visitor.domain.Pex;
import zoo.pl.validator.visitor.domain.RelatedEntity;

public interface ChainedValidator {
	
	public abstract void validate(Entity entity);
	public abstract void validate(Creditor creditor);
    public abstract void validate(Debtor debtor);
    public abstract void validate(Pex pex);
    public abstract void validate(RelatedEntity relatedEntity);
    public abstract void validate(FinancialObligation financialObligation);
    
    public ChainedValidator setNext(ChainedValidator validator);
    
    public void reset();

}
