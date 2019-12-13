package party.of.newyearliterature.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public LikeDto save(@RequestBody LikeCreateDto likeCreateDto, Authentication auth){
        likeCreateDto.setUsername(auth.getName());
        return likeService.save(likeCreateDto);
    }

    @DeleteMapping("/api/like/{id}")
    public void delete(@PathVariable Long id){
        likeService.delete(id);
    }
}