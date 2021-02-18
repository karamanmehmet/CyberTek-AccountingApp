package com.cybertek.accounting.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity{


    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private boolean active;
    private String phone;


    @ManyToMany (cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public User(String firstname, String lastname,  String email, boolean active, String phone,
                Set<Role> roles,String password) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.active = active;
        this.phone = phone;
        this.roles = roles;

    }

    public User(long id, LocalDateTime insertDateTime, String insertUserId, LocalDateTime lastUpdateDateTime,
                String lastUpdateUserId, String firstname, String lastname, String username, String email, boolean active, String phone,
                Set<Role> roles,String password) {
        super(id, insertDateTime, insertUserId, lastUpdateDateTime, lastUpdateUserId);
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.active = active;
        this.phone = phone;
        this.roles = roles;
    }

    public User() {

    }


    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

     public User(String firstname, String lastname,  String email, boolean active, String phone,
                String password) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.active = active;
        this.phone = phone;


    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;


}
