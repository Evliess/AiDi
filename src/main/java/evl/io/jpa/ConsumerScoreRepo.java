package evl.io.jpa;

import evl.io.entity.ConsumerScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ConsumerScoreRepo extends JpaRepository<ConsumerScore, Long> {

    @Modifying
    @Query("UPDATE ConsumerScore a set a.score = :score WHERE a.phone = :phone")
    @Transactional
    void updateScoreByPhone(String phone, String score);

    @Query("SELECT a from ConsumerScore a WHERE a.phone = :phone")
    ConsumerScore findByPhone(String phone);
}
