package party.of.newyearliterature.work;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import party.of.newyearliterature.user.User;
import party.of.newyearliterature.user.UserDto;
import party.of.newyearliterature.user.UserRepository;
import party.of.newyearliterature.user.UserService;

/**
 * WorkServiceImpl
 */
@Service
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {

    private final WorkRepository repository;

    private final UserRepository userRepository;

    private final UserService userService;

    @Override
    @Transactional
    public WorkCreateDto submit(WorkCreateDto workDto) {
        UserDto userDto = userService.signUp(workDto.getUserDto());
        Work work = WorkMapper.map(workDto, findUserById(userDto.getId()));
        work = repository.save(work);
        return WorkMapper.map(work, userDto);
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

    private User findUserById(Long userId){
        Optional<User> optional = userRepository.findById(userId);
        if(optional.isEmpty()) return null;
        return optional.get();
    }

    
}