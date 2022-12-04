package ru.ds.education.testspringboot.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ds.education.testspringboot.db.entity.Category;
import ru.ds.education.testspringboot.db.entity.Tovar;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface TovarRepository extends JpaRepository<Tovar, Long> {

    @Query(value = "Select * from Tovar where id_category = :id_category", nativeQuery = true)
    List<Tovar> findByCategory(@Param("id_category") int id_category);

    @Query(value = "Select * from Tovar where id = :id", nativeQuery = true)
    Tovar getById(@Param("id") Long id);

    @Query(value = "Select name from Category", nativeQuery = true)
    List<String> getAllCategories();

    @Query(value = "Select en from Category en where en.name = :name")
    Category findCategory(@Param("name") String name);

    @Query(value = "Select id from Tovar where id_category = :id_category order by id DESC limit 1"
            , nativeQuery = true)
    Long findIdAdded(@Param("id_category") Long id_category);

    @Modifying
    @Transactional
    @Query(value = "Insert into Tovar VALUES (nextval('tovar_seq'), :id_category, :name, :cost," +
            " :quantity_in_stock, :description, :photo);", nativeQuery = true)
    void createTovar(
            @Param("id_category") Long id_category,
            @Param("name") String name,
            @Param("cost") double cost,
            @Param("quantity_in_stock") int quantity_in_stock,
            @Param("description") String description,
            @Param("photo") String photo
    );


    @Modifying
    @Transactional
    @Query(value = "UPDATE Tovar SET quantity_in_stock = :quantity WHERE id = :id", nativeQuery = true)
    void bookGoods(
            @Param("quantity") int quantity,
            @Param("id") Long id
    );


}

