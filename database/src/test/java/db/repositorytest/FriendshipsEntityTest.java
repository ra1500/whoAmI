package db.repositorytest;

import db.config.DBconfiguration;
import db.config.HibernateProperties;
import db.entity.FriendshipsEntity;
import db.entity.UserEntity;
import db.repository.FriendshipsRepositoryDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:TestConfiguration.properties")
@DataJpaTest
@ContextConfiguration(classes = {Environment.class, FriendshipsEntity.class, FriendshipsEntityTest.class, DBconfiguration.class, HibernateProperties.class})
@ComponentScan({"db"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FriendshipsEntityTest {

    @Autowired
    FriendshipsRepositoryDAO friendshipsRepositoryDAO;

    @Test
    public void crudTest() {
        String userName = "karen";
        String password = "pswrd";
        UserEntity userEntity = new UserEntity(userName,password);
        String friend = "GeorgeTheFriend";
        String connectionStatus = "connected";
        String inviter = "GeorgeInvitedKaren";
        String connectionType = "colleague";
        String visibilityPermission = "private";
        String newFriend = "JunitTestUpdateField";

        //Create - Save to db
        FriendshipsEntity friendshipsEntity = new FriendshipsEntity(userEntity,inviter,friend,connectionStatus,connectionType,visibilityPermission );
        FriendshipsEntity savedFriendshipsEntity = friendshipsRepositoryDAO.saveAndFlush(friendshipsEntity);
        System.out.println(savedFriendshipsEntity.getGid());
        assertEquals(new Long(1), savedFriendshipsEntity.getGid());
        assertEquals(inviter, savedFriendshipsEntity.getInviter());

        //Read
        FriendshipsEntity foundFriendshipsEntity = friendshipsRepositoryDAO.findOneByGid(new Long(1));
        assertEquals(friendshipsEntity.getFriend(),foundFriendshipsEntity.getFriend() );
        assertEquals(friendshipsEntity.getConnectionStatus(),foundFriendshipsEntity.getConnectionStatus() );
        assertEquals(friendshipsEntity.getConnectionType(),foundFriendshipsEntity.getConnectionType() );

        //Update - update saved entity
        savedFriendshipsEntity.setFriend(newFriend);
        savedFriendshipsEntity = friendshipsRepositoryDAO.saveAndFlush(savedFriendshipsEntity);
        assertEquals(newFriend, savedFriendshipsEntity.getFriend());

        //Delete
        friendshipsRepositoryDAO.delete(savedFriendshipsEntity);
        assertEquals(0, friendshipsRepositoryDAO.count());
    }
}