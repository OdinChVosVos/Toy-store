package ru.astradev.toy_store.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.astradev.toy_store.db.entity.Carts;

import javax.transaction.Transactional;

@Repository
public interface CartsRepository extends JpaRepository<Carts, Long> {

    @Modifying
    @Transactional
    @Query(value = "Insert into Carts VALUES (nextval('carts_seq'), :id_user);", nativeQuery = true)
    void add(@Param("id_user") Long id);

    @Query(value = "Select id from Carts where id_user = :id_user", nativeQuery = true)
    Long getLastId(@Param("id_user") Long id_user);

    @Modifying
    @Transactional
    @Query(value = "Delete From Carts Where id = :cartId", nativeQuery = true)
    void removeById(@Param("cartId") Long cartId);

}
