import core.services.InquiryEntityService;
import core.transformers.InquiryEntityDtoTransformer;
import db.entity.InquiryEntity;
import db.repository.InquiryRepositoryDAO;
import model.InquiryEntityDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CoreTest {

    private static final InquiryEntity inquiryEntity =
            new InquiryEntity(1L, "mockDesc", "mockCSV", "mockUser");

    private static final InquiryEntityDto inquiryEntityDto =
            new InquiryEntityDto();

    private InquiryEntityService inquiryEntityService;

    @Mock
    InquiryRepositoryDAO mockedInquiryRepositoryDAO;

    @Mock
    InquiryEntityDtoTransformer mockedInquiryEntityDtoTransformer;

    @Before
    public void setup() {
        inquiryEntityDto.setDescription("mockDesc");
        when(mockedInquiryRepositoryDAO.findOneByGid(anyLong())).thenReturn(inquiryEntity);
        when(mockedInquiryEntityDtoTransformer.generate(inquiryEntity)).thenReturn(inquiryEntityDto);
        when(mockedInquiryEntityDtoTransformer.generate(inquiryEntityDto)).thenReturn(inquiryEntity);
        inquiryEntityService = new InquiryEntityService(mockedInquiryRepositoryDAO, mockedInquiryEntityDtoTransformer);
    }

    @Test
    public void shouldProvideClientDtoWithValidDbInquiryEntity(){
        InquiryEntityDto validClientDto = inquiryEntityService.getInquiryEntity(1L);
        assertEquals (inquiryEntityDto.getDescription(), validClientDto.getDescription());
        // verify InquiryRepository.findOneById invoked
        verify(mockedInquiryRepositoryDAO).findOneByGid(1L);
        // verify DtoTransformer.getInquiryEntity invoked
        verify(mockedInquiryEntityDtoTransformer).generate(inquiryEntity);
    }

    @Test
    public void shouldProvideDbInquiryEntityWithValidClientDto(){

    }

}
