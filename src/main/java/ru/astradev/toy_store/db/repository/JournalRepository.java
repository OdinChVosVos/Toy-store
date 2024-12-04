package ru.astradev.toy_store.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.astradev.toy_store.db.entity.Journal;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {

    @Modifying
    @Transactional
    @Query(value = "Insert into Journal VALUES (nextval('journal_seq'), :user_id, :datein, :description)",
            nativeQuery = true)
    void add(
            @Param("user_id") Long user_id,
            @Param("datein") Date datein,
            @Param("description") String description
    );

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Journal WHERE id = :id", nativeQuery = true)
    void delete(@Param("id") Long id);

    @Query(value = "Select * from Journal where user_id = :user_id ORDER BY id DESC Limit 1",
        nativeQuery = true)
    Journal getLast(@Param("user_id") Long user_id);

    @Query(value = "Select * from Journal", nativeQuery = true)
    List<Journal> getAll();


}