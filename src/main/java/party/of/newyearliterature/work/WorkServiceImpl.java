package party.of.newyearliterature.work;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import party.of.newyearliterature.user.User;
import party.of.newyearliterature.user.UserRepository;

/**
 * WorkServiceImpl
 */
@Service
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {

    private final WorkRepository repository;

    private final UserRepository userRepository;

    @Override
    public WorkDto submit(WorkDto workDto) {
        Work work = new Work();
        work.setArticle(workDto.getArticle());
        work.setAuthor(workDto.getAuthor());
        // Optional<User> opt = userRepository.findById(workDto.getId());
        // work.setUser(opt.get());

        work = repository.save(work);
        WorkDto dto = new WorkDto();
        dto.setArticle(work.getArticle());
        dto.setAuthor(work.getAuthor());
        // dto.setUserId(work.getUser().getId());
        dto.setCreatedAt(work.getCreatedAt().toEpochSecond(ZoneOffset.UTC));
        return dto;
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