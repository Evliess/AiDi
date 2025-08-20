package evliess.io.jpa;

import evliess.io.entity.PlayItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PlayItemRepo extends JpaRepository<PlayItem, Long> {
    @Modifying
    @Query("UPDATE PlayItem a set a.money = :money WHERE a.name = :name")
    @Transactional
    Integer updateByName(String name, String money);

    @Query("SELECT a FROM PlayItem a WHERE a.name = :name")
    PlayItem findByName(String name);

    @Modifying
    @Query("DELETE FROM PlayItem a WHERE a.name = :name")
    void deleteByName(String name);
}
