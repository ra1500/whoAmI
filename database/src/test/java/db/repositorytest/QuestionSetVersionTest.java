package db.repositorytest;


import db.config.DBconfiguration;
import db.config.HibernateProperties;
import db.entity.QuestionSetVersionEntity;
import db.entity.QuestionsEntity;
import db.repository.QuestionSetVersionRepositoryDAO;
import db.repository.QuestionsRepositoryDAO;
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
@ContextConfiguration(classes = {Environment.class, QuestionSetVersionEntity.class, QuestionSetVersionTest.class, DBconfiguration.class, HibernateProperties.class})
@ComponentScan({"db"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class QuestionSetVersionTest {

    @Autowired
    QuestionSetVersionRepositoryDAO questionSetVersionDAO;

    @Autowired
    QuestionsRepositoryDAO questionsRepositoryDAO;

    @Test
    public void crudTest() {

        // save a child. (in OneToMany).
        String question = "working";
        Long id = new Long(1);
 //       QuestionsEntity questionsEntity = new QuestionsEntity(question);
 //       QuestionsEntity savedQuestionsEntity = questionsRepositoryDAO.saveAndFlush(questionsEntity);
 //       QuestionsEntity foundQuestionsEntity = questionsRepositoryDAO.findOneById(id);

        // put child into a Set
 //       Set<QuestionsEntity> questionsEntities = new HashSet<>();
 //       questionsEntities.add(foundQuestionsEntity);

        // create a new parent with the child included
        String title = "life";
        Long qsetversion = new Long(1);
 //       QuestionSetVersionEntity questionSetVersionEntity = new QuestionSetVersionEntity(questionsEntities, title, qsetversion );

        // save parent (which includes child)
 //       QuestionSetVersionEntity savedQuestionSetVersionEntity = questionSetVersionDAO.saveAndFlush(questionSetVersionEntity);

        // retrieve the saved parent
 //       QuestionSetVersionEntity foundQuestionSetVersionEntities = questionSetVersionDAO.findOneByQuestionSetVersion(savedQuestionSetVersionEntity.getQuestionSetVersion());

        // get from the saved parent the child set
 //       Set<QuestionsEntity> questionsSet =  foundQuestionSetVersionEntities.getQuestionsSet();

        // assert that retrieved parent's child's value is equal to the child in db
 //       Stream<QuestionsEntity> stream = questionsSet.stream();
 //       stream.forEach(elem -> System.out.println(elem.getQuestion()));
        //Optional<QuestionsEntity> foundQuestion = stream.findFirst();
        //assertEquals(question, foundQuestion.get().getQuestion() );

    }


}
