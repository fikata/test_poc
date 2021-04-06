package api;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import gherkin.lexer.Th;
import helpers.StoreData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.openqa.selenium.*;

import static webdriver.ChromeBrowser.driver;

import cucumber.api.java.en.Given;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.*;


public class Api {

    public static StoreData data = new StoreData();
    public static String baseURL = "https://www.demoqa.com/";
    public static String accountTokenURI = "Account/v1/GenerateToken";
    public static String accountAuthorizedURI = "/Account/v1/Authorized";
    public static String accountUserBooksURI = "/Account/v1/User";

    @Then("^I'm going to assert error message$")
    public static void tryToCreateUser() {
        RestAssured.baseURI ="https://www.demoqa.com";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("userName", "test"); // Cast
        requestParams.put("password", "mest");
        request.body(requestParams.toString());
        request.header("Content-Type","application/json");
        Response response = request.post("Account/v1/User");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 400);
        String successCode = response.jsonPath().get("message");
        Assert.assertTrue(successCode.contains("Passwords must have at least one non alphanumeric character"));
        //Assert.assertEquals( "UserName and Password required.", successCode, "OPERATION_SUCCESS");
    }

    @Then("^I'm going to assert error message without header$")
    public static void tryToCreateUserWithoutHeader() {
        RestAssured.baseURI ="https://www.demoqa.com";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("userName", "test"); // Cast
        requestParams.put("password", "mest");
        request.body(requestParams.toString());
        Response response = request.post("Account/v1/User");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 400);
        String successCode = response.jsonPath().get("message");
        Assert.assertEquals( "UserName and Password required.", successCode, "OPERATION_SUCCESS");
    }

    @Then("^I'm going to create user$")
    public static void createValidUser() {
        //StoreData data = new StoreData();
        RestAssured.baseURI ="https://www.demoqa.com";
        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();

        Random rand = new Random();
        int rand_int1 = rand.nextInt(10000);
        String user = "test" + rand_int1;
        System.out.println(user);
        data.addData("user",user);
        data.addData("pass","mestMest1!");
        requestParams.put("userName", data.getData("user"));
        requestParams.put("password", data.getData("pass"));


        request.body(requestParams.toString());
        request.header("Content-Type","application/json");
        Response response = request.post("Account/v1/User");

        int statusCode = response.getStatusCode();
        if (statusCode == 406) {
            String successCode = response.jsonPath().get("message");
            Assert.assertEquals( "User exists!", successCode);
        } else {
            Assert.assertEquals(statusCode, 201);
            String successCode = response.jsonPath().get("username");
            Assert.assertEquals( data.getData("user"), successCode);
            data.addData("uuid",response.jsonPath().get("userID"));
            System.out.println(data.getData("uuid"));
        }
    }

    public static String generateToken() {
        RestAssured.baseURI ="https://www.demoqa.com";
        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("userName", data.getData("user") );
        requestParams.put("password", data.getData("pass"));

        request.body(requestParams.toString());
        request.header("Content-Type","application/json");
        Response response = request.post("Account/v1/GenerateToken");
        System.out.println(response.getBody().asString());
        return response.jsonPath().get("token");
    }

    @Then("^I'm going to get user information$")
    public static Response getUserInfo() {
        RestAssured.baseURI ="https://www.demoqa.com";
        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("uuid", data.getData("user"));


        request.body(requestParams.toString());
        request.header("Content-Type","application/json");
        data.addData("token",generateToken());
        request.header("Authorization","Bearer " + data.getData("token"));

        Response response = request.get("Account/v1/User/" + data.getData("uuid"));

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        System.out.println(response.getBody().asString());
        Assert.assertEquals(response.jsonPath().get("username"),data.getData("user"));
        return response;
    }

    @Then("^Going to add book to the user$")
    public static void createBooks() {
        RestAssured.baseURI ="https://www.demoqa.com";
        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        data.addData("book1",getBookIsbn(1));

        requestParams.put("userId", data.getData("uuid"));
        requestParams.put("collectionOfIsbns", Arrays.asList(new LinkedHashMap<String, Object>() {
            {
                put("isbn", data.getData("book1"));
            }
        }));
        request.body(requestParams.toString());
        request.header("Content-Type","application/json");
        request.header("Authorization","Bearer " + data.getData("token"));

        Response response = request.post("BookStore/v1/Books/");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 201);
        System.out.println(response.getBody().asString());
        Assert.assertEquals(response.jsonPath().get("books[0].isbn"), data.getData("book1"));
    }

    @Then("^Going to get books data from user$")
    public static void getBooksFromUser() {
        Response response = getUserInfo();
        Assert.assertEquals(response.jsonPath().get("books[0].isbn"),data.getData("book1"));
    }

    @Then("^Going to remove book from user$")
    public static void deleteBookFromUser() {
        RestAssured.baseURI ="https://www.demoqa.com";
        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();

        requestParams.put("userId", data.getData("uuid"));
        requestParams.put("isbn", data.getData("book1"));
        request.body(requestParams.toString());
        request.header("Content-Type","application/json");
        request.header("Authorization","Bearer " + data.getData("token"));

        Response response = request.delete("BookStore/v1/Book");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 204);
        System.out.println(response.getBody().asString());
    }

    @Then("^Delete the user$")
    public static void deleteUser() {
        RestAssured.baseURI ="https://www.demoqa.com";
        RequestSpecification request = RestAssured.given();
//        JSONObject requestParams = new JSONObject();
//
//        requestParams.put("userId", data.getData("uuid"));
//        request.body(requestParams.toString());
        request.header("Content-Type","application/json");
        request.header("Authorization","Bearer " + data.getData("token"));
        String path = "Account/v1/User/" + data.getData("uuid");
        System.out.println(path);
        System.out.println(data.getData("token"));
        System.out.println();
        Response response = request.delete("Account/v1/User/" + data.getData("uuid"));
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 204);
        System.out.println(response.getBody().asString());
    }




    public static String getBookIsbn(int number) {
        RestAssured.baseURI ="https://www.demoqa.com";
        RequestSpecification request = RestAssured.given();
//        JSONObject requestParams = new JSONObject();
//        requestParams.put("uuid", data.getData("user"));
//
//
//        request.body(requestParams.toString());
        request.header("Content-Type","application/json");
        request.header("Authorization","Bearer " + data.getData("token"));

        Response response = request.get("BookStore/v1/Books");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
       // Assert.assertEquals(response.jsonPath().get("username"),data.getData("user"));

        List<String> allIsbn = response.jsonPath().getList("books.isbn");
        System.out.println(allIsbn.get(number));

        return allIsbn.get(number);
    }





}
