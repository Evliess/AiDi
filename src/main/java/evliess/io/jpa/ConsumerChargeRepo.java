package evliess.io.jpa;

import evliess.io.entity.ConsumerCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerChargeRepo extends JpaRepository<ConsumerCharge, Long> {
}
