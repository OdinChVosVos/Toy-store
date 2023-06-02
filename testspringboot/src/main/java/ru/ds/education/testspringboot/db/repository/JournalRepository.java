package ru.ds.education.testspringboot.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ds.education.testspringboot.db.entity.Category;
import ru.ds.education.testspringboot.db.entity.Journal;

import javax.transaction.Transactional;
import java.util.Date;

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

    @Query(value = "Select * from Journal where user_id = :user_id ORDER BY id DESC Limit 1",
        nativeQuery = true)
    Journal getLast(@Param("user_id") Long user_id);


}