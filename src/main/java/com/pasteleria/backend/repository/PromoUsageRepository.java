package com.pasteleria.backend.repository;

import com.pasteleria.backend.model.PromoUsage;
import com.pasteleria.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromoUsageRepository extends JpaRepository<PromoUsage, Long> {
    boolean existsByUserAndCodeIgnoreCase(User user, String code);
}
