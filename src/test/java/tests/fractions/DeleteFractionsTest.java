package tests.fractions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
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
public class DeleteFractionsTest {

	private static final String PROFILE_A = "A";
	FractionsProxy fractionsProxy = new FractionsProxy();
	final List<Fractions> fractionList = new ArrayList<Fractions>();

	@BeforeMethod
	public void dataPreparation() {

		// Delete data for profile to ensure clear state before
		// executing test case
		fractionsProxy.deleteFractionsForProfile(PROFILE_A);

		// Load data for specific profile
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

		fractionsProxy.addFractions(fractionList);
		fractionList.clear();
	}

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("It should be possible to delete existing fractions for profile")
	public void deleteFractionsForSpecificProfile_fractionsDeleted() {

		final Response response = fractionsProxy.deleteFractionsForProfile(PROFILE_A);
		final SoftAssertions soft = new SoftAssertions();

		// Assert correct response message
		assertThat(response.asString()).as("Response message is not Success").isEqualTo("Success");
		// Assert correct status code 200
		assertThat(response.statusCode()).as("Incorrect status code, should be 200")
				.isEqualTo(Environment.STATUS_CODE_200);
		// Assert that fractions for specific profile have been deleted
		assertThat(fractionsProxy.getFractionsForProfile(PROFILE_A))
				.as("Fractions for profile " + PROFILE_A + " have not been deleted").isEmpty();
		soft.assertAll();
	}

}
