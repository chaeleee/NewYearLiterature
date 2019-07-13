package party.of.newyearliterature.work;

import java.util.List;

import org.springframework.data.domain.Sort;

/**
 * WorkService
 */
public interface WorkService {
    
    WorkCreateDto submit(WorkCreateDto workDto);

    List<WorkDto> getAll(Sort sort);

    List<WorkDto> getByUser(WorkDto workDto);

    WorkDto getOne(WorkDto workDto);

    WorkDto remove(WorkDto workDto);

    WorkDto update(WorkDto workDto);

}