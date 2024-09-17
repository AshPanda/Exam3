package helper;

import data.EmployeeAndCompanyData;
import entity.CompanyEntity;
import entity.EmployeeEntity;
import jakarta.persistence.EntityManager;
import model.CreateEmployeeRequest;
import model.EntityManagerClass;

import java.io.IOException;
import java.util.function.Function;


public class DBHelper {

    private static EntityManager entityManager;

    public static void connectToDB() throws IOException {
        DBHelper.entityManager = EntityManagerClass.setUp();
    }

    public static int createNewCompanyInDB() throws IOException {
        connectToDB();
        CompanyEntity newCompany = new CompanyEntity();

        newCompany.setName(EmployeeAndCompanyData.COMPANY_NAME);
        newCompany.setDescription(EmployeeAndCompanyData.COMPANY_DESCRIPTION);
        newCompany.setActive(true);
        entityManager.getTransaction().begin();
        entityManager.persist(newCompany);
        entityManager.getTransaction().commit();
        return newCompany.getId();

    }

    public static CreateEmployeeRequest createEmployeeRequestDB(CreateEmployeeRequest employee, Function<CreateEmployeeRequest, CreateEmployeeRequest> mandatoryFields) {
        return (employee);
    }

    public static int createEmployeeInDB() throws IOException {
        connectToDB();
        int newCompanyId = createNewCompanyInDB();
        EmployeeEntity newEmployee = new EmployeeEntity();
        newEmployee.setFirstName(EmployeeAndCompanyData.FIRST_NAME);
        newEmployee.setLastName(EmployeeAndCompanyData.LAST_NAME);
        newEmployee.setPhone(EmployeeAndCompanyData.PHONE);
        newEmployee.setCompanyId(newCompanyId);
        newEmployee.setActive(true);
        entityManager.getTransaction().begin();
        entityManager.persist(newEmployee);
        entityManager.getTransaction().commit();
        return newEmployee.getId();
    }


    public static EmployeeEntity getEmployeeById(int employeeId) throws IOException {
        connectToDB();
        return entityManager.find(EmployeeEntity.class, employeeId);
        //return null;
    }

    public static void getCompanyById(int companyId) throws IOException {
        connectToDB();
        entityManager.find(CompanyEntity.class, companyId);
    }

    public static void deleteEmployeeFromDB(int employeeId) throws IOException {
        connectToDB();
        EmployeeEntity employee = entityManager.find(EmployeeEntity.class, employeeId);
        entityManager.getTransaction().begin();
        entityManager.remove(employee);
        entityManager.getTransaction().commit();
        entityManager.find(EmployeeEntity.class, employeeId);
    }

    public static void deleteCompanyFromDB(int companyId) throws IOException {
        connectToDB();
        CompanyEntity company = entityManager.find(CompanyEntity.class, companyId);
        entityManager.getTransaction().begin();
        entityManager.remove(company);
        entityManager.getTransaction().commit();
        entityManager.find(CompanyEntity.class, companyId);

    }

}
