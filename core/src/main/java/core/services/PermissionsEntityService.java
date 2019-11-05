package core.services;

import core.transformers.PermissionsEntityDtoTransformer;
import db.entity.PermissionsEntity;
import db.entity.QuestionSetVersionEntity;
import db.repository.PermissionsRepositoryDAO;
import db.repository.QuestionSetVersionRepositoryDAO;
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
    // used for 'mini' service to set QuestionSetVersion on incoming new POST
    QuestionSetVersionRepositoryDAO questionSetVersionRepositoryDAO;

    public PermissionsEntityService(final PermissionsRepositoryDAO permissionsRepositoryDAO, final PermissionsEntityDtoTransformer permissionsEntityDtoTransformer, QuestionSetVersionRepositoryDAO questionSetVersionRepositoryDAO) {
        this.permissionsRepositoryDAO = permissionsRepositoryDAO;
        this.permissionsEntityDtoTransformer = permissionsEntityDtoTransformer;
        this.questionSetVersionRepositoryDAO = questionSetVersionRepositoryDAO; // used in new POST
    }

    // GET
    public PermissionsEntityDto getPermissionsEntity(final String userName, final String auditee, final String profilePageGroup,final Long questionSetVersion) {
        return permissionsEntityDtoTransformer.generate(permissionsRepositoryDAO.findOneByUserNameAndAuditeeAndProfilePageGroupAndQuestionSetVersion(userName, auditee, profilePageGroup, questionSetVersion));
    }

    // GET  (not currently used since network level profile permission currently sits in FriendshipsEntity
    //public PermissionsEntityDto getPermissionsEntity(final String userName, String auditee) {
    //    return permissionsEntityDtoTransformer.generate(permissionsRepositoryDAO.findOneByUserNameAndAuditee(userName, auditee));
    //}

    // POST/PATCH  post if not found, otherwise patch.
    public PermissionsEntityDto createPermissionsEntity(final PermissionsEntityDto permissionsEntityDto) {
        PermissionsEntity permissionsEntity = permissionsRepositoryDAO.findOneByUserNameAndAuditeeAndProfilePageGroupAndQuestionSetVersion(permissionsEntityDto.getUserName(), permissionsEntityDto.getAuditee(), permissionsEntityDto.getProfilePageGroup(),permissionsEntityDto.getQuestionSetVersion());

        if (permissionsEntity == null) {

            // Add the Qset to the new permission.
            QuestionSetVersionEntity questionSetVersionEntity = questionSetVersionRepositoryDAO.findOneByQuestionSetVersion(permissionsEntityDto.getQuestionSetVersion());
            Set<QuestionSetVersionEntity> questionSetVersionEntities = new HashSet<>();
            questionSetVersionEntities.add(questionSetVersionEntity);

            // then save the overall new permission
            permissionsEntityDto.setQuestionSetVersionEntitySet(questionSetVersionEntities); //
            PermissionsEntity newPermissionsEntity = permissionsRepositoryDAO.saveAndFlush(permissionsEntityDtoTransformer.generate(permissionsEntityDto));
            return permissionsEntityDtoTransformer.generate(newPermissionsEntity);
        }
        else {
            // no need to update the Qset Set since only a unique combination of all the below (except for tbd) is required. So, this else statement almost useless.
            permissionsEntity.setUserName(permissionsEntityDto.getUserName());
            permissionsEntity.setAuditee(permissionsEntityDto.getAuditee());
            permissionsEntity.setProfilePageGroup(permissionsEntityDto.getProfilePageGroup());
            permissionsEntity.setQuestionSetVersion(permissionsEntityDto.getQuestionSetVersion());
            permissionsEntity.setTbd(permissionsEntityDto.getTbd());
            permissionsRepositoryDAO.save(permissionsEntity);
            return permissionsEntityDtoTransformer.generate(permissionsEntity);
        }
    }

    // PATCH (not currently used, updates InNetwork Permission)
    //public PermissionsEntityDto patchPermissionsEntity(final PermissionsEntityDto permissionsEntityDto) {
    //    PermissionsEntity permissionsEntity = permissionsRepositoryDAO.findOneByUserNameAndAuditeeAndQuestionSetVersion(permissionsEntityDto.getUserName(),
    //            permissionsEntityDto.getAuditee(), permissionsEntityDto.getQuestionSetVersion());
    //    permissionsEntity.setProfilePageGroup(permissionsEntityDto.getProfilePageGroup());
    //    permissionsRepositoryDAO.save(permissionsEntity);
    //    return permissionsEntityDtoTransformer.generate(permissionsEntity);
    //}
}
