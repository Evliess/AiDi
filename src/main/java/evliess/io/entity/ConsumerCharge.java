package evliess.io.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "consumer_charge")
public class ConsumerCharge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;
    private String money;
    @Column(name = "charge_at")
    private Long chargeAt;

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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Long getChargeAt() {
        return chargeAt;
    }

    public void setChargeAt(Long chargeAt) {
        this.chargeAt = chargeAt;
    }
}
