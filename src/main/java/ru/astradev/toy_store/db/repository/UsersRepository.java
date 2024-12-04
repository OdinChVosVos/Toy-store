package ru.astradev.toy_store.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.astradev.toy_store.db.entity.Users;

import javax.transaction.Transactional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query(value = "Select n from Users n where n.id_telegram = :id_telegram")
    Users getByTgID(@Param("id_telegram") Long id_telegram);

    @Query(value = "Select n from Users n where n.name = :name")
    Users getByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "Insert into Users VALUES (nextval('users_seq'), :id_telegram, :name, :password, :active, :firstname, :lastname, :phone, :mail, :agreement);",
            nativeQuery = true)
    void add(
            @Param("id_telegram") Long id_telegram,
            @Param("name") String name,
            @Param("password") String password,
            @Param("active") boolean active,
            @Param("firstname") String firstname,
            @Param("lastname") String lastname,
            @Param("phone") String phone,
            @Param("mail") String mail,
            @Param("agreement") boolean agreement
    );

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Users WHERE id = :id", nativeQuery = true)
    void delete(@Param("id") Long id);

}
