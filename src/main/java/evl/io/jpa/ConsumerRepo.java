package evl.io.jpa;

import evl.io.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ConsumerRepo extends JpaRepository<Consumer, Long> {
    @Query("SELECT a from Consumer a WHERE a.phone= :phone")
    Consumer findByPhone(String phone);

    @Query("SELECT a.name from Consumer a WHERE a.phone= :phone")
    String findNameByPhone(String phone);

    @Modifying
    @Query("UPDATE Consumer a set a.leftCount = :leftCount WHERE a.phone = :phone")
    @Transactional
    void updateLeftCountByPhone(String phone, Integer leftCount);

    @Modifying
    @Query("UPDATE Consumer a set a.name = :name WHERE a.phone = :phone")
    @Transactional
    void updateNameByPhone(String phone, String name);

    @Query("SELECT a from Consumer a ORDER BY a.leftCount DESC, a.expiredAt DESC")
    List<Consumer> findAllOrdered();

}
