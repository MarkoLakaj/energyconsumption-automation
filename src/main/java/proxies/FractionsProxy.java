package proxies;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import common.Environment;
import common.RestAssuredBaseClient;
import io.restassured.response.Response;
import pojos.Fractions;


public class FractionsProxy extends RestAssuredBaseClient {

	private static final int NUMBER_OF_MONTHS = 12;
	private static final String PROFILE_A = "A";

	public FractionsProxy() {
	}

	public FractionsProxy getFractionsProxy() {
		return new FractionsProxy();
	}

	public Fractions[] getFractionsForProfile(final String profile) {

		final Response response = get(Environment.BASE_FRACTIONS + profile);
		final Fractions[] fractions = response.as(Fractions[].class);
		return fractions;
	}

	public Response addFractions(final Object requestBody) {

		final Response response = post(Environment.BASE_FRACTIONS, requestBody);
		return response;
	}

	public Response updateFractions(final Object requestBody) {

		final Response response = put(Environment.BASE_FRACTIONS, requestBody);
		return response;
	}

	public Response deleteFractionsForProfile(final String profile) {

		final Response response = delete(Environment.BASE_FRACTIONS + profile);
		return response;
	}

	public boolean isFractionSumValid() {

		// final FractionsProxy fractionProxy = new FractionsProxy();
		final List<Fractions> fractionsList = Arrays
				.asList(getFractionsProxy().getFractionsForProfile(PROFILE_A));
		double sum = 0;

		for (final Fractions fraction : fractionsList) {

			sum += fraction.getFraction();
		}
		return (sum == 1);
	}

	public double getFractionForSpecificMonth(final String month) {


		final List<Fractions> fractionsList = Arrays
				.asList(getFractionsProxy().getFractionsForProfile(PROFILE_A));
		final Fractions januaryFraction = fractionsList.stream()
				.filter(item -> item.getMonthProfileKey().getMonth().equals(month)).collect(Collectors.toList()).get(0);
		return januaryFraction.getFraction();

	}

	public boolean allFractionsPresentForProfile(final String profile) {

		final List<Fractions> fractionsList = Arrays
				.asList(getFractionsProxy().getFractionsForProfile(PROFILE_A));
		return (fractionsList.size() == NUMBER_OF_MONTHS);
	}

}
