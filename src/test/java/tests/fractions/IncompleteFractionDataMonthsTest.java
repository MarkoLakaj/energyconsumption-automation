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
public class IncompleteFractionDataMonthsTest {

	private static final String PROFILE_A = "A";
	FractionsProxy fractionsProxy = new FractionsProxy();

	@BeforeMethod
	public void dataPreparation() {

		// Delete data for profile to ensure clear state before
		// executing test case
		fractionsProxy.deleteFractionsForProfile(PROFILE_A);
	}

	@Test
	@Severity(SeverityLevel.NORMAL)
	@Description("It must not be possible to upload partial data for fractions - incomplete list of months")
	public void addIncompleteDataForMonths_validationErrorThrown() {

		final List<Fractions> fractionList = new ArrayList<Fractions>();
		final SoftAssertions soft = new SoftAssertions();

		// Test data
		fractionList.add(new Fractions(0.15, new MonthProfileKey(Environment.MONTH_JAN, PROFILE_A)));
		fractionList.add(new Fractions(0.17, new MonthProfileKey(Environment.MONTH_FEB, PROFILE_A)));
		fractionList.add(new Fractions(0.13, new MonthProfileKey(Environment.MONTH_MAR, PROFILE_A)));


		// Add incomplete fractions for specific profile
		final Response response = fractionsProxy.addFractions(fractionList);

		// Assert correct validation error
		assertThat(response.asString()).as("Validation error not according to specification")
				.isEqualTo("Validation error : Profile " + PROFILE_A + " must have 12 months");
		// Assert correct status code
		assertThat(response.statusCode()).as("Status code should be 400, but it is not")
				.isEqualTo(Environment.STATUS_CODE_400);
		// Assert no data has been partially written
		assertThat(fractionsProxy.getFractionsForProfile(PROFILE_A))
				.as("Data should not be saved for profile " + PROFILE_A)
				.isEmpty();
		soft.assertAll();
	}

}
