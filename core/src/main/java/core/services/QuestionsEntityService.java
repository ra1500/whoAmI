package core.services;

import core.transformers.QuestionsEntityDtoTransformer;
import db.entity.QuestionsEntity;
import db.repository.QuestionsRepositoryDAO;
import model.QuestionsEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class QuestionsEntityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final QuestionsRepositoryDAO questionsEntityRepository;

    private final QuestionsEntityDtoTransformer questionsEntityDtoTransformer;

    public QuestionsEntityService(final QuestionsRepositoryDAO questionsEntityRepository, final QuestionsEntityDtoTransformer questionsEntityDtoTransformer) {
        this.questionsEntityRepository = questionsEntityRepository;
        this.questionsEntityDtoTransformer = questionsEntityDtoTransformer;
    }

    public QuestionsEntityDto getQuestionsEntity(final Long gid) {
        return questionsEntityDtoTransformer.generate(questionsEntityRepository.findOneByGid(gid));
    }

    // POST (not used)
    //public QuestionsEntityDto createQuestionsEntity(final QuestionsEntityDto questionsEntityDto) {
    //    QuestionsEntity questionsEntity = questionsEntityRepository.saveAndFlush(questionsEntityDtoTransformer.generate(questionsEntityDto));
    //    return questionsEntityDtoTransformer.generate(questionsEntity);
    //}
}
