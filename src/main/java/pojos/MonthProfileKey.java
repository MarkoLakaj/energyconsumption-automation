package pojos;

public class MonthProfileKey {

	private String month;
	private String profile;

	public MonthProfileKey() {

	}

	public MonthProfileKey(final String month, final String profile) {

		this.month = month;
		this.profile = profile;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(final String month) {
		this.month = month;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(final String profile) {
		this.profile = profile;
	}

}
