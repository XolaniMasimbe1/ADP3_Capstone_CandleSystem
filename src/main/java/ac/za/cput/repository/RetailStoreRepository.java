package ac.za.cput.repository;

import ac.za.cput.domain.RetailStore;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RetailStoreRepository extends JpaRepository<RetailStore, String> {
    Optional<RetailStore> findByStoreEmail(String storeEmail);

    @Modifying
    @Transactional
    @Query("update RetailStore u set u.passwordHash = ?2 where u.storeEmail = ?1")
    void updatePassword(String email,String password);
}