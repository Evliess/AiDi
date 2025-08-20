package evliess.io.jpa;

import evliess.io.entity.ConsumerPlay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerPlayRepo extends JpaRepository<ConsumerPlay, Long> {
}
