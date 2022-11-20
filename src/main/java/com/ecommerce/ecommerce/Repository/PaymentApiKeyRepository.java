package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Models.PaymentApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentApiKeyRepository extends JpaRepository<PaymentApiKey,Long> {
    Optional<PaymentApiKey> findFirstByProduction(boolean production);
}
