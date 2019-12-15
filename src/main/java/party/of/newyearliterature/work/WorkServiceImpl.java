package party.of.newyearliterature.work;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import party.of.newyearliterature.exception.BadRequestException;
import party.of.newyearliterature.user.User;
import party.of.newyearliterature.user.UserService;

/**
 * WorkServiceImpl
 */
@Service
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {

    private final WorkRepository repository;
    private final UserService userService;

    @Override
    @Transactional
    public WorkDto submit(WorkCreateDto workCreateDto) {
        validate(workCreateDto);
        
        User user = userService.signUp(workCreateDto.getUserDto());

        Work work = WorkMapper.map(workCreateDto);
        work.setUser(user);
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

    private void validate(WorkCreateDto workCreateDto){
        if(workCreateDto.getArticle().isBlank() || workCreateDto.getAuthor().isBlank()){
            throw new BadRequestException("본문과 작명이 비어있습니다");
        }
        if(Objects.isNull(workCreateDto.getUserDto())){
            throw new BadRequestException("유저 정보가 없습니다.");
        }
    }

    

    
}