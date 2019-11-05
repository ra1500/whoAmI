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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

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

        // save a child. (in manyTomany).
        String title = "lovin it";
        Long questionSetVersion = new Long(1);
        QuestionSetVersionEntity questionSetVersionEntity = new QuestionSetVersionEntity(title, questionSetVersion);
        QuestionSetVersionEntity savedQuestionSetVersionEntity = questionSetVersionDAO.saveAndFlush(questionSetVersionEntity);
        QuestionSetVersionEntity foundQuestionSetVersionEntity = questionSetVersionDAO.findOneByQuestionSetVersion(questionSetVersion);

        // put child into a Set
        Set<QuestionSetVersionEntity> questionSetVersionEntities = new HashSet<>();
        questionSetVersionEntities.add(foundQuestionSetVersionEntity);

        // create a new parent with the child included
        String userName = "maria";
        String auditee = "karen";
        String profilePageGroup = "Public";
        String tbd = "tbd";
        PermissionsEntity permissionsEntity = new PermissionsEntity(questionSetVersionEntities, userName, auditee, profilePageGroup, questionSetVersion, tbd  );

        // save parent (which includes child)
        PermissionsEntity savedPermissionsEntity = permissionsRepositoryDAO.saveAndFlush(permissionsEntity);

        // retrieve the saved parent
        PermissionsEntity foundPermmissionsEntities = permissionsRepositoryDAO.findOneByUserNameAndAuditee(userName, auditee);

        // get from the saved parent the child set
        Set<QuestionSetVersionEntity> qsets =  foundPermmissionsEntities.getQuestionSetVersionEntities();

        // assert that retrieved parent's child's value is equal to the child in db
        Stream<QuestionSetVersionEntity> stream = qsets.stream();
        //stream.forEach(elem -> System.out.println(elem.getTitle()));
        Optional<QuestionSetVersionEntity> foundTitle = stream.findFirst();
        assertEquals(title, foundTitle.get().getTitle() );

    }


}
