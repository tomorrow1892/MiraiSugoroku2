package ksp.group3.miraiSugoroku.security;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    private String password;
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    Role role;
    public enum Role{
        ADMIN,
        SUB_ADMIN
    }

    public boolean checkPassword(String pass){
        if(pass == this.password) return true;
        else return false;
    }
}
