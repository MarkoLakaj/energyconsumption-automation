package pojos;

public class MeterReading {

	private int connectionId;
	private double meterReading;
	private MonthProfileKey monthProfileKey;

	public MeterReading() {

	}


	public MeterReading(final int connectionId, final double meterReading, final MonthProfileKey monthProfileKey) {
		this.connectionId = connectionId;
		this.meterReading = meterReading;
		this.monthProfileKey = monthProfileKey;
	}

	public int getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(final int connectionId) {
		this.connectionId = connectionId;
	}

	public double getMeterReading() {
		return meterReading;
	}

	public void setMeterReading(final double meterReading) {
		this.meterReading = meterReading;
	}

	public MonthProfileKey getMonthProfileKey() {
		return monthProfileKey;
	}

	public void setMonthProfileKey(final MonthProfileKey monthProfileKey) {
		this.monthProfileKey = monthProfileKey;
	}

}
