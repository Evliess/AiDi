package evl.io.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "consumer_score")
public class ConsumerScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;
    @Column(name = "score")
    private String score;
    @Column(name = "consume_at")
    private Long consumeAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Long getConsumeAt() {
        return consumeAt;
    }

    public void setConsumeAt(Long consumeAt) {
        this.consumeAt = consumeAt;
    }
}
