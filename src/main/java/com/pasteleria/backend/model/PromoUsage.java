package com.pasteleria.backend.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "promo_usage", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "code"})
})
public class PromoUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "used_at", nullable = false)
    private Instant usedAt = Instant.now();

    public PromoUsage() {}

    public PromoUsage(User user, String code) {
        this.user = user;
        this.code = code;
        this.usedAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Instant getUsedAt() { return usedAt; }
    public void setUsedAt(Instant usedAt) { this.usedAt = usedAt; }
}
