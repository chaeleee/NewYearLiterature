package party.of.newyearliterature.work;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import party.of.newyearliterature.controller.WorkController;
import party.of.newyearliterature.security.MyUserDetailsService;

/**
 * WorkControllerTest
 */
@WebMvcTest(controllers = WorkController.class)
@RunWith(SpringRunner.class)
public class WorkControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WorkService workService;

    @MockBean
    private MyUserDetailsService myUserDetailsService;

    @Test
    public void Given_WorkCreateDto_When_Submit_Then_WorkDto() throws Exception{
        // given
        WorkCreateDto workCreateDto = new WorkCreateDto();
        workCreateDto.setArticle("article");
        workCreateDto.setAuthor("author");
        String content = new ObjectMapper().writeValueAsString(workCreateDto);

        WorkDto workDto = new WorkDto();
        workDto.setId(1L);

        when(workService.submit(workCreateDto)).thenReturn(workDto);

        // when
        mvc.perform(MockMvcRequestBuilders.post("/api/works")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(content))
        // then
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(notNullValue())));
    }

    @Test
    public void Given_Sort_When_GetAll_Then_WorkDtos() throws Exception{
        // Given
        List<WorkDto> works = new ArrayList<>();
        WorkDto work1 = new WorkDto();
        work1.setId(1L);
        work1.setArticle("article1");
        work1.setAuthor("author1");
        works.add(work1);

        when(workService.getAll(any(), any(), any())).thenReturn(works);
    
        mvc.perform(MockMvcRequestBuilders.get("/api/works")
        .param("sort", "id,desc"))
        // Then
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(work1.getId().intValue())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].article", is(work1.getArticle())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].author", is(work1.getAuthor())));
    }

    @WithMockUser(username = "user@of.com")
    @Test
    public void Given_Auth_When_GetAll_Then_isLIked_WorkDto() throws Exception{
        // Given
        WorkDto workDto = new WorkDto();
        workDto.setIsLiked(true);
        List<WorkDto> dtos = new ArrayList<>();
        dtos.add(workDto);

        when(workService.getAll(any(), any(), eq("user@of.com"))).thenReturn(dtos);

        // When
        mvc.perform(MockMvcRequestBuilders.get("/api/works"))
        // Then
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].isLiked", is(workDto.getIsLiked().booleanValue())));
    }

    @WithMockUser
    @Test
    public void Given_LoggedIn_When_Submit_Then_WorkDto() throws Exception {
        // Given
        WorkCreateLoggedDto createDto = new WorkCreateLoggedDto();
        createDto.setArticle("article");
        createDto.setAuthor("author");
        createDto.setUserEmail(null);

        String content = new ObjectMapper().writeValueAsString(createDto);

        WorkDto workDto = new WorkDto();
        workDto.setId(1L);
        workDto.setArticle(createDto.getArticle());
        workDto.setAuthor(createDto.getAuthor());

        when(workService.submitLogged(any())).thenReturn(workDto);

        // When Then
        mvc.perform(
            MockMvcRequestBuilders.post("/api/works/logged")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(content)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.article", is(workDto.getArticle())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.author", is(workDto.getAuthor())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(workDto.getId().intValue())));
    }
}