package ru.astradev.toy_store.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class Users {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
    @SequenceGenerator(name = "users_generator", sequenceName = "users_seq", allocationSize = 1)
    private Long id;

    private Long id_telegram;
    private String name;
    private String password;
    private boolean active;
    private String firstname;
    private String lastname;
    private String phone;
    private String mail;
    private boolean agreement;

//    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name="user_id"))
//    @Enumerated(EnumType.STRING)
//    private Set<Roles> roles;

    public Users(Long id_telegram, String name, String password, boolean active, String firstname, String lastname, String phone, String mail, boolean agreement) {
        this.id_telegram = id_telegram;
        this.name = name;
        this.password = password;
        this.active = active;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.mail = mail;
        this.agreement = agreement;
    }
}
