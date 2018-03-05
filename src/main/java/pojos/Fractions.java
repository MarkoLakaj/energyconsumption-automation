package pojos;

public class Fractions {

	private double fraction;
	private MonthProfileKey monthProfileKey;

	public Fractions() {

	}

	public Fractions(final double fraction, final MonthProfileKey monthProfileKey) {

		this.fraction = fraction;
		this.monthProfileKey = monthProfileKey;
	}

	public Double getFraction() {
		return fraction;
	}

	public void setFraction(final Double fraction) {
		this.fraction = fraction;
	}

	public MonthProfileKey getMonthProfileKey() {
		return monthProfileKey;
	}

	public void setMonthProfileKey(final MonthProfileKey monthProfileKey) {
		this.monthProfileKey = monthProfileKey;
	}





}
