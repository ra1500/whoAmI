package core.services;

import core.transformers.PermissionsEntityDtoTransformer;
import db.entity.PermissionsEntity;
import db.entity.QuestionSetVersionEntity;
import db.repository.PermissionsRepositoryDAO;
import db.repository.QuestionSetVersionRepositoryDAO;
import db.repository.UserAnswersRepositoryDAO;
import model.PermissionsEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PermissionsEntityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final PermissionsRepositoryDAO permissionsRepositoryDAO;
    private final PermissionsEntityDtoTransformer permissionsEntityDtoTransformer;
    private final UserAnswersRepositoryDAO userAnswersRepositoryDAO;
    QuestionSetVersionRepositoryDAO questionSetVersionRepositoryDAO;

    public PermissionsEntityService(final PermissionsRepositoryDAO permissionsRepositoryDAO, final PermissionsEntityDtoTransformer permissionsEntityDtoTransformer, QuestionSetVersionRepositoryDAO questionSetVersionRepositoryDAO, UserAnswersRepositoryDAO userAnswersRepositoryDAO) {
        this.permissionsRepositoryDAO = permissionsRepositoryDAO;
        this.permissionsEntityDtoTransformer = permissionsEntityDtoTransformer;
        this.questionSetVersionRepositoryDAO = questionSetVersionRepositoryDAO;
        this.userAnswersRepositoryDAO = userAnswersRepositoryDAO;
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
}
