package ru.ds.education.testspringboot.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ds.education.testspringboot.db.entity.Category;
import ru.ds.education.testspringboot.db.entity.Users;

import javax.transaction.Transactional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying
    @Transactional
    @Query(value = "Insert into Category VALUES (nextval('category_seq'), :name, :description);",
            nativeQuery = true)
    void add(
            @Param("name") String name,
            @Param("description") String description
    );

    @Query(value = "Select n from Category n where n.name = :name")
    Category getByName(@Param("name") String name);


}
