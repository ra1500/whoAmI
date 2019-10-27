package core.services;

import core.transformers.FriendshipsEntityDtoTransformer;
import db.entity.FriendshipsEntity;
import db.entity.UserEntity;
import db.repository.FriendshipsRepositoryDAO;
import model.FriendshipsEntityDto;
import model.FriendshipsEntityDtoPOST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FriendshipsEntityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final FriendshipsRepositoryDAO friendshipsRepositoryDAO;
    private final FriendshipsEntityDtoTransformer friendshipsEntityDtoTransformer;

    public FriendshipsEntityService(final FriendshipsRepositoryDAO friendshipsRepositoryDAO, final FriendshipsEntityDtoTransformer friendshipsEntityDtoTransformer) {
        this.friendshipsRepositoryDAO = friendshipsRepositoryDAO;
        this.friendshipsEntityDtoTransformer = friendshipsEntityDtoTransformer;
    }

    // for GET. not used currently. can't use just a userEntity. need to also specify 'friend'.
    //public FriendshipsEntityDto getFriendshipsEntity(final UserEntity userEntity) {
    //    return friendshipsEntityDtoTransformer.generate(friendshipsRepositoryDAO.findAllByUserEntity(userEntity));
    //}

    // for POST
    public FriendshipsEntityDto createFriendshipsEntity(final FriendshipsEntityDtoPOST friendshipsEntityDtoPOST) {
        FriendshipsEntity friendshipsEntity = friendshipsRepositoryDAO.saveAndFlush(friendshipsEntityDtoTransformer.generate(friendshipsEntityDtoPOST));
        return friendshipsEntityDtoTransformer.generate(friendshipsEntity);
    }
}