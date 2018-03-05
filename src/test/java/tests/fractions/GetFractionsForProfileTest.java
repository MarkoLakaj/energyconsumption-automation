package tests.fractions;

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
import pojos.MonthProfileKey;
import proxies.FractionsProxy;

@Feature("Fractions")
public class GetFractionsForProfileTest {

	private static final String PROFILE_A = "A";
	FractionsProxy fractionsProxy = new FractionsProxy();

	@BeforeMethod
	public void dataPreparation() {

		// Delete data for profile to ensure clear state before
		// executing test case
		fractionsProxy.deleteFractionsForProfile(PROFILE_A);

		final List<Fractions> fractionList = new ArrayList<Fractions>();

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
	@Description("It should be possible to retrieve fractions for specific profile")
	public void getFractionsForProfile_allFractionsPresent() {

		assertThat(fractionsProxy.allFractionsPresentForProfile(PROFILE_A))
				.as("Missing fractions for profile " + PROFILE_A)
				.isTrue();
	}

	@AfterMethod
	public void dataDeletion() {

		// Delete data to ensure clear state
		fractionsProxy.deleteFractionsForProfile(PROFILE_A);
	}

}
