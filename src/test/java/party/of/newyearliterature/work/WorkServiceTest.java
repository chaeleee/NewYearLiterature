package party.of.newyearliterature.work;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import party.of.newyearliterature.user.User;
import party.of.newyearliterature.user.UserRepository;

/**
 * WorkServiceTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkServiceTest {

    private WorkService service;
    @Mock
    private UserRepository userRepository;
    @Autowired
    private WorkRepository workRepository;

    @org.junit.Before
    public void setup(){
        service = new WorkServiceImpl(workRepository, userRepository);
    }

    // 공모작 등록 테스트
    @Test
    public void 작품_응모_테스트(){
        // 예시: 모든 정보를 잘 입력하고, submit 하면, 작품이 반환된다.
        submitSuccess();
        // 예시: article을 입력하지 않고, submit 하면, article 입력이 없다는 잘못된 요청이라고 에러메시지를 반환한다.
        // 예시: author 입력하지 않고, submit 하면, author 내용이 없다는 잘못된 요청이라고 에러메시지를 반환한다.
        // 예시: 존재하지 않는 userId를 입력하지 않고, submit 하면, 존재하지 않는 유저이며 잘못된 요청이라고 에러메시지를 반환한다.
    }

    // @Test
    public void submitSuccess(){
        // Given
        WorkDto dto = new WorkDto();
        dto.setArticle("article");
        dto.setAuthor("author");
        dto.setUserId(1L);
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // When
        WorkDto res = service.submit(dto);

        // Then
        assertEquals(dto.getArticle(), res.getArticle());
    }

    @Test
    public void 새유저_작업등록_작업과유저반환(){
        // Given
        String article = "article";
        String author = "author";
        Long userId = 1L;
        String email = "test@test.com";

        WorkDto dto = new WorkDto();
        dto.setArticle(article);
        dto.setAuthor(author);
        dto.setUserEmail(email);
        User user = new User();
        user.setId(userId);
        user.setEmail(email);
        when(userRepository.save(any(User.class))).thenReturn(user);
        // When
        WorkDto res = service.submit(dto);

        // Then
        assertEquals(dto.getArticle(), res.getArticle());
        assertEquals(dto.getAuthor(), res.getAuthor());
        assertEquals(userId, res.getUserId());
    }

    @Test
    public void 기존유저_작업등록_작업과유저반환(){
         // Given
         String article = "article";
         String author = "author";
         Long userId = 1L;
         String email = "test@test.com";
 
         WorkDto dto = new WorkDto();
         dto.setArticle(article);
         dto.setAuthor(author);
         dto.setUserId(userId);

         User user = new User();
         user.setId(userId);
         user.setEmail(email);
         when(userRepository.findById(userId)).thenReturn(Optional.of(user));
         // When
         WorkDto res = service.submit(dto);
 
         // Then
         assertEquals(dto.getArticle(), res.getArticle());
         assertEquals(dto.getAuthor(), res.getAuthor());
         assertEquals(userId, res.getUserId());
    }

    @Test
    public void 잘못된정보입력_새유저_작업등록_Badrequest반환(){

    }

    @Test
    public void 작업공란등록_BadRequest반환(){

    }


    
}