package ru.ds.education.testspringboot.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ds.education.testspringboot.db.entity.Users;
import ru.ds.education.testspringboot.db.entity.UsersRoles;

import javax.transaction.Transactional;

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

}
