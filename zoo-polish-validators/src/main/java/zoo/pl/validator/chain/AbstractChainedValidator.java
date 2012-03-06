package zoo.pl.validator.chain;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

import zoo.pl.validator.visitor.domain.Creditor;
import zoo.pl.validator.visitor.domain.Debtor;
import zoo.pl.validator.visitor.domain.Entity;
import zoo.pl.validator.visitor.domain.FinancialObligation;
import zoo.pl.validator.visitor.domain.Pex;
import zoo.pl.validator.visitor.domain.RelatedEntity;

public abstract class AbstractChainedValidator implements ChainedValidator {

	protected ChainedValidator next;
	
	private AtomicBoolean callNext = new AtomicBoolean(true);
	
	protected Stack<Object> stack = new Stack<Object>();

	
	
	@Override
	public void reset() {
		callNext.set(true);
		
	}

	public ChainedValidator setNext(ChainedValidator validator) {
		next = validator;
		return validator;
	}

	public void validate(Entity entity) {
		stack.push(entity);
		boolean call = callNext.compareAndSet(true, false);
		
		if (next != null && call)
			next.validate(entity);
		
		stack.pop();
	}

	public void validate(Creditor creditor) {
		stack.push(creditor);
		boolean call = callNext.compareAndSet(true, false);
		
		if (creditor.entity != null)
			validate(creditor.entity);

		if (creditor.entities != null) {
			for (RelatedEntity r : creditor.entities) {
				validate(r);
			}
		}

		
		if (next != null && call)
			next.validate(creditor);
		
		stack.pop();
	}

	public void validate(Debtor debtor) {
		stack.push(debtor);
		boolean call = callNext.compareAndSet(true, false);
		
		if (debtor.entity != null)
			validate(debtor.entity);

		if (debtor.entities != null) {
			for (RelatedEntity r : debtor.entities) {
				validate(r);
			}
		}
		
		
		if (next != null && call)
			next.validate(debtor );
		
		stack.pop();
	}

	public void validate(Pex pex) {
		stack.push(pex);
		boolean call = callNext.compareAndSet(true, false);
		
		
		if (next != null && call)
			next.validate(pex);
		
		stack.pop();
	}

	public void validate(RelatedEntity relatedEntity) {
		stack.push(relatedEntity);
		boolean call = callNext.compareAndSet(true, false);
		
		if (relatedEntity.entity != null)
			validate(relatedEntity.entity);

		
		if (next != null && call)
			next.validate(relatedEntity);
		
		stack.pop();
	}

	public void validate(FinancialObligation financialObligation) {
		stack.push(financialObligation);
		boolean call = next != null && callNext.compareAndSet(true, false);
		
		if(financialObligation.creditor != null)
			validate(financialObligation.creditor);
		
		if(financialObligation.debtor != null)
			validate(financialObligation.debtor);
		
		if(financialObligation.pex != null)
			validate(financialObligation.pex);

		
		if (call)
			next.validate(financialObligation);
		
		stack.pop();
	}

	protected void log(String log) {
		System.out.println(getClass().getSimpleName() + ": " + log);
	}

}
