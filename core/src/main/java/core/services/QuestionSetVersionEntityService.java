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

    // GET
    public QuestionSetVersionEntityDto getQuestionSetVersionEntity(final Long questionSetVersionEntityId) {
        return questionSetVersionEntityDtoTransformer.generate(questionSetVersionEntityRepository.findOneById(questionSetVersionEntityId));
    }

    // POST/PATCH
    public QuestionSetVersionEntityDto createQuestionSetVersionEntity(final QuestionSetVersionEntityDto questionSetVersionEntityDto,
                final Long questionSetVersionEntityId, final String userName) {

        // get questionSetVersionEntity from db, if it exists.
        QuestionSetVersionEntity questionSetVersionEntity = questionSetVersionEntityRepository.findOneById(questionSetVersionEntityId);

        if (questionSetVersionEntity == null) {

            // create and save new questionSetVersionEntity
            QuestionSetVersionEntity newQuestionSetVersionEntity = questionSetVersionEntityRepository.saveAndFlush(questionSetVersionEntityDtoTransformer.generate(questionSetVersionEntityDto));

            // create and save a new initial child permission. (ManyToOne. a permission only has one qset parent. a qset can have many permissions associated with it)
            PermissionsEntity newPermissionsEntity = new PermissionsEntity(userName, userName, new String("Public"), new String("tbd"));
            newPermissionsEntity.setQuestionSetVersionEntity(newQuestionSetVersionEntity);
            permissionsRepositoryDAO.saveAndFlush(newPermissionsEntity);

            return questionSetVersionEntityDtoTransformer.generate(newQuestionSetVersionEntity);
        }
        else {
            questionSetVersionEntity.setId(questionSetVersionEntityDto.getId());
            questionSetVersionEntity.setCreated(questionSetVersionEntityDto.getCreated());
            questionSetVersionEntity.setTitle(questionSetVersionEntityDto.getTitle());
            questionSetVersionEntity.setCategory(questionSetVersionEntityDto.getCategory());
            questionSetVersionEntity.setDescription(questionSetVersionEntityDto.getDescription());
            questionSetVersionEntity.setVersion(questionSetVersionEntityDto.getVersion());
            questionSetVersionEntity.setCreativeSource(questionSetVersionEntityDto.getCreativeSource());
            questionSetVersionEntityRepository.save(questionSetVersionEntity);
            return questionSetVersionEntityDtoTransformer.generate(questionSetVersionEntity);
        }

    }
}
