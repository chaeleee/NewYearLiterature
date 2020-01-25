package party.of.newyearliterature.like;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import party.of.newyearliterature.exception.ForbiddenException;
import party.of.newyearliterature.exception.NotFoundException;
import party.of.newyearliterature.user.User;
import party.of.newyearliterature.user.UserRepository;
import party.of.newyearliterature.work.Work;
import party.of.newyearliterature.work.WorkRepository;

/**
 * LikeServiceImpl
 */
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final WorkRepository workRepository;

    @Override
    public LikeDto save(LikeCreateDto likeCreateDto) {
        Optional<User> optUser = userRepository.findByEmail(likeCreateDto.getUserEmail());
        if(optUser.isEmpty()) throw new NotFoundException("유저가 존재하지 않습니다.");
        User user = optUser.get();
        Optional<Work> work = workRepository.findById(likeCreateDto.getWorkId());
        Like like = new Like(user, work.get());
        like = likeRepository.save(like);
        LikeDto likeDto = new LikeDto();
        likeDto.setId(like.getId());
        return likeDto;
    }

    @Override
    public LikeDto delete(long id) {

        Like like = likeRepository.findById(id).get();

        likeRepository.delete(like);

        LikeDto likeDto = new LikeDto();
        likeDto.setId(id);
        likeDto.setUserId(like.getUser().getId());
        likeDto.setWorkId(1L);
        likeDto.setUsername(like.getUser().getName());
        return likeDto;
    }

    @Override
    public void deleteByWorkId(long workId, String userEmail) {
        Optional<User> optUser = userRepository.findByEmail(userEmail);
        if(optUser.isEmpty()) throw new ForbiddenException("유저가 존재하지 않습니다.");
        User user = optUser.get();

        Optional<Work> optWork = workRepository.findById(workId);
        if(optWork.isEmpty()) throw new NotFoundException(workId + ": 해당 출품작이 존재하지 않습니다.");
        Work work = optWork.get();

        Like like = likeRepository.findByWorkIdAndUserId(work.getId(), user.getId());
        if(Objects.isNull(like)) throw new NotFoundException("해당 추천이 존재하지 않습니다.");

        likeRepository.delete(like);

    }

    
}