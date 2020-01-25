package party.of.newyearliterature.controller;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import party.of.newyearliterature.exception.ForbiddenException;
import party.of.newyearliterature.work.WorkCreateDto;
import party.of.newyearliterature.work.WorkCreateLoggedDto;
import party.of.newyearliterature.work.WorkDto;
import party.of.newyearliterature.work.WorkService;

/**
 * WorkController
 */
@RestController
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;

    @PostMapping("/api/works")
    public WorkDto submit(@RequestBody WorkCreateDto workCreateDto){
        return workService.submit(workCreateDto);
    }

    @PostMapping("/api/works/logged")
    public WorkDto submitLogged(@RequestBody WorkCreateLoggedDto createDto, Principal principal){
        if(Objects.isNull(principal)) throw new ForbiddenException("로그인 정보가 없습니다.");
        createDto.setUserEmail(principal.getName());
        return workService.submitLogged(createDto);
    }

    @GetMapping("/api/works")
    public List<WorkDto> getWorksAll(
        @RequestParam(required = false, defaultValue = "") String author, 
        @SortDefault(value = "createdAt", direction = Direction.DESC) Sort sort,
        Principal principal){

        String loginUserEmail = null;
        if(!Objects.isNull(principal)) loginUserEmail = principal.getName();
            
        return workService.getAll(author, sort, loginUserEmail).stream()
            .map(work->{
                String article = work.getArticle();
                if(!Objects.isNull(work.getArticle()))
                    work.setArticle(article.replaceAll("\n", "<br />"));
                return work;
            }).collect(Collectors.toList());
    }
}