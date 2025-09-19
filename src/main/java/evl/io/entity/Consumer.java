package evl.io.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "consumer")
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;
    private String name;
    @Column(name = "left_count")
    private Integer leftCount;
    private String type;
    private String expiredAt;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(Integer leftCount) {
        this.leftCount = leftCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(String expiredAt) {
        this.expiredAt = expiredAt;
    }
}
