package ru.astradev.toy_store.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.astradev.toy_store.db.entity.Booked;


import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BookedRepository extends JpaRepository<Booked, Long> {

    @Modifying
    @Transactional
    @Query(value = "Insert into Booked VALUES (nextval('booked_seq'), :id_tovar, :booked_quantity, :id_user)",
            nativeQuery = true)
    void putInBooked(
            @Param("id_tovar") Long id_tovar,
            @Param("booked_quantity") int booked_quantity,
            @Param("id_user") Long id_user
    );

    @Query(value = "Select * from Booked where id_user = :id_user"
            , nativeQuery = true)
    List<Booked> getByUser(@Param("id_user") Long id_user);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Booked where id = :id",
            nativeQuery = true)
    void deleteBooked(@Param("id") Long id);

}