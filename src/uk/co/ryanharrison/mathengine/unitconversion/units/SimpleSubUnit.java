package uk.co.ryanharrison.mathengine.unitconversion.units;

import uk.co.ryanharrison.mathengine.BigRational;

public class SimpleSubUnit extends SubUnit
{
	private BigRational conversion;

	public BigRational getConversion()
	{
		return conversion;
	}

	public void setConversion(BigRational conversion)
	{
		this.conversion = conversion;
	}
}
