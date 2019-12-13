package party.of.newyearliterature.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import party.of.newyearliterature.like.LikeCreateDto;
import party.of.newyearliterature.like.LikeDto;
import party.of.newyearliterature.like.LikeService;

/**
 * LikeController
 */
@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/api/like")
    public LikeDto save(LikeCreateDto likeCreateDto){
        return likeService.save(likeCreateDto);
    }

    @DeleteMapping("/api/like/{id}")
    public LikeDto delete(@PathVariable Long id){
        return likeService.delete(id);
    }
}