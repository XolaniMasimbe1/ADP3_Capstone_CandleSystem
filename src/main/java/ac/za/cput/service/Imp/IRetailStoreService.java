package ac.za.cput.service.Imp;
/*
 * RetailStoreService.java
 * Service for Invoice
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/
import ac.za.cput.domain.RetailStore;
import ac.za.cput.service.IService;

import java.util.List;
import java.util.Optional;

public interface IRetailStoreService extends IService<RetailStore, String> {
    List<RetailStore> getAll();
    Optional<RetailStore> findByStoreNumber(String storeNumber);
}
