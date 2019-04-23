import core.services.InquiryEntityService;
import core.transformers.InquiryEntityDtoTransformer;
import db.entity.InquiryEntity;
import db.repository.InquiryRepositoryDAO;
import model.InquiryEntityDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class InquiryEntityServiceTest {

    private static final InquiryEntity inquiryEntity =
            new InquiryEntity(1L, "mockDesc", "mockCSV", "mockUser");

    private static final InquiryEntityDto inquiryEntityDto =
            new InquiryEntityDto();

    private InquiryEntityService inquiryEntityService;

    @Mock
    InquiryRepositoryDAO mockedInquiryRepositoryDAO;

    @Mock
    InquiryEntityDtoTransformer mockedInquiryEntityDtoTransformer;

    @Test
    public void shouldProvideClientDtoWithValidDbInquiryEntity(){
        inquiryEntityService = new InquiryEntityService(mockedInquiryRepositoryDAO, mockedInquiryEntityDtoTransformer);

        when(mockedInquiryRepositoryDAO.findOneByGid(anyLong())).thenReturn(inquiryEntity);
        when(mockedInquiryEntityDtoTransformer.generate(inquiryEntity)).thenReturn(inquiryEntityDto);
        when(mockedInquiryEntityDtoTransformer.generate(inquiryEntityDto)).thenReturn(inquiryEntity);

        inquiryEntityDto.setDescription("mockDesc");

        InquiryEntityDto validClientDto = inquiryEntityService.getInquiryEntity(1L);
        assertEquals (inquiryEntityDto.getDescription(), validClientDto.getDescription());
        verify(mockedInquiryRepositoryDAO).findOneByGid(1L);
        verify(mockedInquiryEntityDtoTransformer).generate(inquiryEntity);
    }

    @Test
    public void shouldProvideDbInquiryEntityWithValidClientDto(){
        //TBD
    }

}
