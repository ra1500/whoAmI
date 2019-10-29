/**

import core.services.FriendshipsEntityService;
import core.transformers.FriendshipsEntityDtoTransformer;
import db.entity.FriendshipsEntity;
import db.entity.UserEntity;
import db.repository.FriendshipsRepositoryDAO;
import model.FriendshipsEntityDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class FriendshipsEntityServiceTest {

    static String userName = "karen";
    static String password = "pswrd";
    static UserEntity userEntity = new UserEntity(userName,password);

    private static final FriendshipsEntity friendshipsEntity =
            new FriendshipsEntity(userEntity, "mockInviter", "mockFriend", "mockConnectionStatus", "mockConnectionType", "mockVisibilityPermission");

    private static final FriendshipsEntityDto FRIENDSHIPS_ENTITY_DTO =
            new FriendshipsEntityDto();

    private FriendshipsEntityService friendshipsEntityService;

    @Mock
    FriendshipsRepositoryDAO mockedFriendshipsRepositoryDAO;

    @Mock
    FriendshipsEntityDtoTransformer mockedInquiryEntityDtoTransformer;

    @Test
    public void shouldProvideClientDtoWithValidDbInquiryEntity(){
        friendshipsEntityService = new FriendshipsEntityService(mockedFriendshipsRepositoryDAO, mockedInquiryEntityDtoTransformer);

        when(mockedFriendshipsRepositoryDAO.findOneByGid(anyLong())).thenReturn(friendshipsEntity);
        when(mockedInquiryEntityDtoTransformer.generate(friendshipsEntity)).thenReturn(FRIENDSHIPS_ENTITY_DTO);
        when(mockedInquiryEntityDtoTransformer.generate(FRIENDSHIPS_ENTITY_DTO)).thenReturn(friendshipsEntity);

        FRIENDSHIPS_ENTITY_DTO.setFriend("mockFriend");

        FriendshipsEntityDto validClientDto = friendshipsEntityService.getFriendshipsEntity(userEntity);
        assertEquals (FRIENDSHIPS_ENTITY_DTO.getFriend(), validClientDto.getFriend());
        verify(mockedFriendshipsRepositoryDAO).findOneByGid(1L);
        verify(mockedInquiryEntityDtoTransformer).generate(friendshipsEntity);
    }

    @Test
    public void shouldProvideDbInquiryEntityWithValidClientDto(){
        //TBD
    }

}
**/