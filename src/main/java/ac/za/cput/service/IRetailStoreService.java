package ac.za.cput.service;
/*
 * RetailStoreService.java
 * Service for Invoice
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/
import ac.za.cput.domain.Delivery;
import ac.za.cput.domain.RetailStore;

import java.util.List;

public interface IRetailStoreService extends  IService<RetailStore, String> {
    List<RetailStore> getAll();
    // Additional methods specific to RetailStore can be defined here
}
