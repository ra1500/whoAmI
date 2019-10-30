package core.services;

import core.transformers.QuestionSetVersionEntityDtoTransformer;
import db.entity.QuestionSetVersionEntity;
import db.repository.QuestionSetVersionRepositoryDAO;
import model.QuestionSetVersionEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class QuestionSetVersionEntityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final QuestionSetVersionRepositoryDAO questionSetVersionEntityRepository;

    private final QuestionSetVersionEntityDtoTransformer questionSetVersionEntityDtoTransformer;

    public QuestionSetVersionEntityService(final QuestionSetVersionRepositoryDAO questionSetVersionEntityRepository, final QuestionSetVersionEntityDtoTransformer questionSetVersionEntityDtoTransformer) {
        this.questionSetVersionEntityRepository = questionSetVersionEntityRepository;
        this.questionSetVersionEntityDtoTransformer = questionSetVersionEntityDtoTransformer;
    }

    public QuestionSetVersionEntityDto getQuestionSetVersionEntity(final Long gid) {
        return questionSetVersionEntityDtoTransformer.generate(questionSetVersionEntityRepository.findOneByGid(gid));
    }

    //public QuestionSetVersionEntityDto createQuestionSetVersionEntity(final QuestionSetVersionEntityDto questionSetVersionEntityDto) {
    //    QuestionSetVersionEntity questionSetVersionEntity = questionSetVersionEntityRepository.saveAndFlush(questionSetVersionEntityDtoTransformer.generate(questionSetVersionEntityDto));
    //    return questionSetVersionEntityDtoTransformer.generate(questionSetVersionEntity);
    //}
}
