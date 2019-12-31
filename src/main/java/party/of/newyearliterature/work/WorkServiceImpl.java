package party.of.newyearliterature.work;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import party.of.newyearliterature.exception.BadRequestException;
import party.of.newyearliterature.like.Like;
import party.of.newyearliterature.like.LikeRepository;
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
    private final LikeRepository likeRepository;

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
    public List<WorkDto> getAll(String author, Sort sort, String loginUserEmail) {
        if(Objects.isNull(author)) author = "";
        List<Work> works = repository.findByAuthorContaining(author, sort);
        List<WorkDto> workDtos = new ArrayList<>();
        for(Work work : works){
            WorkDto dto = WorkMapper.map(work, true);
            List<Like> likes = likeRepository.findByWorkId(dto.getId());
            boolean isLiked = getIsLiked(likes, loginUserEmail);
            dto.setIsLiked(isLiked);
            dto.setNumOfLikes(likes.size());
            workDtos.add(dto);
        }
        return workDtos;
    }

    private boolean getIsLiked(List<Like> likes, String loginUserEmail) {
        for(Like like : likes){
            if(like.getUser().getEmail().equals(loginUserEmail))
                return true;
        }
        return false;
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