package party.of.newyearliterature.work;

import java.time.ZoneOffset;
import java.util.Objects;

import party.of.newyearliterature.user.User;
import party.of.newyearliterature.user.UserMapper;

/**
 * WorkMapper
 */
public class WorkMapper {
    
    public static Work map(WorkCreateDto dto){
        Work work = new Work();
        work.setArticle(dto.getArticle());
        work.setAuthor(dto.getAuthor());
        work.setUser(UserMapper.map(dto.getUserDto()));
        return work;
    }

    public static WorkDto map(Work work, Boolean isCascade){
        WorkDto dto = new WorkDto();
        dto.setId(work.getId());
        dto.setArticle(work.getArticle());
        dto.setAuthor(work.getAuthor());
        if(!Objects.isNull(work.getCreatedAt())){
            dto.setCreatedAt(work.getCreatedAt().toEpochSecond(ZoneOffset.UTC));
        }
        if(isCascade && !Objects.isNull(work.getUser())){
            dto.setUserDto(UserMapper.map(work.getUser()));
        }
        return dto;
    }

    public static Work map(WorkDto dto, Boolean isCascade){
        Work work = new Work();
        work.setArticle(dto.getArticle());
        work.setAuthor(dto.getAuthor());
        if(isCascade){
            work.setUser(UserMapper.map(dto.getUserDto()));
        }
        return work;
    }

	public static Work map(WorkCreateLoggedDto createDto, User user) {
        Work work = new Work();
        work.setArticle(createDto.getArticle());
        work.setAuthor(createDto.getAuthor());
        work.setUser(user);
		return work;
	}

}