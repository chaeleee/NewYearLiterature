package party.of.newyearliterature.work;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import party.of.newyearliterature.user.User;

/**
 * Work
 */
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Entity
@Table(name = "tbl_work")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String article;

    private String author;

    // @ManyToOne(cascade = CascadeType.PERSIST)
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    private User user;
    
    @Enumerated(EnumType.STRING)
    private Award award;

    enum Award {
        GOLD, SILVER, BRONZE, BASIC
    }
    
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Work(){}
    public Work(String article, String author, User user){
        this.article = article;
        this.author = author;
        this.user = user;
    }
}