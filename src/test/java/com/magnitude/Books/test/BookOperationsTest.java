package com.magnitude.Books.test;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BookOperationsTest {

	//This endpoint can be placed in a property file for ease of access/modification
	String endpoint = "http://5bff98560296210013dc7de9.mockapi.io/dummyAPI/v1/books/";
	
	//Below parameter "id" is used acrossthis test and can be parameterised to assert for multiple values
	String id = "109";

	
	//Fetch Book details as response from id 
	public Response getBooksResponse(String id) {

		Response response = given().when().get(endpoint + id).then().contentType(ContentType.JSON).extract().response();
		return response;
	}

	
	/**
	 * Test Get Books by Id by providing valid Id
	 */
	@Test(priority=1)
	public void testGetBooksById() {
		Response booksResponse = getBooksResponse(id);
		if (booksResponse.statusCode() == 200)
			Assert.assertTrue(validation(booksResponse));
		else
			Assert.fail("Test failed due to Unexpected result");
	}

	/**
	 * Test Get Books by Id by providing invalid Id i.e., Id which does not exists.
	 */
	@Test(priority=1)
	public void testGetBooksByInvalidId() {
		Response booksResponse = getBooksResponse("100000");
		if (booksResponse.statusCode() == 404) 
			Assert.assertEquals("\"Not found\"", booksResponse.body().asString());
		else
			Assert.fail("Test failed due to Unexpected result");
	}
	
	/**
	 * Validation of Get Books by Id based on the response. 
	 * Extract each item into a map and 
	 * Compare each item from each map to identify if the data is same across the maps i.e., name, ISPublic, createdDate
	 * @param resp
	 * @return
	 */
	public boolean validation(Response resp) {
		Map<String, String> nameDetails = resp.jsonPath().getMap("name");
		Map<String, String> publicDetails = resp.jsonPath().getMap("iSPublic");
		Map<String, String> dateDetails = resp.jsonPath().getMap("createdDate");
		boolean compare = nameDetails.equals(publicDetails);
		boolean newCompare = compare && nameDetails.equals(dateDetails);
		return newCompare;

	}

	/**
	 * Test PUT operation on Book i.e., Update book details based on its Id.
	 * Ex: Update the name of the book to Mario + random number
	 * Vali
	 */
	@Test(priority=1)
	public void testUpdateBooksById() {
		String newName = "Mario " + Math.random();

		Map<String, String> inputPayload = new HashMap<String, String>();
		inputPayload.put("name", newName);

		Response response = given().contentType("application/json").body(inputPayload).when().put(endpoint + id).then()
				.statusCode(200).contentType(ContentType.JSON).extract().response();
		Assert.assertEquals(newName, response.jsonPath().getString("name.name"));
		Assert.assertTrue(validation(getBooksResponse(id)));
	}

	/**
	 * Test Delete Operations on Book by Id
	 * Validate delete by fetching the book by id which will throw 404 exception.
	 */

	@Test(priority=2)
	public void testDeleteBooksById() {

		given().when().delete(endpoint + id).then().statusCode(200);
		Assert.assertEquals(404, getBooksResponse(id).statusCode());
	}

	// public Response addBooksResponse(String id) {
	//
	// Response response = given().when().post(endpoint +
	// id).then().contentType(ContentType.JSON).extract()
	// .response();
	// return response;
	// }

}
