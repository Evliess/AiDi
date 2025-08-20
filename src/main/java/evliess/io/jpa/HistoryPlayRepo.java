package evliess.io.jpa;

import evliess.io.entity.HistoryPlay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryPlayRepo extends JpaRepository<HistoryPlay, Long> {
}
