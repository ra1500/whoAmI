package core.services;

import core.transformers.PermissionsEntityDtoTransformer;
import db.entity.FriendshipsEntity;
import db.entity.PermissionsEntity;
import db.entity.QuestionSetVersionEntity;
import db.repository.PermissionsRepositoryDAO;
import db.repository.QuestionSetVersionRepositoryDAO;
import db.repository.UserAnswersRepositoryDAO;
import db.repository.UserRepositoryDAO;
import model.PermissionsEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class PermissionsEntityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final PermissionsRepositoryDAO permissionsRepositoryDAO;
    private final PermissionsEntityDtoTransformer permissionsEntityDtoTransformer;
    private final UserAnswersRepositoryDAO userAnswersRepositoryDAO;
    private final QuestionSetVersionRepositoryDAO questionSetVersionRepositoryDAO;
    private final UserRepositoryDAO userRepositoryDAO;

    public PermissionsEntityService(final PermissionsRepositoryDAO permissionsRepositoryDAO, final PermissionsEntityDtoTransformer permissionsEntityDtoTransformer, QuestionSetVersionRepositoryDAO questionSetVersionRepositoryDAO, UserAnswersRepositoryDAO userAnswersRepositoryDAO, UserRepositoryDAO userRepositoryDAO) {
        this.permissionsRepositoryDAO = permissionsRepositoryDAO;
        this.permissionsEntityDtoTransformer = permissionsEntityDtoTransformer;
        this.questionSetVersionRepositoryDAO = questionSetVersionRepositoryDAO;
        this.userAnswersRepositoryDAO = userAnswersRepositoryDAO;
        this.userRepositoryDAO = userRepositoryDAO;
    }

    // GET
    public PermissionsEntityDto getPermissionsEntity(final String userName, final String auditee, final Long questionSetVersionEntityId) {
        return permissionsEntityDtoTransformer.generate(permissionsRepositoryDAO.findOneByUserNameAndAuditeeAndQuestionSetVersionEntityId(userName, auditee, questionSetVersionEntityId));
    }

    // POST/PATCH  post if not found, otherwise patch.
    public PermissionsEntityDto createPermissionsEntity(final PermissionsEntityDto permissionsEntityDto, final Long questionSetVersionEntityId, final String userName) {

        // find in db. if exists.
        PermissionsEntity permissionsEntity = permissionsRepositoryDAO.findOneByUserNameAndTypeNumberAndQuestionSetVersionEntityId(permissionsEntityDto.getUserName(), permissionsEntityDto.getTypeNumber(),questionSetVersionEntityId);

        if (permissionsEntity == null) {

            // create a new 'raw' permissionsEntity based on incoming Dto. Add parent Qset after.
            PermissionsEntity newPermissionsEntity = permissionsEntityDtoTransformer.generate(permissionsEntityDto);
            newPermissionsEntity.setScore(userAnswersRepositoryDAO.findUserScoresTotal(userName, userName, questionSetVersionEntityId));

            // find and add QuestionSetVersionEntity parent. (ManyToOne). (only adding parent to child. not adding child to a parent set/list).
            QuestionSetVersionEntity foundQuestionSetVersionEntity = questionSetVersionRepositoryDAO.findOneById(questionSetVersionEntityId);
            newPermissionsEntity.setQuestionSetVersionEntity(foundQuestionSetVersionEntity);

            // then save the overall new permission
            permissionsRepositoryDAO.saveAndFlush(newPermissionsEntity);

            return permissionsEntityDtoTransformer.generate(newPermissionsEntity);
        }
        else {
            //permissionsEntity.setId(permissionsEntityDto.getId()); // cannot change primary key Id!
            //permissionsEntity.setCreated(permissionsEntityDto.getCreated());
            permissionsEntity.setUserName(permissionsEntityDto.getUserName());
            permissionsEntity.setAuditee(permissionsEntityDto.getAuditee());
            permissionsEntity.setViewGroup(permissionsEntityDto.getViewGroup());
            permissionsEntity.setType(permissionsEntityDto.getType());
            permissionsEntity.setTypeNumber(permissionsEntityDto.getTypeNumber());
            permissionsEntity.setScore(userAnswersRepositoryDAO.findUserScoresTotal(userName, userName, questionSetVersionEntityId));
            //permissionsEntity.setQuestionSetVersionEntity(permissionsEntityDto.getQuestionSetVersionEntity()); // should already by set!
            permissionsRepositoryDAO.save(permissionsEntity);

            return permissionsEntityDtoTransformer.generate(permissionsEntity);
        }
    }

    // POST generate a Set of QsetView permissions (for groups)
    public String createQsetViewPermissionEntities (Long typeNumber, Long questionSetVersionEntityId ,String userName) {

        // get the QsetEntity to add as parent to each permissionEntity
        QuestionSetVersionEntity foundQuestionSetVersionEntity = questionSetVersionRepositoryDAO.findOneById(questionSetVersionEntityId);

        // validate that token user is same as Qset creator
        if (!foundQuestionSetVersionEntity.getCreativeSource().equals(userName)) {
            return new String("invalid operation");
        }

        // Stream through list of connections and then create all the related permissions
        Set<FriendshipsEntity> foundFriendshipsEntities = userRepositoryDAO.findOneByUserName(userName).getFriendsSet();

        if (typeNumber == 5) {
        Stream<FriendshipsEntity> stream = foundFriendshipsEntities.stream().filter(element -> element.getConnectionType().equals("Friend"));
        stream.forEach(element -> permissionsRepositoryDAO.saveAndFlush(new PermissionsEntity(element.getFriend(),
                userName, "Network", "viewQuestionSet", typeNumber, new Long(0), foundQuestionSetVersionEntity))); }

        if (typeNumber == 6) {
            Stream<FriendshipsEntity> stream = foundFriendshipsEntities.stream().filter(element -> element.getConnectionType().equals("Colleague"));
            stream.forEach(element -> permissionsRepositoryDAO.saveAndFlush(new PermissionsEntity(element.getFriend(),
                    userName, "Network", "viewQuestionSet", typeNumber, new Long(0), foundQuestionSetVersionEntity))); }

        if (typeNumber == 7) {
            Stream<FriendshipsEntity> stream = foundFriendshipsEntities.stream().filter(element -> element.getConnectionType().equals("Other"));
            stream.forEach(element -> permissionsRepositoryDAO.saveAndFlush(new PermissionsEntity(element.getFriend(),
                    userName, "Network", "viewQuestionSet", typeNumber, new Long(0), foundQuestionSetVersionEntity))); }

        // all connections.
        if (typeNumber == 4) {
            foundFriendshipsEntities.stream().forEach(element -> permissionsRepositoryDAO.saveAndFlush(new PermissionsEntity(element.getFriend(),
                    userName, "Network", "viewQuestionSet", typeNumber, new Long(0), foundQuestionSetVersionEntity))); }

        return new String("Qset Permissions set");
    }

    // POST generate a QsetView permissions (for an individual invitee)
    public String createIndividualQsetViewPermissionEntity (Long questionSetVersionEntityId, String userName, String invitee) {

        // get the QsetEntity to add as parent to the permissionEntity
        QuestionSetVersionEntity foundQuestionSetVersionEntity = questionSetVersionRepositoryDAO.findOneById(questionSetVersionEntityId);

        // validate that token user is same as Qset creator
        if (!foundQuestionSetVersionEntity.getCreativeSource().equals(userName)) {
            return new String("invalid operation");
        }

        // Stream through list of connections and then create the respective permission
        Set<FriendshipsEntity> foundFriendshipsEntities = userRepositoryDAO.findOneByUserName(userName).getFriendsSet();

        Stream<FriendshipsEntity> stream = foundFriendshipsEntities.stream().filter(element -> element.getFriend().equals(invitee));
        stream.forEach(element -> permissionsRepositoryDAO.saveAndFlush(new PermissionsEntity(element.getFriend(),
                    userName, "Network", "viewQuestionSet",  new Long(8), new Long(0), foundQuestionSetVersionEntity)));

        return new String("Qset Permission set");
    }

}
