package tests.meterreadings;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import common.Environment;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import pojos.Fractions;
import pojos.MeterReading;
import pojos.MonthProfileKey;
import proxies.FractionsProxy;
import proxies.MeterReadingProxy;

@Feature("MeterReadings")
public class AddMeterReadingsForExistingProfileTest {

	private static final String PROFILE_A = "A";
	private static final int CONNECTION_ID = 555333;
	FractionsProxy fractionsProxy = new FractionsProxy();
	MeterReadingProxy meterReadingProxy = new MeterReadingProxy();

	@BeforeMethod
	public void dataPreparation() {

		final List<Fractions> fractionList = new ArrayList<Fractions>();

		// Delete fractions for profile, if they exist to ensure clear state
		fractionsProxy.deleteFractionsForProfile(PROFILE_A);

		// Test data
		fractionList.add(new Fractions(0.15, new MonthProfileKey(Environment.MONTH_JAN, PROFILE_A)));
		fractionList.add(new Fractions(0.17, new MonthProfileKey(Environment.MONTH_FEB, PROFILE_A)));
		fractionList.add(new Fractions(0.13, new MonthProfileKey(Environment.MONTH_MAR, PROFILE_A)));
		fractionList.add(new Fractions(0.08, new MonthProfileKey(Environment.MONTH_APR, PROFILE_A)));
		fractionList.add(new Fractions(0.08, new MonthProfileKey(Environment.MONTH_MAY, PROFILE_A)));
		fractionList.add(new Fractions(0.00, new MonthProfileKey(Environment.MONTH_JUN, PROFILE_A)));
		fractionList.add(new Fractions(0.00, new MonthProfileKey(Environment.MONTH_JUL, PROFILE_A)));
		fractionList.add(new Fractions(0.01, new MonthProfileKey(Environment.MONTH_AUG, PROFILE_A)));
		fractionList.add(new Fractions(0.04, new MonthProfileKey(Environment.MONTH_SEP, PROFILE_A)));
		fractionList.add(new Fractions(0.09, new MonthProfileKey(Environment.MONTH_OCT, PROFILE_A)));
		fractionList.add(new Fractions(0.10, new MonthProfileKey(Environment.MONTH_NOV, PROFILE_A)));
		fractionList.add(new Fractions(0.15, new MonthProfileKey(Environment.MONTH_DEC, PROFILE_A)));

		// Add fractions for specific profile
		fractionsProxy.addFractions(fractionList);

	}

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("It should be possible to add meter readings for specific profile")
	public void addMeterReadingsForSpecificProfile_meterReadingsAdded() {

		final List<MeterReading> meterReadingList = new ArrayList<MeterReading>();
		final SoftAssertions soft = new SoftAssertions();

		meterReadingList.add(new MeterReading(CONNECTION_ID, 54, new MonthProfileKey(Environment.MONTH_JAN, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 115.2, new MonthProfileKey(Environment.MONTH_FEB, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 162, new MonthProfileKey(Environment.MONTH_MAR, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 190.8, new MonthProfileKey(Environment.MONTH_APR, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 219.6, new MonthProfileKey(Environment.MONTH_MAY, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 219.6, new MonthProfileKey(Environment.MONTH_JUN, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 219.6, new MonthProfileKey(Environment.MONTH_JUL, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 223.2, new MonthProfileKey(Environment.MONTH_AUG, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 237.6, new MonthProfileKey(Environment.MONTH_SEP, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 270, new MonthProfileKey(Environment.MONTH_OCT, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 306, new MonthProfileKey(Environment.MONTH_NOV, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 360, new MonthProfileKey(Environment.MONTH_DEC, PROFILE_A)));

		final Response response = meterReadingProxy.addMeterReadings(meterReadingList);

		// Assert correct validation message text
		assertThat(response.asString()).as("Incorrect validation message").contains("Success");
		// Assert correct status code
		assertThat(response.statusCode()).as("Status code must be 200").isEqualTo(Environment.STATUS_CODE_200);
		// Assert no meter reading have been created
		assertThat(meterReadingProxy.getMeterReadingsForProfile(PROFILE_A))
				.as("There should be no meter for profile " + PROFILE_A).isNotEmpty();
		soft.assertAll();
	}

	@AfterMethod
	public void dataDeletion() {

		// Delete data to ensure clear state
		fractionsProxy.deleteFractionsForProfile(PROFILE_A);
		meterReadingProxy.deleteMeterReadingsForProfile(PROFILE_A);
	}

}
