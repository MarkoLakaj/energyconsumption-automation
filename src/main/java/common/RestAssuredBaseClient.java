package common;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredBaseClient {

	private final RequestSpecification request = RestAssured.given();
	private final Gson gson = new Gson();

	public RestAssuredBaseClient() {
		configuration();
		getRequestSpec();
	}

	public RequestSpecification getRequestSpec() {
		return request;
	}

	public Gson getGson() {
		return gson;
	}

	public void configuration() {
		getRequestSpec().baseUri(Environment.BASE_URI);
		getRequestSpec().header(Environment.CONTENT_TYPE, Environment.APPLICATION_JSON);
	}

	public Response get(final String path) {

		final Response response = getRequestSpec().
				contentType(Environment.APPLICATION_JSON).
				when().
					get(path).
				then().
					log().all().
				extract().
					response();

		return response;

	}

	public Response post(final String path, final Object body) {

		final String bodyToJson = getGson().toJson(body);

		final Response response = getRequestSpec().
				contentType(Environment.APPLICATION_JSON).
					body(bodyToJson).
				when().
					post(path).
				then().
					log().all().
				extract().
					response();
		return response;

	}

	public Response put(final String path, final Object body) {

		final String bodyToJson = getGson().toJson(body);

		final Response response = getRequestSpec().
				contentType(Environment.APPLICATION_JSON).
					body(bodyToJson).
				when().
					put(path).
				then().
					log().all().
				extract().
					response();
		return response;

	}

	public Response delete(final String path) {


		final Response response = getRequestSpec().
				contentType(Environment.APPLICATION_JSON).
				when().
					delete(path).
				then().
					log().all().
				extract().
					response();
		return response;

	}



}
