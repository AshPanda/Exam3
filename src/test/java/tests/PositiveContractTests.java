package tests;


import data.EmployeeAndCompanyData;

import helper.AuthHelper;
import helper.RequestHelper;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import model.AuthorizationResponse;
import model.CreateEmployeeRequest;
import model.EntityManagerClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static helper.DBHelper.*;

import static helper.RequestHelper.*;
import static helper.ResponseHelper.*;
import static io.restassured.RestAssured.given;


public class PositiveContractTests {

    AuthorizationResponse info = RequestHelper.auth();
    private static EntityManager entityManager;


    public PositiveContractTests() throws IOException {

    }

    @BeforeAll
    public static void setUp() throws IOException {
        PositiveContractTests.entityManager = EntityManagerClass.setUp();
        AuthHelper.restAssuredSetUp();

    }

    @Test
    @DisplayName("Успешное создание сотрудника")
    public void iCanCreateEmployee() throws IOException {
//        String key = AuthHelper.getToken();
//        System.out.println(key);
//                RestAssured.registerParser("text/plain", Parser.JSON);
//        RestAssured.defaultParser = Parser.JSON;

        given()
                .queryParam("company", createNewCompanyWithEmployees())
                .basePath("employee")
                .body(createEmployeeRequest(new CreateEmployeeRequest(), mandatoryFields))
                .header(TOKEN_TYPE, info.userToken())
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(CREATED)
                .header("Content-Type", JSON);

    }
    @Test
    @DisplayName("Успешное получение сотрудника по Id")
    public void iCanGetEmployeeById() throws IOException {


        given()
                .basePath("employee")
                .when()
                .get("{id}", createEmployeeInDB())
                .then()
                .assertThat()
                .statusCode(OK)
                .header("Content-Type", JSON);

    }

    @Test
    @DisplayName("Успешное получение списка сотрудников")
    public void iCanGetAListOfEmployees() {


        given()
                .queryParam("company", EmployeeAndCompanyData.NEW_COMPANY_ID)
                .basePath("employee")
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(OK)
                .header("Content-Type", JSON);
    }


    @Test
    @DisplayName("Изменение информации сотрудника")
    public void iCanChangeEmployeeData() throws IOException {

        given()
                .basePath("employee")
                .body(createEmployeeRequest(new CreateEmployeeRequest(), mandatoryFields))
                .header(TOKEN_TYPE, info.userToken())
                .contentType(ContentType.JSON)
                .when()
                .patch("{id}", createEmployeeInDB())
                .then()
                .assertThat()
                .statusCode(OK)
                .header("Content-Type", JSON);
    }

}
