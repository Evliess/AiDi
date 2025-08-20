package evliess.io.jpa;

import evliess.io.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface ConsumerRepo extends JpaRepository<Consumer, Long> {
    @Query("SELECT a from Consumer a WHERE a.phone= :phone")
    Consumer findByPhone(String phone);

    @Modifying
    @Query("UPDATE Consumer a set a.leftCount = :leftCount WHERE a.phone = :phone")
    @Transactional
    void updateLeftCountByPhone(String phone, Integer leftCount);

    @Modifying
    @Query("UPDATE Consumer a set a.name = :name WHERE a.phone = :phone")
    @Transactional
    void updateNameByPhone(String phone, String name);

}
