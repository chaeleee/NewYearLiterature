package party.of.newyearliterature.like;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
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
        User user = userRepository.findByName(likeCreateDto.getUsername());
        Optional<Work> work = workRepository.findById(likeCreateDto.getWorkId());
        Like like = new Like(user, work.get());
        like = likeRepository.save(like);
        LikeDto likeDto = new LikeDto();
        likeDto.setId(like.getId());
        return likeDto;
    }

    @Override
    public LikeDto delete(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    
}