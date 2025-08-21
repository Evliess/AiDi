package evliess.io.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "consumer_play")
public class ConsumerPlay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;
    @Column(name = "item_name")
    private String itemName;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getConsumeAt() {
        return consumeAt;
    }

    public void setConsumeAt(Long consumeAt) {
        this.consumeAt = consumeAt;
    }
}
