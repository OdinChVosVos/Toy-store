package ru.astradev.toy_store.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.astradev.toy_store.db.entity.ArchiveCarts;

import javax.transaction.Transactional;

@Repository
public interface ArchiveCartsRepository extends JpaRepository<ArchiveCarts, Long> {

    @Modifying
    @Transactional
    @Query(value = "Insert into Archive_Carts VALUES (nextval('archive_carts_seq'), :id_user);", nativeQuery = true)
    void add(@Param("id_user") Long id);

    @Query(value = "Select id from Archive_Carts where id_user = :id_user", nativeQuery = true)
    Long getLastId(@Param("id_user") Long id_user);

    @Modifying
    @Transactional
    @Query(value = "Delete From Archive_Carts Where id = :cartId", nativeQuery = true)
    void removeById(@Param("cartId") Long cartId);

}
