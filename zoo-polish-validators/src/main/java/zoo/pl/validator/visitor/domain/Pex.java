package zoo.pl.validator.visitor.domain;

import java.math.BigDecimal;
import java.util.Date;

import zoo.pl.validator.visitor.FinancialObligationElement;
import zoo.pl.validator.visitor.FinancialObligationElementsVisitor;

public class Pex implements FinancialObligationElement{
	public BigDecimal amount = new BigDecimal("20000.99");
	public Date date = new Date();
	
	@Override
	public void accept(FinancialObligationElementsVisitor visitor) {
		visitor.visit(this);
	}
}
