package party.of.newyearliterature.like.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import party.of.newyearliterature.user.entities.User;
import party.of.newyearliterature.work.entities.Work;

/**
 * Like
 */
@Entity
@Table(name="tbl_like")
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
}