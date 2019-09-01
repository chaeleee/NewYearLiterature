package party.of.newyearliterature.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import party.of.newyearliterature.work.WorkService;

/**
 * TestMvcSecurity
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class TestMvcSecurity {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WorkService workService;

    @WithMockUser
    @Test
    public void getIndex() throws Exception{
        this.mvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }

}