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

import java.time.LocalDate;
import java.time.ZoneId;
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

    // GET
    public PermissionsEntityDto getPermissionsEntity(final Long Id, final String userName) {
        return permissionsEntityDtoTransformer.generate(permissionsRepositoryDAO.findOneByIdAndUserName(Id, userName));
    }

    // GET. Alerts. Newly posted '16' audits posted.
    public Set<PermissionsEntity> getPermissionsEntityNewAuditsPosted(final String userName) {
        LocalDate windowDate = LocalDate.now().minusDays(7);
        Set<PermissionsEntity> foundNewAuditsPosted = permissionsRepositoryDAO.getAuditsRecent(userName);
        foundNewAuditsPosted.removeIf(i -> windowDate.isAfter(i.getCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));

        for (PermissionsEntity y : foundNewAuditsPosted) {
            y.setCreated(null);
            y.setId(null);
            y.getQuestionSetVersionEntity().setCreated(null);
            y.getQuestionSetVersionEntity().setResult1(null); y.getQuestionSetVersionEntity().setResult2(null);
            y.getQuestionSetVersionEntity().setResult3(null); y.getQuestionSetVersionEntity().setResult4(null);
            y.getQuestionSetVersionEntity().setResult1start(null); y.getQuestionSetVersionEntity().setResult2start(null);
            y.getQuestionSetVersionEntity().setResult3start(null);
        }

        return foundNewAuditsPosted;
    }

    // POST/PATCH  SCORE. userAnswers Score permission. post if not found, otherwise patch. (not used for '16' to post audit answers, that method is below).
    public PermissionsEntityDto createPermissionsEntity(final PermissionsEntityDto permissionsEntityDto, final Long questionSetVersionEntityId, final String userName) {

        // find in db. if exists.
        PermissionsEntity permissionsEntity = permissionsRepositoryDAO.findOneByUserNameAndQuestionSetVersionEntityId(permissionsEntityDto.getUserName(), questionSetVersionEntityId);

        if (permissionsEntity == null) {

            // create a new 'raw' permissionsEntity based on incoming Dto. Add parent Qset after.
            PermissionsEntity newPermissionsEntity = permissionsEntityDtoTransformer.generate(permissionsEntityDto);
            newPermissionsEntity.setType("Score"); //null from post DTO.
            newPermissionsEntity.setAuditee(userName); //null from post DTO.
            newPermissionsEntity.setScore(userAnswersRepositoryDAO.findUserScoresTotal(userName, userName, questionSetVersionEntityId));

            // find and add QuestionSetVersionEntity parent. (ManyToOne). (only adding parent to child. not adding child to a parent set/list).
            QuestionSetVersionEntity foundQuestionSetVersionEntity = questionSetVersionRepositoryDAO.findOneById(questionSetVersionEntityId);
            newPermissionsEntity.setQuestionSetVersionEntity(foundQuestionSetVersionEntity);

            // set the initial permission 'typeNumber' to either 9 or 15 (public or 'privateprofile & qset creator')
            if (foundQuestionSetVersionEntity.getCreativeSource().equals("company")) {
                newPermissionsEntity.setTypeNumber(new Long(9));
                newPermissionsEntity.setViewGroup("Public"); //null from post DTO.
            }
            else {
                newPermissionsEntity.setTypeNumber(new Long(15));
                newPermissionsEntity.setViewGroup("QsetCreator"); //null from post DTO.
            }

            // then save the overall new permission
            permissionsRepositoryDAO.saveAndFlush(newPermissionsEntity);

            return permissionsEntityDtoTransformer.generate(newPermissionsEntity);
        }

        // Note: when adding feature to allow updating typeNumber to NetworkFriends or NetworkColleagues etc. make sure typeNumber doesnt come from DTO. Internal/Back-end change only.
        else {
            if (permissionsEntityDto.getTypeNumber() != null) {
                if (!permissionsEntity.getTypeNumber().equals(new Long(15))) {  // '15's not allowed to update. can only be viewed by creator and individual. not network or public.
                permissionsEntity.setTypeNumber(permissionsEntityDto.getTypeNumber()); }
            }
            if (permissionsEntityDto.getType() != null) {
                permissionsEntity.setType(permissionsEntityDto.getType());
            }
            if (permissionsEntityDto.getViewGroup() != null) {
                permissionsEntity.setViewGroup(permissionsEntityDto.getViewGroup());
            }
            if (permissionsEntityDto.getAuditee() != null) {
                permissionsEntity.setAuditee(permissionsEntityDto.getAuditee());
            }
            //permissionsEntity.setId(permissionsEntityDto.getId()); // cannot change primary key Id
            //permissionsEntity.setCreated(permissionsEntityDto.getCreated()); // no need to update the original created date
            permissionsEntity.setUserName(permissionsEntityDto.getUserName()); // coming from token (set in controller)
            permissionsEntity.setScore(userAnswersRepositoryDAO.findUserScoresTotal(userName, userName, questionSetVersionEntityId)); // Yes, update it of course.
            permissionsEntity.setResult(permissionsEntityDto.getResult()); // not updated by client. back-end calculated.
            permissionsEntity.setBadge(permissionsEntityDto.getBadge()); // TODO: for now comes from client, but later should be if/then on back-end to avoid manipulation.
            //permissionsEntity.setQuestionSetVersionEntity(permissionsEntityDto.getQuestionSetVersionEntity()); // should already by set!
            permissionsRepositoryDAO.save(permissionsEntity);

            return permissionsEntityDtoTransformer.generate(permissionsEntity);
        }
    }

    // POST/PATCH  Audit Score '16'
    public PermissionsEntityDto createPermissionsEntityAuditScore(final PermissionsEntityDto permissionsEntityDto, final Long questionSetVersionEntityId, final String userName) {

        // find in db. if exists.
        PermissionsEntity permissionsEntity = permissionsRepositoryDAO.findOneByUserNameAndTypeNumberAndQuestionSetVersionEntityId(permissionsEntityDto.getUserName(), new Long(16),questionSetVersionEntityId);

        if (permissionsEntity == null) {

            // create a new 'raw' permissionsEntity based on incoming Dto. Add parent Qset after.
            PermissionsEntity newPermissionsEntity = permissionsEntityDtoTransformer.generate(permissionsEntityDto);
            newPermissionsEntity.setType("Audit Score"); //null from post DTO.
            newPermissionsEntity.setViewGroup("Auditee Individual"); //null from post DTO.
            newPermissionsEntity.setTypeNumber(new Long(16));

            // find and add QuestionSetVersionEntity parent. (ManyToOne). (only adding parent to child. not adding child to a parent set/list).
            QuestionSetVersionEntity foundQuestionSetVersionEntity = questionSetVersionRepositoryDAO.findOneById(questionSetVersionEntityId);
            newPermissionsEntity.setQuestionSetVersionEntity(foundQuestionSetVersionEntity);

            // then save the overall new permission
            permissionsRepositoryDAO.saveAndFlush(newPermissionsEntity);

            return permissionsEntityDtoTransformer.generate(newPermissionsEntity);
        }

        // Note: when adding feature to allow updating typeNumber to NetworkFriends or NetworkColleagues etc. make sure typeNumber doesnt come from DTO. Internal/Back-end change only.
        else {
            // permissionsEntity.setId(permissionsEntityDto.getId()); // cannot change primary key Id
            // permissionsEntity.setCreated(permissionsEntityDto.getCreated()); // no need to update the original created date
            // permissionsEntity.setTypeNumber(permissionsEntityDto.getTypeNumber()); // should already by set
            // permissionsEntity.setAuditee(permissionsEntityDto.getAuditee()); // should already by set
            // permissionsEntity.setViewGroup(permissionsEntityDto.getViewGroup()); // should already by set
            // permissionsEntity.setType(permissionsEntityDto.getType()); // should already by set
            permissionsEntity.setUserName(permissionsEntityDto.getUserName()); // coming from token (set in controller)
            permissionsEntity.setScore(permissionsEntityDto.getScore()); // update
            permissionsEntity.setResult(permissionsEntityDto.getResult()); // update
            permissionsEntity.setBadge(permissionsEntityDto.getBadge()); // update TODO: update so back-end determined not front-end determined
            //permissionsEntity.setQuestionSetVersionEntity(permissionsEntityDto.getQuestionSetVersionEntity()); // should already by set
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
                userName, "Network", "viewQuestionSet", typeNumber, new Long(0), null, null, foundQuestionSetVersionEntity))); }

        if (typeNumber == 6) {
            Stream<FriendshipsEntity> stream = foundFriendshipsEntities.stream().filter(element -> element.getConnectionType().equals("Colleague"));
            stream.forEach(element -> permissionsRepositoryDAO.saveAndFlush(new PermissionsEntity(element.getFriend(),
                    userName, "Network", "viewQuestionSet", typeNumber, new Long(0), null, null,  foundQuestionSetVersionEntity))); }

        if (typeNumber == 7) {
            Stream<FriendshipsEntity> stream = foundFriendshipsEntities.stream().filter(element -> element.getConnectionType().equals("Other"));
            stream.forEach(element -> permissionsRepositoryDAO.saveAndFlush(new PermissionsEntity(element.getFriend(),
                    userName, "Network", "viewQuestionSet", typeNumber, new Long(0), null, null,  foundQuestionSetVersionEntity))); }

        // all connections.
        if (typeNumber == 4) {
            foundFriendshipsEntities.stream().forEach(element -> permissionsRepositoryDAO.saveAndFlush(new PermissionsEntity(element.getFriend(),
                    userName, "Network", "viewQuestionSet", typeNumber, new Long(0), null, null,  foundQuestionSetVersionEntity))); }

        return new String("can now answer your set");
    }

    // POST generate a QsetView permissions (for an individual invitee)
    public String createIndividualQsetViewPermissionEntity (Long questionSetVersionEntityId, String userName, String invitee) {

        // get the QsetEntity to add as parent to the permissionEntity
        QuestionSetVersionEntity foundQuestionSetVersionEntity = questionSetVersionRepositoryDAO.findOneById(questionSetVersionEntityId);

        // validate that token user is same as Qset creator
        if (!foundQuestionSetVersionEntity.getCreativeSource().equals(userName)) {
            return new String("invalid operation");
        }

        // get list of friends
        Set<FriendshipsEntity> foundFriendshipsEntities = userRepositoryDAO.findOneByUserName(userName).getFriendsSet();

        // reduce to just friend/invitee
        foundFriendshipsEntities.removeIf(i -> !i.getFriend().equals(invitee));

        // check if empty (meaning friend is not in contacts list)
        if (foundFriendshipsEntities.isEmpty()) { return new String(" not found in your contacts list."); };

        // Stream through list of contacts and then create the respective permission
        Stream<FriendshipsEntity> stream = foundFriendshipsEntities.stream().filter(element -> element.getFriend().equals(invitee));

        stream.forEach(element -> permissionsRepositoryDAO.saveAndFlush(new PermissionsEntity(element.getFriend(),
                    userName, "Network", "viewQuestionSet",  new Long(8), new Long(0), null, null,  foundQuestionSetVersionEntity)));

        return new String("can now answer your set");
    }

}
