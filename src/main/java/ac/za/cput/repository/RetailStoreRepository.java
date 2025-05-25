package ac.za.cput.repository;

import ac.za.cput.domain.RetailStore;
import org.springframework.data.jpa.repository.JpaRepository;
/*
 * RetailFactory.java
 * Factory for Retail
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 25 May 2025
 **/
import java.util.Optional;

public interface RetailStoreRepository extends JpaRepository<RetailStore, String> {
    Optional<RetailStore> findByStoreNumber(String storeNumber);

}
