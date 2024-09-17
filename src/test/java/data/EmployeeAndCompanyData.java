package data;

import helper.DBHelper;

import java.io.IOException;

public class EmployeeAndCompanyData {
    public static String COMPANY_NAME = "ООО \"СЛЕПАЯ КУРИЦА\"";
    public static String COMPANY_DESCRIPTION = "Ну как получилось";

    public static String FIRST_NAME = "Фиофан";
    public static String LAST_NAME = "Фалафель";
    public static String MIDDLE_NAME = "Феодосевич";

    public static String EMAIL = "kek@mail.ru";
    public static String AVATAR_URL = "kek.com";
    public static String PHONE = "+2(485)7942726";
    public static String BIRTHDAY = "1996-01-14T21:37:06";

    public static String CHANGED_EMAIL = "kek-cheburek@mail.ru";
    public static String CHANGED_AVATAR_URL = "keki.com";
    public static String CHANGED_PHONE = "+5(421)754-2723";
    public static String CHANGED_BIRTHDAY = "1996-01-14T21:37:06";
    public static String CHANGED_LAST_NAME = "DFJHkтпьапол";

    public static int NONEXISTING_COMPANY_ID = 99999999;
    public static int NEW_COMPANY_ID;

    static {
        try {
            NEW_COMPANY_ID = DBHelper.createNewCompanyInDB();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


}
