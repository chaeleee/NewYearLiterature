package party.of.newyearliterature.user;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;
import party.of.newyearliterature.role.Role;

/**
 * User
 */
@Setter
@Getter
@Entity
@Table(name="tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
   
    private String email;

    private String name;

    private String password;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public User(){}

    public User(String email, String name){
        this.email = email;
        this.name = name;
    }

    public User(String email, String name, String password){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(String email, String password, String name, Role role){
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

}