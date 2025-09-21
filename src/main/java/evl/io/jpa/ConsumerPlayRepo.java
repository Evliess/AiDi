package evl.io.jpa;

import evl.io.entity.ConsumerPlay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumerPlayRepo extends JpaRepository<ConsumerPlay, Long> {

    @Query("SELECT a from ConsumerPlay a WHERE a.phone= :phone ORDER BY a.consumeAt DESC")
    List<ConsumerPlay> findAllByPhone(String phone);
}
