package zoo.pl.validator.visitor;

public interface FinancialObligationElement {
	
	void accept(FinancialObligationElementsVisitor visitor);
}
