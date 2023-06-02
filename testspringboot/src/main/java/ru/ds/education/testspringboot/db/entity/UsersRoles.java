package ru.ds.education.testspringboot.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Users_Roles")
public class UsersRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_roles_generator")
    @SequenceGenerator(name = "users_roles_generator", sequenceName = "users_roles_seq", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Roles role;

    public UsersRoles(Users user, Roles role) {
        this.user = user;
        this.role = role;
    }
}