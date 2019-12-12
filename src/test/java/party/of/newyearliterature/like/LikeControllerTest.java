package party.of.newyearliterature.like;

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

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

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


}
