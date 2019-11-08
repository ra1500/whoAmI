package db.repositorytest;

import db.config.DBconfiguration;
import db.config.HibernateProperties;
import db.entity.QuestionSetVersionEntity;
import db.entity.QuestionsEntity;
import db.entity.UserAnswersEntity;
import db.repository.QuestionSetVersionRepositoryDAO;
import db.repository.QuestionsRepositoryDAO;
import db.repository.UserAnswersRepositoryDAO;
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
@ContextConfiguration(classes = {Environment.class, QuestionsEntity.class, QuestionSetVersionEntity.class, UserAnswersTest.class, DBconfiguration.class, HibernateProperties.class})
@ComponentScan({"db"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserAnswersTest {

    @Autowired
    UserAnswersRepositoryDAO userAnswersRepositoryDAO;

    @Autowired
    QuestionsRepositoryDAO questionsRepositoryDAO;

    @Autowired
    QuestionSetVersionRepositoryDAO questionSetVersionRepositoryDAO;

    @Test
    public void crudTest() {

        // create and save a Question. find it.
        String question = "how are you";
        QuestionsEntity questionsEntity = new QuestionsEntity(question);
        QuestionsEntity savedQuestionsEntity = questionsRepositoryDAO.saveAndFlush(questionsEntity);
        QuestionsEntity foundQuestionsEntity = questionsRepositoryDAO.findOneById(savedQuestionsEntity.getId());
        assertEquals(question, foundQuestionsEntity.getQuestion());

        // create and save a QuestionSetVersion. find it.
        String title = "is this thing on?";
        QuestionSetVersionEntity questionSetVersionEntity = new QuestionSetVersionEntity(title);
        QuestionSetVersionEntity savedQuestionSetVersionEntity = questionSetVersionRepositoryDAO.saveAndFlush(questionSetVersionEntity);
        QuestionSetVersionEntity foundQuestionSetVersionEntity = questionSetVersionRepositoryDAO.findOneById(savedQuestionSetVersionEntity.getId());
        assertEquals(title, foundQuestionSetVersionEntity.getTitle());

        // create a UserAnswer. find it. (with added parents of Question & QuestonSetVersion
        String userName = "karen";
        Long answerPoints = new Long(199);
        UserAnswersEntity userAnswersEntity = new UserAnswersEntity(userName, answerPoints);
        userAnswersEntity.setQuestionsEntity(foundQuestionsEntity);
        userAnswersEntity.setQuestionSetVersionEntity(foundQuestionSetVersionEntity);
        UserAnswersEntity savedUserAnswersEntity = userAnswersRepositoryDAO.saveAndFlush(userAnswersEntity);
        UserAnswersEntity foundUserAnswersEntity = userAnswersRepositoryDAO.findOneById(savedUserAnswersEntity.getId());
        assertEquals(userName, foundUserAnswersEntity.getUserName());

        // Test for parents included/persisted in UserAnswersEntity child!!!!!
        assertEquals(question, foundUserAnswersEntity.getQuestionsEntity().getQuestion()); // question parent included? Yes/No
        assertEquals(title, foundUserAnswersEntity.getQuestionSetVersionEntity().getTitle()); // questionSetVersion included? Yes/No

    }
}
