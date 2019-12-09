package party.of.newyearliterature.like;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * LikeServiceImpl
 */
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;

    @Override
    public Like save(Like like) {
        likeRepository.save(like);
        return like;
    }

    @Override
    public Like delete(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    
}