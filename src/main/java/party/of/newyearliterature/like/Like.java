package party.of.newyearliterature.like;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import party.of.newyearliterature.user.User;
import party.of.newyearliterature.work.Work;

/**
 * Like
 */
@Entity
@Getter
@Table(name="tbl_like")
@EqualsAndHashCode(of={"id"})
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Work work;
    
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Like(){}
    public Like(User user, Work work){
        this.user = user;
        this.work = work; 
    }
}