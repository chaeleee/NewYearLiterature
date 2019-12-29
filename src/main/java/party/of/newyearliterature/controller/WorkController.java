package party.of.newyearliterature.controller;

import java.util.List;
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
import party.of.newyearliterature.work.WorkCreateDto;
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

    @GetMapping("/api/works")
    public List<WorkDto> getWorksAll(
        @RequestParam(required = false, defaultValue = "") String author, 
        @SortDefault(value = "createdAt", direction = Direction.DESC) Sort sort){

        return workService.getAll(author, sort).stream().map(work->{
            String article = work.getArticle();
            work.setArticle(article.replaceAll("\n", "<br />"));
            return work;
        }).collect(Collectors.toList());
    }
}