package ru.ds.education.testspringboot.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ds.education.testspringboot.db.entity.Users;
import ru.ds.education.testspringboot.db.entity.UsersRoles;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UsersRolesRepository extends JpaRepository<UsersRoles, Long> {

    @Modifying
    @Transactional
    @Query(value = "Insert into Users_Roles VALUES (nextval('users_roles_seq'), :user_id, :role_id)",
            nativeQuery = true)
    void add(
            @Param("user_id") Long user_id,
            @Param("role_id") Long role_id
    );

    @Query(value = "Select * from Users_Roles", nativeQuery = true)
    List<UsersRoles> getAll();

    @Modifying
    @Transactional
    @Query(value = "UPDATE Users_Roles SET role_id = :role_id WHERE user_id = :user_id", nativeQuery = true)
    void update(
            @Param("user_id") Long user_id,
            @Param("role_id") Long role_id
    );

}
