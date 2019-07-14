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

    @Override
    @Transactional
    public WorkDto submit(WorkDto workDto) {
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

    private User findUserById(Long userId){
        Optional<User> optional = userRepository.findById(userId);
        if(optional.isEmpty()) return null;
        return optional.get();
    }

    
}