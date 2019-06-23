package party.of.newyearliterature.work;

import java.util.List;

import org.springframework.data.domain.Sort;

import lombok.RequiredArgsConstructor;

/**
 * WorkServiceImpl
 */
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {

    private final WorkRepository repository;

    @Override
    public WorkDto submit(WorkDto workDto) {
        return null;
    }

    @Override
    public List<WorkDto> getAll(Sort sort) {
        return null;
    }

    @Override
    public List<WorkDto> getByUser(WorkDto workDto) {
        return null;
    }

    @Override
    public WorkDto getOne(WorkDto workDto) {
        return null;
    }

    @Override
    public WorkDto remove(WorkDto workDto) {
        return null;
    }

    @Override
    public WorkDto update(WorkDto workDto) {
        return null;
    }

    
}