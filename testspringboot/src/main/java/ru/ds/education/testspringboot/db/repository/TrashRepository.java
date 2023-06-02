package ru.ds.education.testspringboot.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ds.education.testspringboot.db.entity.Trash;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TrashRepository extends JpaRepository<Trash, Long> {


    @Modifying
    @Transactional
    @Query(value = "Insert into Trash VALUES (nextval('trash_seq'), :id_tovar, :quantity, :id_cart);", nativeQuery = true)
    void add(
            @Param("id_tovar") Long id_tovar,
            @Param("quantity") int quantity,
            @Param("id_cart") Long id_cart
    );

    @Query(value = "Select id from Tovar where id_category = :id_category order by id DESC limit 1"
            , nativeQuery = true)
    Long findIdAdded(@Param("id_category") Long id_category);

    @Transactional
    @Query(value = "Select * from Trash where id_cart = :id_cart"
            , nativeQuery = true)
    List<Trash> getByCart(@Param("id_cart") Long id_cart);

    @Query(value = "Select * from Trash where (id_tovar = :id_tovar) AND (id_cart = :id_cart)"
            , nativeQuery = true)
    List<Trash> getTovarFromCart(
            @Param("id_tovar") Long id_tovar,
            @Param("id_cart") Long id_cart
    );

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Trash WHERE id_cart = :id_cart", nativeQuery = true)
    void deleteByCart(@Param("id_cart") Long id_cart);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Trash WHERE id = :id", nativeQuery = true)
    void delete(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Trash SET quantity = :quantity WHERE id = :id", nativeQuery = true)
    void updateQuantity(@Param("id") Long id,
                        @Param("quantity") int quantity);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Trash WHERE id_cart = :id_cart AND id_tovar = :id_tovar", nativeQuery = true)
    void deleteFromCart(
            @Param("id_cart") Long id_cart,
            @Param("id_tovar") Long id_tovar
    );

    @Modifying
    @Transactional
    @Query(value = "UPDATE Trash SET quantity = quantity+1 WHERE id = :id", nativeQuery = true)
    void addOne(@Param("id") Long id);



}