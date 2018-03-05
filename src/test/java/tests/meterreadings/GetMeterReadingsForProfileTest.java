package tests.meterreadings;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import common.Environment;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pojos.Fractions;
import pojos.MeterReading;
import pojos.MonthProfileKey;
import proxies.FractionsProxy;
import proxies.MeterReadingProxy;

@Feature("MeterReadings")
public class GetMeterReadingsForProfileTest {

	public static final String PROFILE_A = "A";
	public static final int CONNECTION_ID = 555333;
	FractionsProxy fractionsProxy = new FractionsProxy();
	MeterReadingProxy meterReadingProxy = new MeterReadingProxy();


	@BeforeMethod
	public void dataPreparation() {

		final List<Fractions> fractionList = new ArrayList<Fractions>();
		final List<MeterReading> meterReadingList = new ArrayList<MeterReading>();
		// Delete fractions for profile, if they exist to ensure clear state
		fractionsProxy.deleteFractionsForProfile(PROFILE_A);

		// Test data
		// Note - this is faulty test data, but since application applies incorrect formula
		// for retrieving meter reading, this is considered a WORKAROUND in order to test GET method
		// for meter readings
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

		meterReadingList.add(new MeterReading(CONNECTION_ID, 0.00, new MonthProfileKey(Environment.MONTH_JAN, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0.00, new MonthProfileKey(Environment.MONTH_FEB, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0.00, new MonthProfileKey(Environment.MONTH_MAR, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0.00, new MonthProfileKey(Environment.MONTH_APR, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0.00, new MonthProfileKey(Environment.MONTH_MAY, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0.00, new MonthProfileKey(Environment.MONTH_JUN, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0.00, new MonthProfileKey(Environment.MONTH_JUL, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0.00, new MonthProfileKey(Environment.MONTH_AUG, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0.00, new MonthProfileKey(Environment.MONTH_SEP, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0.00, new MonthProfileKey(Environment.MONTH_OCT, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 0.00, new MonthProfileKey(Environment.MONTH_NOV, PROFILE_A)));
		meterReadingList.add(new MeterReading(CONNECTION_ID, 360.00, new MonthProfileKey(Environment.MONTH_DEC, PROFILE_A)));

		// Add fractions for specific profile
		fractionsProxy.addFractions(fractionList);
		// Add meter readings for specific profile
		meterReadingProxy.addMeterReadings(meterReadingList);
		// Clear the list
		meterReadingList.clear();

	}

	@Test
	@Severity(SeverityLevel.BLOCKER)
	@Description("It should be possible to retrieve meter readings for specific profile")
	public void getMeterReadingsForProfile_meterReadingsRetrieved() {

		// Assert correct amount of meters retrieved for specific profile
		assertThat(meterReadingProxy.allMeterReadingsPresentForProfile(PROFILE_A))
				.as("Insufficient amount of meters for profile " + PROFILE_A).isTrue();

	}

	@AfterMethod
	public void dataDeletion() {

		// Delete data to ensure clear state
		fractionsProxy.deleteFractionsForProfile(PROFILE_A);
		meterReadingProxy.deleteMeterReadingsForProfile(PROFILE_A);
	}

}
