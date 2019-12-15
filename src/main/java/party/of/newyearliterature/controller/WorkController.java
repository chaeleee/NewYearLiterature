package party.of.newyearliterature.controller;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/api/work")
    public WorkDto submit(@RequestBody WorkCreateDto workCreateDto){
        return workService.submit(workCreateDto);
    }

    @GetMapping("/api/works")
    public List<WorkDto> getWorksAll(Sort sort){
        return workService.getAll(sort);
    }
}