package common;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class common {
	public static String userid = null;

	public static Response getMethod(String baseurl, String path) {
		RestAssured.baseURI = baseurl;
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, path);
		return response;
	}

	public static boolean isValidEmailAddress(String email) {
		String pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	public static String getUserIDFromName(Response response, String username) {
		List<Map<String, String>> users = response.jsonPath().getList("$");

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).get("username").equalsIgnoreCase(username)) {
				userid = String.valueOf(users.get(i).get("id"));
			}
		}
		return userid;
	}

	public static Response postMethod(String baseurl, String path) {
		RequestSpecification request = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		requestParams.put("userId", "1");
		requestParams.put("id", "1");
		requestParams.put("title", "testpost");
		requestParams.put("body", "testbody");

		request.header("Content-Type", "application/json"); // Add the Json to the body of the request
		request.body(requestParams.toJSONString());

		Response response = request.post(baseurl + path);

		return response;
	}

	public static Response putMethod(String baseurl, String path) {
		RestAssured.baseURI = baseurl;
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.PUT, path);
		return response;
	}

	public static Response deleteMethod(String baseurl, String path) {
		RestAssured.baseURI = baseurl;
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.DELETE, path);
		return response;
	}

	// This method will read json file and will return JsonObject
	public static JSONObject readJSON(String path) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(path));

		JSONObject jsonObject = (JSONObject) obj;
		return jsonObject;
	}
}