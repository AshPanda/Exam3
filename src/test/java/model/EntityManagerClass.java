package model;

import helper.PersistenceUnitInfoClass;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EntityManagerClass {

    public static EntityManager setUp() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/config.properties");
        Properties properties = new Properties();
        properties.load(fileInputStream);

        PersistenceUnitInfo persistenceUnitInfo = new PersistenceUnitInfoClass(properties);

        HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();

        EntityManagerFactory entityManagerFactory = hibernatePersistenceProvider.createContainerEntityManagerFactory(persistenceUnitInfo, persistenceUnitInfo.getProperties());


        return entityManagerFactory.createEntityManager();
    }
}
