package model;

public record AuthorizationResponse(String userToken,String role, String displayName, String login) {

}
