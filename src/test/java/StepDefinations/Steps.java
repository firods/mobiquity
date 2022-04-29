package StepDefinations;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import common.common;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.junit.Assert;

public class Steps {
	Response response;
	public static JSONObject dataobj = null;
	public static Logger logger = null;
	public static String userid = null;

	@Given("User completed the initial setup")
	public void initialSetup() throws InterruptedException, IOException, ParseException {
		logger = Logger.getLogger("Steps");
		PropertyConfigurator.configure("log4j.properties");
		dataobj = common.readJSON(System.getProperty("user.dir") + "\\src\\test\\java\\data\\testdata.json");
	}

	@And("Verify email format from comment section for user {string}")
	public void getUserdetails(String username) throws Exception {

		response = common.getMethod((String) dataobj.get("url"), "/users");
		userid = common.getUserIDFromName(response, username);
		logger.info("user id = " + userid + "for username = " + username);

		if (!userid.isEmpty()) {
			response = common.getMethod((String) dataobj.get("url"), "/posts/" + userid + "/comments");

			List<Map<String, String>> emailList = response.jsonPath().getList("$");

			for (int i = 0; i < emailList.size(); i++) {
				logger.info("email = " + String.valueOf(emailList.get(i).get("email")));
				Assert.assertTrue("email format is incorrect",
						common.isValidEmailAddress(String.valueOf(emailList.get(i).get("email"))));
			}
		} else {
			throw new Exception("User id is empty for User = " + username);
		}
	}

	@Then("^User completed ([^\"]*) GET call ([^\"]*) with responseBody ([^\"]*)$")
	public void getCall(String path, String statuscode, boolean flag)
			throws InterruptedException, IOException, ParseException {
		response = common.getMethod((String) dataobj.get("url"), path);
		Assert.assertEquals("status code does not match actual = " + response.getStatusCode() + " and feature file = "
				+ Integer.parseInt(statuscode), response.getStatusCode(), Integer.parseInt(statuscode));
		logger.info("GET call completed with status code = " + response.getStatusCode());
		if (flag) {
			Assert.assertTrue(response.getBody().asString().equals("{}"));
		} else {
			Assert.assertTrue(!response.getBody().path("id").toString().isEmpty());
		}
	}

	@And("^User completed POST call ([^\"]*) and ([^\"]*)$")
	public void postComment(String path, String statuscode) throws InterruptedException, IOException, ParseException {

		response = common.postMethod((String) dataobj.get("url"), path);
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statuscode));
		logger.info("POST call completed with status code = " + response.getStatusCode());
	}

	@And("^User completed DELETE call ([^\"]*) and ([^\"]*)$")
	public void deleteCall(String path, String statuscode) throws InterruptedException, IOException, ParseException {

		response = common.deleteMethod((String) dataobj.get("url"), path);
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statuscode));
		logger.info("DELETE call completed with status code = " + response.getStatusCode());
	}
}
