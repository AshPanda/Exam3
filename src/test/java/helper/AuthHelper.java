package helper;

import io.restassured.RestAssured;

import model.AuthorizationRequest;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;


public class AuthHelper {


    public static AuthorizationRequest login() {
        return new AuthorizationRequest(Objects.requireNonNull(getProperties()).getProperty("user"), getProperties().getProperty("password"));
    }

    public static Properties getProperties() {

        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/config.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);

            return properties;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public static String url() {

        return Objects.requireNonNull(getProperties()).getProperty("base_uri");
    }

    public static void restAssuredSetUp() throws IOException {

        RestAssured.baseURI = AuthHelper.url();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

}
