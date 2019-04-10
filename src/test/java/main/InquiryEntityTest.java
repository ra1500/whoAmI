package main;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:TestConfiguration.properties")
@SpringBootTest
@ContextConfiguration(classes = {DBconfiguration.class, Environment.class, InquiryEntity.class, InquiryEntityDto.class, InquiryEntityService.class, InquiryEntityController.class, InquiryEntityDtoTransformer.class, Application.class,
                        InquiryEntityTest.class, AbstractRestController.class, SwaggerConfig.class})
@ComponentScan({"main"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class InquiryEntityTest {

    @Autowired
    InquiryRepositoryDAO inquiryRepositoryDAO;

    @Test
    public void crudTest() {
        Long categoryId = 111l;
        String description = "JunitTest";
        String rawCsvData = "000,111";
        String username = "karen";
        String newDescription = "JunitTestUpdateField";

        //Create - Save to db
        InquiryEntity inquiryEntity = new InquiryEntity(categoryId,description,rawCsvData, username);
        InquiryEntity savedInquiryEntity = inquiryRepositoryDAO.saveAndFlush(inquiryEntity);
        System.out.println(savedInquiryEntity.getGid());
        assertEquals(new Long(1), savedInquiryEntity.getGid());
        assertEquals(description, savedInquiryEntity.getDescription());

        //Read
        InquiryEntity foundInquiryEntity = inquiryRepositoryDAO.findOneByGid(new Long(1));
        assertEquals(inquiryEntity.getCategoryId(),foundInquiryEntity.getCategoryId() );
        assertEquals(inquiryEntity.getDescription(),foundInquiryEntity.getDescription() );
        assertEquals(inquiryEntity.getRawCsvData(),foundInquiryEntity.getRawCsvData() );

        //Update - update saved entity
        savedInquiryEntity.setDescription(newDescription);
        savedInquiryEntity = inquiryRepositoryDAO.saveAndFlush(savedInquiryEntity);
        assertEquals(newDescription, savedInquiryEntity.getDescription());

        //Delete
        inquiryRepositoryDAO.delete(savedInquiryEntity);
        assertEquals(0, inquiryRepositoryDAO.count());
    }
}