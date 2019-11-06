package core.services;

import core.transformers.QuestionSetVersionEntityDtoTransformer;
import db.entity.PermissionsEntity;
import db.entity.QuestionSetVersionEntity;
import db.repository.PermissionsRepositoryDAO;
import db.repository.QuestionSetVersionRepositoryDAO;
import model.QuestionSetVersionEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class QuestionSetVersionEntityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final QuestionSetVersionRepositoryDAO questionSetVersionEntityRepository;
    private final PermissionsRepositoryDAO permissionsRepositoryDAO;
    private final QuestionSetVersionEntityDtoTransformer questionSetVersionEntityDtoTransformer;

    public QuestionSetVersionEntityService(final QuestionSetVersionRepositoryDAO questionSetVersionEntityRepository,
                                           final QuestionSetVersionEntityDtoTransformer questionSetVersionEntityDtoTransformer,
                                            final PermissionsRepositoryDAO permissionsRepositoryDAO) {
        this.questionSetVersionEntityRepository = questionSetVersionEntityRepository;
        this.questionSetVersionEntityDtoTransformer = questionSetVersionEntityDtoTransformer;
        this.permissionsRepositoryDAO = permissionsRepositoryDAO;
    }

    public QuestionSetVersionEntityDto getQuestionSetVersionEntity(final Long questionSetVersion) {
        return questionSetVersionEntityDtoTransformer.generate(questionSetVersionEntityRepository.findOneByQuestionSetVersion(questionSetVersion));
    }

    // POST/PATCH
    public QuestionSetVersionEntityDto createQuestionSetVersionEntity(final QuestionSetVersionEntityDto questionSetVersionEntityDto) {
        QuestionSetVersionEntity questionSetVersionEntity = questionSetVersionEntityRepository.findOneByQuestionSetVersion(questionSetVersionEntityDto.getQuestionSetVersion());

        if (questionSetVersionEntity == null) {
            QuestionSetVersionEntity newQuestionSetVersionEntity = questionSetVersionEntityRepository.saveAndFlush(questionSetVersionEntityDtoTransformer.generate(questionSetVersionEntityDto));

            // set questionSetVersion equal to the generated ID // TODO maybe don't need to set since would be equal to gid.... So use gid instead for joins and GETs etc.
            newQuestionSetVersionEntity.setQuestionSetVersion(newQuestionSetVersionEntity.getGid());
            questionSetVersionEntityRepository.save(newQuestionSetVersionEntity);

            // Add/create the new respective permission
            Set<QuestionSetVersionEntity> newQuestionSetVersionEntities = new HashSet<>();
            newQuestionSetVersionEntities.add(newQuestionSetVersionEntity);
            PermissionsEntity newPermissionsEntity = new PermissionsEntity(newQuestionSetVersionEntities, questionSetVersionEntityDto.getCreativeSource(),
                    questionSetVersionEntityDto.getCreativeSource(), new String("Private"), newQuestionSetVersionEntity.getGid(), new String("tbd") );
            permissionsRepositoryDAO.saveAndFlush(newPermissionsEntity);

            return questionSetVersionEntityDtoTransformer.generate(questionSetVersionEntity);
        }
        else {
            questionSetVersionEntity.setTitle(questionSetVersionEntityDto.getTitle());
            questionSetVersionEntity.setCategory(questionSetVersionEntityDto.getCategory());
            questionSetVersionEntity.setDescription(questionSetVersionEntityDto.getDescription());
            // no need to update questionSetVersion since this is an update and should be the same.
            // no need to update 'version' at this time per front-end.
            questionSetVersionEntityRepository.save(questionSetVersionEntity);
            return questionSetVersionEntityDtoTransformer.generate(questionSetVersionEntity);
        }

    }
}
