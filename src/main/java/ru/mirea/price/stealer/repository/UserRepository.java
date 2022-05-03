package ru.mirea.price.stealer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.price.stealer.model.PriceStealerUser;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<PriceStealerUser, UUID> {
	PriceStealerUser findByEmail(String email);
}
