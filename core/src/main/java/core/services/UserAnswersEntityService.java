package core.services;

import core.transformers.UserAnswersEntityDtoTransformer;
import db.entity.*;
import db.repository.*;
import model.UserAnswersEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserAnswersEntityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserAnswersRepositoryDAO userAnswersEntityRepository;
    private final QuestionsRepositoryDAO questionsRepositoryDAO;
    private final UserAnswersEntityDtoTransformer userAnswersEntityDtoTransformer;
    private final UserRepositoryDAO userRepositoryDAO;
    private final FriendshipsRepositoryDAO friendshipsRepositoryDAO;

    public UserAnswersEntityService(final UserAnswersRepositoryDAO userAnswersEntityRepository,
                                    final UserAnswersEntityDtoTransformer userAnswersEntityDtoTransformer,
                                    final QuestionsRepositoryDAO questionsRepositoryDAO,
                                    final UserRepositoryDAO userRepositoryDAO,
                                    FriendshipsRepositoryDAO friendshipsRepositoryDAO) {
        this.userAnswersEntityRepository = userAnswersEntityRepository;
        this.userAnswersEntityDtoTransformer = userAnswersEntityDtoTransformer;
        this.questionsRepositoryDAO = questionsRepositoryDAO;
        this.userRepositoryDAO = userRepositoryDAO;
        this.friendshipsRepositoryDAO = friendshipsRepositoryDAO;
    }

    // GET an answer
    public UserAnswersEntityDto getUserAnswersEntity(final String userName, final String auditee, final Long questionsEntityId) {
        return userAnswersEntityDtoTransformer.generate(userAnswersEntityRepository.findOneByUserNameAndAuditeeAndQuestionsEntityId(userName, auditee, questionsEntityId));
    }

    // GET an answer. sequence = 1. For use in NetworkContactAudit.
    public UserAnswersEntityDto getUserAnswersEntity(final String userName, final String auditee) {
        return userAnswersEntityDtoTransformer.generateEAGER(userAnswersEntityRepository.findOneByUserNameAndAuditee(userName, auditee));
    }

    // POST/PATCH a user's answer. If not found, POST, else if found, PATCH.
    public UserAnswersEntityDto createUserAnswersEntity(final UserAnswersEntityDto userAnswersEntityDto, final Long questionsEntityId) {

        // get userAnswersEntityDto from db, if it exists.
        UserAnswersEntity userAnswersEntity = userAnswersEntityRepository.findOneByUserNameAndAuditeeAndQuestionsEntityId(userAnswersEntityDto.getUserName(), userAnswersEntityDto.getAuditee(), questionsEntityId);

        if (userAnswersEntity == null) {

            // create a new 'raw' UserAnswersEntity based on the incoming UserAnswersDto (before adding two parents)
            UserAnswersEntity newUserAnswersEntity = userAnswersEntityDtoTransformer.generate(userAnswersEntityDto);

            // find and add the parent questionsEntity (ManyToOne only. no OneToMany, so no need to add child to a parent Set/List)
            QuestionsEntity foundQuestionsEntity = questionsRepositoryDAO.findOneById(questionsEntityId);
            newUserAnswersEntity.setQuestionsEntity(foundQuestionsEntity);

            // find and add the parent questionSetVersionEntity (ManyToOne only. no OneToMany, so no need to add child to a parent Set/List)
            QuestionSetVersionEntity foundQuestionSetVersionEntity = foundQuestionsEntity.getQuestionSetVersionEntity();
            newUserAnswersEntity.setQuestionSetVersionEntity(foundQuestionSetVersionEntity);

            // save the completed userAnswersEntity
            userAnswersEntityRepository.saveAndFlush(newUserAnswersEntity);

            return userAnswersEntityDtoTransformer.generate(newUserAnswersEntity);

        }
        else {
            // this else statement is an update/patch. no need to update parents since client already 'knows' who they are. and they are already set.
            userAnswersEntity.setUserName(userAnswersEntityDto.getUserName());
            userAnswersEntity.setAnswer(userAnswersEntityDto.getAnswer());
            userAnswersEntity.setAnswerPoints(userAnswersEntityDto.getAnswerPoints());
            userAnswersEntity.setAuditee(userAnswersEntityDto.getAuditee());
            userAnswersEntity.setComments(userAnswersEntityDto.getComments());
            userAnswersEntityRepository.save(userAnswersEntity);
            return userAnswersEntityDtoTransformer.generate(userAnswersEntity);
        }
    }

    // POST audit permissions. Group: Friend, Colleague, Other. (replicating a user's set of answers but making userName = to the auditor).
    public String createUserAnswersEntitiesForAudits(final String user, final Long questionSetVersionEntityId, final String group) {

        Set<FriendshipsEntity> foundFriendshipsEntities = userRepositoryDAO.findOneByUserName(user).getFriendsSet();
        foundFriendshipsEntities.removeIf(i -> !i.getConnectionType().equals(group)); // reduce list to group (if friend not in 'group'. Java8 'removeIf')
        Set<UserAnswersEntity> foundUserAnswersEntities = userAnswersEntityRepository.findAllByUserNameAndAuditeeAndQuestionSetVersionEntityId(user, user, questionSetVersionEntityId);

        // for each friendship add the list of auditee's answers to the db with friend's name on them. (if connection status is not 'pending')
        for (FriendshipsEntity x : foundFriendshipsEntities) {
               if (!x.getConnectionStatus().equals("pending") && userAnswersEntityRepository.findAllByUserNameAndAuditeeAndQuestionSetVersionEntityId(x.getFriend(), user, questionSetVersionEntityId).equals(Collections.emptySet())) {
                 for (UserAnswersEntity y : foundUserAnswersEntities) {
                            userAnswersEntityRepository.save(
                            new UserAnswersEntity( x.getFriend(), y.getAnswer(), y.getAnswerPoints(), y.getAuditee(),
                                    y.getComments(), y.getQuestionsEntity(), y.getQuestionSetVersionEntity() ));
                      } // end for
            } // end if
        }

        String auditorsSet = " {auditors added} ";
        return auditorsSet;
    }

    // POST audit permissions. Everyone in friendships set. (replicating a user's set of answers but making userName = to the auditor).
    public String createUserAnswersEntitiesForAuditsEveryone(final String user, final Long questionSetVersionEntityId) {

        Set<FriendshipsEntity> foundFriendshipsEntities = userRepositoryDAO.findOneByUserName(user).getFriendsSet();
        Set<UserAnswersEntity> foundUserAnswersEntities = userAnswersEntityRepository.findAllByUserNameAndAuditeeAndQuestionSetVersionEntityId(user, user, questionSetVersionEntityId);

        // for each friendship add the list of auditee's answers to the db with friend's name on them. (if connection status is not 'pending')
        for (FriendshipsEntity x : foundFriendshipsEntities) {
            if (!x.getConnectionStatus().equals("pending") && userAnswersEntityRepository.findAllByUserNameAndAuditeeAndQuestionSetVersionEntityId(x.getFriend(), user, questionSetVersionEntityId).equals(Collections.emptySet())) {
                for (UserAnswersEntity y : foundUserAnswersEntities) {
                    userAnswersEntityRepository.save(
                            new UserAnswersEntity( x.getFriend(), y.getAnswer(), y.getAnswerPoints(), y.getAuditee(),
                                    y.getComments(), y.getQuestionsEntity(), y.getQuestionSetVersionEntity() ));
                } // end for
            } // end if
        }

        String auditorsSet = " {auditors added} ";
        return auditorsSet;
    }

    // POST audit permissions. Individual friend. (replicating a user's set of answers but making userName = to the auditor).
    public String createUserAnswersEntitiesForAuditsIndividual(final String user, final String friend, final Long questionSetVersionEntityId) {

        UserEntity foundUserEntity = userRepositoryDAO.findOneByUserName(user);
        FriendshipsEntity foundFriendshipsEntity = friendshipsRepositoryDAO.findOneByUserEntityIdAndFriend(foundUserEntity.getId(), friend);

        if (foundFriendshipsEntity == null) {
            String auditorsSet = " {contact not found} ";
            return auditorsSet;
        }

        else {

        Set<UserAnswersEntity> foundUserAnswersEntities = userAnswersEntityRepository.findAllByUserNameAndAuditeeAndQuestionSetVersionEntityId(user, user, questionSetVersionEntityId);

        if (!foundFriendshipsEntity.getConnectionStatus().equals("pending") && userAnswersEntityRepository.findAllByUserNameAndAuditeeAndQuestionSetVersionEntityId(friend, user, questionSetVersionEntityId).equals(Collections.emptySet())) {
                for (UserAnswersEntity y : foundUserAnswersEntities) {
                    userAnswersEntityRepository.save(
                            new UserAnswersEntity( friend, y.getAnswer(), y.getAnswerPoints(), y.getAuditee(),
                                    y.getComments(), y.getQuestionsEntity(), y.getQuestionSetVersionEntity() ));
                } // end for
            } // end if


        } // end else

        // TODO; return message of status is pending and post on front-end to let user know not added.

        String auditorsSet = " {auditors added} ";
        return auditorsSet;
    }

    public String deleteAllAnswersForUserNameAndAuditeeAndQuestionSetVersionEntityId(String user, String auditee, Long questionSetVersionEntityId) {
        userAnswersEntityRepository.deleteAllByUserNameAndAuditeeAndQuestionSetVersionEntityId(user, auditee, questionSetVersionEntityId);
        String allDeleted = "{ all answers deleted for this set }";
        return allDeleted;
    }

}
