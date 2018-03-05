package proxies;

import java.util.Arrays;
import java.util.List;

import common.Environment;
import common.RestAssuredBaseClient;
import io.restassured.response.Response;
import pojos.MeterReading;

public class MeterReadingProxy extends RestAssuredBaseClient {

	private static final int NUMBER_OF_MONTHS = 12;

	public MeterReadingProxy() {
	}

	public MeterReadingProxy getMeterReadingProxy() {
		return new MeterReadingProxy();
	}

	public MeterReading[] getMeterReadingsForProfile(final String profile) {

		final Response response = get(Environment.BASE_METERREADING + profile);
		final MeterReading[] meterReadings = response.as(MeterReading[].class);
		return meterReadings;
	}

	public Response addMeterReadings(final Object requestBody) {

		final Response response = post(Environment.BASE_METERREADING, requestBody);
		return response;
	}

	public Response updateMeterReadings(final Object requestBody) {

		final Response response = put(Environment.BASE_METERREADING, requestBody);
		return response;
	}

	public Response deleteMeterReadingsForProfile(final String profile) {

		final Response response = delete(Environment.BASE_METERREADING + profile);
		return response;
	}

	public boolean allMeterReadingsPresentForProfile(final String profile) {

		final List<MeterReading> meterReadingList = Arrays
				.asList(getMeterReadingProxy().getMeterReadingsForProfile(profile));
		return (meterReadingList.size() == NUMBER_OF_MONTHS);
	}

}
