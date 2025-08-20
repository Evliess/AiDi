package evliess.io.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "history_play")
public class HistoryPlay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String money;
    private String item_name;
    @Column(name = "consume_at")
    private Long consumeAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public Long getConsumeAt() {
        return consumeAt;
    }

    public void setConsumeAt(Long consumeAt) {
        this.consumeAt = consumeAt;
    }
}
