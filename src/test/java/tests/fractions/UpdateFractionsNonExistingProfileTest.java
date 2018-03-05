package tests.fractions;

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
import pojos.MonthProfileKey;
import proxies.FractionsProxy;

@Feature("Fractions")
public class UpdateFractionsNonExistingProfileTest {

	private static final String PROFILE_A = "A";
	FractionsProxy fractionsProxy = new FractionsProxy();

	@BeforeMethod
	public void dataPreparation() {

		// Delete data for profile to ensure clear state before
		// executing test case
		fractionsProxy.deleteFractionsForProfile(PROFILE_A);
	}

	@Test
	@Severity(SeverityLevel.MINOR)
	@Description("It should not be possible to update fractions for non existing profile")
	public void updateFractionsForNonExistingProfile_errorIsThrown() {

		final List<Fractions> fractionList = new ArrayList<Fractions>();
		final SoftAssertions soft = new SoftAssertions();

		// Update fractions with swapped values for JAN and FEB
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

		final Response response = fractionsProxy.updateFractions(fractionList);

		// Assert error message for non existing profile
		assertThat(response.asString()).as("Error message does not match with the one provided in specification")
				.isEqualTo(PROFILE_A + " profile does not exist");
		// Assert status code is 400
		assertThat(response.statusCode()).as("Status code is not 400").isEqualTo(Environment.STATUS_CODE_400);
		// Assert no actual data has been stored
		assertThat(fractionsProxy.getFractionsForProfile(PROFILE_A))
				.as("No data for profile " + PROFILE_A + " should exist, but it does").isEmpty();
		soft.assertAll();
	}

	@AfterMethod
	public void dataDeletion() {

		// Delete data to ensure clear state
		fractionsProxy.deleteFractionsForProfile(PROFILE_A);
	}

}
