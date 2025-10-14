package evl.io.jpa;

import evl.io.entity.ConsumerCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ConsumerChargeRepo extends JpaRepository<ConsumerCharge, Long> {
    @Query("SELECT a from ConsumerCharge a WHERE a.phone= :phone ORDER BY a.chargeAt DESC")
    List<ConsumerCharge> findAllByPhone(String phone);

    @Query("SELECT a from ConsumerCharge a WHERE a.chargeAt >= :start and a.chargeAt < :end")
    List<ConsumerCharge> findByDateGap(Long start, Long end);

    @Modifying
    @Query("UPDATE ConsumerCharge a set a.phone = :newPhone WHERE a.phone = :oldPhone")
    @Transactional
    void updatePhoneByNewPhone(String oldPhone, String newPhone);
}
