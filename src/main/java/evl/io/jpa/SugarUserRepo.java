package evl.io.jpa;

import evl.io.entity.SugarUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SugarUserRepo extends JpaRepository<SugarUser, Long> {
    @Query("SELECT a FROM SugarUser a WHERE a.username = :name")
    SugarUser findByName(String name);
}
