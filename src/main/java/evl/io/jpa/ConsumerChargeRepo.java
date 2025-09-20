package evl.io.jpa;

import evl.io.entity.ConsumerCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumerChargeRepo extends JpaRepository<ConsumerCharge, Long> {
    @Query("SELECT a from ConsumerCharge a WHERE a.phone= :phone ORDER BY a.id DESC")
    List<ConsumerCharge> findAllByPhone(String phone);
}
