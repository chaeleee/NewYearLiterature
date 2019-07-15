package party.of.newyearliterature.work;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import party.of.newyearliterature.exception.BadRequestException;

/**
 * WorkServiceImpl
 */
@Service
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {

    private final WorkRepository repository;

    @Override
    @Transactional
    public WorkDto submit(WorkDto workDto) {
        validate(workDto);
        Work work = WorkMapper.map(workDto, true);
        work = repository.save(work);
        return WorkMapper.map(work, true);
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

    private void validate(WorkDto workDto){
        if(workDto.getArticle().isBlank() || workDto.getAuthor().isBlank()){
            throw new BadRequestException("본문과 작명이 비어있습니다");
        }   
    }

    
}