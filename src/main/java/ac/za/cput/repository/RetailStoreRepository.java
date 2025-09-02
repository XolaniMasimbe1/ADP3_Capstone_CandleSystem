package ac.za.cput.repository;

import ac.za.cput.domain.RetailStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RetailStoreRepository extends JpaRepository<RetailStore, String> {
    Optional<RetailStore> findByStoreNumber(String storeNumber);
    Optional<RetailStore> findByUser_UserId(String userId);
    Optional<RetailStore> findByUser_Username(String username);
}