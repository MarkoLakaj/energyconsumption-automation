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
public class DeleteMeterRaadingsForProfileTest {

	private static final String PROFILE_A = "A";
	private static final int CONNECTION_ID = 555333;
	FractionsProxy fractionsProxy = new FractionsProxy();
	MeterReadingProxy meterReadingProxy = new MeterReadingProxy();

	@BeforeMethod
	public void dataPreparation() {

		final List<Fractions> fractionList = new ArrayList<Fractions>();
		final List<MeterReading> meterReadingList = new ArrayList<MeterReading>();

		// Delete fractions for profile, if they exist to ensure clear state
		fractionsProxy.deleteFractionsForProfile(PROFILE_A);

		// Test data
		// Note - this is faulty test data, but since application applies incorrect
		// formula
		// for retrieving meter reading, this is considered a WORKAROUND in order to
		// test DELETE method for meter readings
		fractionList.add(new Fractions(0.00, new MonthProfileKey(Environment.MONTH_JAN, PROFILE_A)));
		fractionList.add(new Fractions(0.00, new MonthProfileKey(Environment.MONTH_FEB, PROFILE_A)));
		fractionList.add(new Fractions(0.00, new MonthProfileKey(Environment.MONTH_MAR, PROFILE_A)));
		fractionList.add(new Fractions(0.00, new MonthProfileKey(Environment.MONTH_APR, PROFILE_A)));
		fractionList.add(new Fractions(0.00, new MonthProfileKey(Environment.MONTH_MAY, PROFILE_A)));
		fractionList.add(new Fractions(0.00, new MonthProfileKey(Environment.MONTH_JUN, PROFILE_A)));
		fractionList.add(new Fractions(0.00, new MonthProfileKey(Environment.MONTH_JUL, PROFILE_A)));
		fractionList.add(new Fractions(0.00, new MonthProfileKey(Environment.MONTH_AUG, PROFILE_A)));
		fractionList.add(new Fractions(0.00, new MonthProfileKey(Environment.MONTH_SEP, PROFILE_A)));
		fractionList.add(new Fractions(0.00, new MonthProfileKey(Environment.MONTH_OCT, PROFILE_A)));
		fractionList.add(new Fractions(0.00, new MonthProfileKey(Environment.MONTH_NOV, PROFILE_A)));
		fractionList.add(new Fractions(1.00, new MonthProfileKey(Environment.MONTH_DEC, PROFILE_A)));

		meterReadingList.add(new MeterReading(CONNECTION_ID, 0, new MonthProfileKey(Environment.MONTH_JAN, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0, new MonthProfileKey(Environment.MONTH_FEB, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0, new MonthProfileKey(Environment.MONTH_MAR, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0, new MonthProfileKey(Environment.MONTH_APR, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0, new MonthProfileKey(Environment.MONTH_MAY, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0, new MonthProfileKey(Environment.MONTH_JUN, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0, new MonthProfileKey(Environment.MONTH_JUL, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0, new MonthProfileKey(Environment.MONTH_AUG, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0, new MonthProfileKey(Environment.MONTH_SEP, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0, new MonthProfileKey(Environment.MONTH_OCT, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0, new MonthProfileKey(Environment.MONTH_NOV, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 360, new MonthProfileKey(Environment.MONTH_DEC, PROFILE_A)));

		// Add fractions for specific profile
		fractionsProxy.addFractions(fractionList);
		// Add meter readings for specific profile
		meterReadingProxy.addMeterReadings(meterReadingList);
		// Clear the list
		meterReadingList.clear();

	}

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("It should be possible to delete meter readings for specific profile")
	public void deleteMeterReadingsForProfile_meterReadingsDeleted() {

		final Response response = meterReadingProxy.deleteMeterReadingsForProfile(PROFILE_A);
		final SoftAssertions soft = new SoftAssertions();

		// Assert correct response message
		assertThat(response.asString()).as("Response message is not Success").isEqualTo("Success");
		// Assert correct status code 200
		assertThat(response.statusCode()).as("Incorrect status code, should be 200")
				.isEqualTo(Environment.STATUS_CODE_200);
		// Assert that fractions for specific profile have been deleted
		assertThat(meterReadingProxy.getMeterReadingsForProfile(PROFILE_A))
				.as("Meter readings for profile " + PROFILE_A + " have not been deleted").isEmpty();
		soft.assertAll();

	}

	@AfterMethod
	public void dataDeletion() {

		// Delete data to ensure clear state
		fractionsProxy.deleteFractionsForProfile(PROFILE_A);
		meterReadingProxy.deleteMeterReadingsForProfile(PROFILE_A);
	}

}
