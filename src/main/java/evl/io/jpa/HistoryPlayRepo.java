package evl.io.jpa;

import evl.io.entity.HistoryPlay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryPlayRepo extends JpaRepository<HistoryPlay, Long> {
    @Query("SELECT a from HistoryPlay a WHERE a.consumeAt >= :consumeAt")
    List<HistoryPlay> findAllByDate(Long consumeAt);
}
