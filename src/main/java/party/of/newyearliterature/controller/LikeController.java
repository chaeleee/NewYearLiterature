package party.of.newyearliterature.controller;

import java.security.Principal;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import party.of.newyearliterature.exception.ForbiddenException;
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
        if(Objects.isNull(auth)) throw new ForbiddenException("추천 권한이 없습니다. 로그인 상태를 확인해주세요.");
        likeCreateDto.setUserEmail(auth.getName());
        return likeService.save(likeCreateDto);
    }

    @DeleteMapping("/api/like")
    public void delete(@RequestParam Long workId, Principal principal){
        if(Objects.isNull(principal)) throw new ForbiddenException("권한이 없습니다. 로그인 상태를 확인해주세요.");
        likeService.deleteByWorkId(workId, principal.getName());
    }
}