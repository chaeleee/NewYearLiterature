package party.of.newyearliterature.work;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * WorkRepoTest
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class WorkRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WorkRepository workRepository;

    private List<Work> persists = new ArrayList<>();

    @Before
    public void setup(){
        // Given
        Work work1 = new Work("article1", "author1", null);
        Work work2 = new Work("article2", "author2", null);
        Work work3 = new Work("article3", "author3", null);

        entityManager.persist(work1);
        entityManager.persist(work2);
        entityManager.persist(work3);

        persists.add(work1);
        persists.add(work2);
        persists.add(work3);
    }

    @Test 
    public void Test_Work_Save(){
        // Given
        Work work = new Work("article", "author", null);
        // When
        work = workRepository.save(work);
        // Then
        assertFalse(Objects.isNull(work.getId()));
    }

    @Test
    public void Given_3Works_When_sortByCreatedDesc_Then_Sorted(){

        // When
        Sort sort = new Sort(Direction.DESC, "createdAt");
        List<Work> works = workRepository.findByAuthorContaining("", sort);
        // Then
        for(int i=0; i<works.size()-1; i++){
            LocalDateTime current = works.get(i).getCreatedAt();
            LocalDateTime next = works.get(i+1).getCreatedAt();
            assertTrue(current.isAfter(next));
        }
    }

    @Test
    public void Given_3Works_When_sortByCreatedAsc_Then_Sorted(){
        
        // When
        Sort sort = new Sort(Direction.ASC, "createdAt");
        List<Work> works = workRepository.findByAuthorContaining("", sort);
        // Then
        for(int i=0; i<works.size()-1; i++){
            LocalDateTime current = works.get(i).getCreatedAt();
            LocalDateTime next = works.get(i+1).getCreatedAt();
            assertTrue(current.isBefore(next));
        }
    }

    @Test
    public void Given_3Works_When_findByNameContaing(){

        Work work2 = this.persists.get(1);

        List<Work> works = workRepository.findByAuthorContaining(work2.getAuthor().substring(3), null);

        assertEquals(1, works.size());
        assertEquals(work2.getAuthor(), works.get(0).getAuthor());
    }

}