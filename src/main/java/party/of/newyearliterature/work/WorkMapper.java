package party.of.newyearliterature.work;

import java.time.ZoneOffset;

import party.of.newyearliterature.user.User;
import party.of.newyearliterature.user.UserDto;

/**
 * WorkMapper
 */
public class WorkMapper {
    
    public static Work map(WorkCreateDto dto, User user){
        Work work = new Work();
        work.setArticle(dto.getArticle());
        work.setAuthor(dto.getAuthor());
        work.setUser(user);
        return work;
    }

    public static WorkCreateDto map(Work work, UserDto userDto){
        WorkCreateDto dto = new WorkCreateDto();
        dto.setId(work.getId());
        dto.setArticle(work.getArticle());
        dto.setAuthor(work.getAuthor());
        dto.setCreatedAt(work.getCreatedAt().toEpochSecond(ZoneOffset.UTC));
        dto.setUserDto(userDto);
        return dto;
    }

}