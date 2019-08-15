package party.of.newyearliterature.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
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
    public WorkDto submit(@RequestBody(required=true)  WorkDto workDto){
        return workService.submit(workDto);
    }
}