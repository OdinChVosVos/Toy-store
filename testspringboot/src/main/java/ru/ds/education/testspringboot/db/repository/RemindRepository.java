package ru.ds.education.testspringboot.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ds.education.testspringboot.db.entity.Remind;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RemindRepository extends JpaRepository<Remind, Long> {

    @Modifying
    @Transactional
    @Query(value = "Update Remind Set is_delivered = :is_delivered Where id_tovar = :id_tovar"
            , nativeQuery = true)
    void putIsDelivered(@Param("is_delivered") boolean isDelivered,
                        @Param("id_tovar") Long idTovar
    );

    @Query(value = "Select * from Remind where id_user = :id_user", nativeQuery = true)
    List<Remind> getByUser(@Param("id_user") Long id_user);

    @Modifying
    @Transactional
    @Query(value = "Insert into Remind VALUES (nextval('remind_seq'), :id_user, :id_tovar, :is_delivered, :quantity);",
            nativeQuery = true)
    void add(@Param("id_user") Long idUser,
             @Param("id_tovar") Long idTovar,
             @Param("is_delivered") boolean isDelivered,
             @Param("quantity") int quantity
    );


    @Modifying
    @Transactional
    @Query(value = "Delete from Remind WHERE id_user = :id_user AND id_tovar = :id_tovar",
            nativeQuery = true)
    void deleteFromRemind(
            @Param("id_user") Long idUser,
            @Param("id_tovar") Long idTovar
    );

}
