package db.repositorytest;


import db.config.DBconfiguration;
import db.config.HibernateProperties;
import db.entity.PermissionsEntity;
import db.entity.QuestionSetVersionEntity;
import db.repository.PermissionsRepositoryDAO;
import db.repository.QuestionSetVersionRepositoryDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:TestConfiguration.properties")
@DataJpaTest
@ContextConfiguration(classes = {Environment.class, PermissionsEntity.class, PermissionsEntityTest.class, DBconfiguration.class, HibernateProperties.class})
@ComponentScan({"db"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PermissionsEntityTest {

    @Autowired
    PermissionsRepositoryDAO permissionsRepositoryDAO;

    @Autowired
    QuestionSetVersionRepositoryDAO questionSetVersionDAO;


    @Test
    public void crudTest() {
        String userName = "maria";
        String auditee = "karen";
        String title = "lovin it";
        Long questionSetVersion = new Long(1);

        QuestionSetVersionEntity questionSetVersionEntity = new QuestionSetVersionEntity(title, questionSetVersion);
        QuestionSetVersionEntity savedQuestionSetVersionEntity = questionSetVersionDAO.saveAndFlush(questionSetVersionEntity);
        System.out.println("Qset Title and Version:");
        System.out.println(savedQuestionSetVersionEntity.getTitle());
        System.out.println(savedQuestionSetVersionEntity.getQuestionSetVersion());

        PermissionsEntity permissionsEntity = new PermissionsEntity(userName, auditee, questionSetVersion );
        PermissionsEntity savedPermissionsEntity = permissionsRepositoryDAO.saveAndFlush(permissionsEntity);
        System.out.println("Permissions userName and Qsets:");
        System.out.println(savedPermissionsEntity.getUserName());
        System.out.println(savedPermissionsEntity.getQuestionSetVersionEntities());
        System.out.println("Other methods....");
        System.out.println(permissionsRepositoryDAO.findAllByUserName(userName));



    }


}
