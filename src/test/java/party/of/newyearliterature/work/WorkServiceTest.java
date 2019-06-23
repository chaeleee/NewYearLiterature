package party.of.newyearliterature.work;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * WorkServiceTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkServiceTest {

    @Autowired private WorkService service;

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

        // When
        WorkDto res = service.submit(dto);

        // Then
        assertEquals(dto.getArticle(), res.getArticle());
    }
    
}