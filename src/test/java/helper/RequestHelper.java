package helper;

import data.EmployeeAndCompanyData;
import io.restassured.http.ContentType;
import model.AuthorizationResponse;
import model.CreateEmployeeRequest;

import java.io.IOException;
import java.util.function.Function;

import static helper.AuthHelper.login;
import static helper.DBHelper.*;
import static helper.ResponseHelper.TOKEN_TYPE;
import static io.restassured.RestAssured.given;

public class RequestHelper {

    public static AuthorizationResponse auth() {

        return given()
                .basePath("/auth/login")
                .body(login())
                .contentType(ContentType.JSON)
                .when()
                .post()
                .as(AuthorizationResponse.class);

    }

    public static Function<CreateEmployeeRequest, CreateEmployeeRequest> mandatoryFields =
            new Function<CreateEmployeeRequest, CreateEmployeeRequest>() {
                @Override
                public CreateEmployeeRequest apply(CreateEmployeeRequest e) {
                    e.setFirstName(EmployeeAndCompanyData.FIRST_NAME);
                    e.setLastName(EmployeeAndCompanyData.LAST_NAME);
                    e.setCompanyId(EmployeeAndCompanyData.NEW_COMPANY_ID);
                    e.setEmail(EmployeeAndCompanyData.EMAIL);
                    e.setPhone(EmployeeAndCompanyData.PHONE);
                    e.setActive(true);
                    return e;
                }
            };

    public static Function<CreateEmployeeRequest, CreateEmployeeRequest> fullFields = e -> {
        e.setFirstName(EmployeeAndCompanyData.FIRST_NAME);
        e.setLastName(EmployeeAndCompanyData.LAST_NAME);
        e.setMiddleName(EmployeeAndCompanyData.MIDDLE_NAME);
        //e.setDate(EmployeeAndCompanyData.BIRTHDAY);
        e.setEmail(EmployeeAndCompanyData.EMAIL);
        e.setCompanyId(EmployeeAndCompanyData.NEW_COMPANY_ID);
        e.setPhone(EmployeeAndCompanyData.PHONE);
        e.setActive(true);
        e.setAvatarUrl(EmployeeAndCompanyData.AVATAR_URL);
        return e;
    };

    public static Function<CreateEmployeeRequest, CreateEmployeeRequest> employeeNoCompany =
            new Function<CreateEmployeeRequest, CreateEmployeeRequest>() {
                @Override
                public CreateEmployeeRequest apply(CreateEmployeeRequest e) {
                    e.setFirstName(EmployeeAndCompanyData.FIRST_NAME);
                    e.setLastName(EmployeeAndCompanyData.LAST_NAME);
                    e.setCompanyId(EmployeeAndCompanyData.NONEXISTING_COMPANY_ID);
                    e.setPhone(EmployeeAndCompanyData.PHONE);
                    e.setActive(true);
                    return e;
                }
            };

    public static CreateEmployeeRequest createEmployeeRequest(CreateEmployeeRequest employee,
                                                              Function<CreateEmployeeRequest, CreateEmployeeRequest> rule) {
        return rule.apply(employee);
    }

    public static int createNewCompanyWithEmployees() throws IOException {
        AuthorizationResponse info = auth();

        int newCompanyId = DBHelper.createNewCompanyInDB();
        int newEmployee = createEmployeeInDB();

        CreateEmployeeRequest fullEmployeeName = createEmployeeRequest(new CreateEmployeeRequest(), fullFields);
        fullEmployeeName.setCompanyId(newCompanyId);

        given()
                .basePath("employee")
                .body(fullEmployeeName)
                .header(TOKEN_TYPE, info.userToken())
                .contentType(ContentType.JSON)
                .when()
                .post();

        CreateEmployeeRequest mandatoryEmployeeName = createEmployeeRequest(new CreateEmployeeRequest(), mandatoryFields);
        mandatoryEmployeeName.setCompanyId(newCompanyId);

        given()
                .basePath("employee")
                .body(mandatoryEmployeeName)
                .header(TOKEN_TYPE, info.userToken())
                .contentType(ContentType.JSON)
                .when()
                .post();
        return newCompanyId;

    }

}
