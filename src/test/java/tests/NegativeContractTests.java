package tests;

import data.EmployeeAndCompanyData;
import helper.AuthHelper;
import helper.RequestHelper;
import helper.ResponseHelper;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import model.AuthorizationResponse;
import model.CreateEmployeeRequest;
import model.EntityManagerClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static helper.RequestHelper.*;
import static helper.ResponseHelper.*;
import static io.restassured.RestAssured.given;

public class NegativeContractTests {
    AuthorizationResponse info = RequestHelper.auth();
    private static EntityManager entityManager;

    public NegativeContractTests() throws IOException {

    }

    @BeforeAll
    public static void setUp() throws IOException {
        NegativeContractTests.entityManager = EntityManagerClass.setUp();
        AuthHelper.restAssuredSetUp();

    }

    @Test
    @DisplayName("Получение списка сотрудников для несуществующей компании")
    public void iCanGetListOfEmployeesOfNotExistedCompany() {

        given()
                .basePath("employee")
                .when()
                .get("{company}", EmployeeAndCompanyData.NONEXISTING_COMPANY_ID)
                .then()
                .assertThat()
                .statusCode(ResponseHelper.OK);
    }

    @Test
    @DisplayName("Попытка создания сотрудника с некорректным телом")
    public void iCanCreateEmployeeWithStrangeBody() throws IOException {

        given()
                .basePath("employee")
                .body(CreateEmployeeRequest.class.getName())
                .header(TOKEN_TYPE, info.userToken())
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(BAD_REQUEST);
    }

    @Test
    @DisplayName("Попытка создания сотрудника c неверным токеном")
    public void iCanCreateEmployeeWrongToken() throws IOException {

        given()
                .basePath("employee")
                .body(createNewCompanyWithEmployees())
                .header(TOKEN_TYPE, BAD_TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(BAD_REQUEST);
    }

    @Test
    @DisplayName("Попытка создания сотрудника без токена")
    public void iCanCreateEmployeeNoToken() {

        given()
                .basePath("employee")
                .body(createEmployeeRequest(new CreateEmployeeRequest(), fullFields))
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(UNAUTHORIZED);
    }
}
