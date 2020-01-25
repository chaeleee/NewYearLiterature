package party.of.newyearliterature.like;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

import party.of.newyearliterature.controller.LikeController;
import party.of.newyearliterature.security.MyUserDetailsService;

/**
 * LikeControllerTest
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LikeController.class)
public class LikeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LikeService likeService;

    // SecuriryContext 생성을 위해
    @MockBean
    private MyUserDetailsService myUserDetailsService;

    @WithMockUser
    @Test
    public void Save_Like_Test() throws Exception{
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(new LikeCreateDto());
        
        LikeDto likeDto = new LikeDto();
        likeDto.setId(1L);
        when(likeService.save(any())).thenReturn(likeDto);
        // when
        mvc.perform(MockMvcRequestBuilders.post("/api/like")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(content))
        // then
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(notNullValue())));
    }

    @WithMockUser
    @Test
    public void Delete_Like_Test() throws Exception{
        // given
        Long likeId = 1L;
        LikeDto likeDto = new LikeDto();
        likeDto.setId(likeId);
        when(likeService.delete(likeId)).thenReturn(likeDto);
        // when
        mvc.perform(MockMvcRequestBuilders.delete("/api/like").param("workId", "1"))
        // then
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void Given_NotLogged_When_DeleteLike_Then_ForbiddenException() throws Exception {
        // when
        mvc.perform(MockMvcRequestBuilders.delete("/api/like").param("workId", "1"))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
    }


}
