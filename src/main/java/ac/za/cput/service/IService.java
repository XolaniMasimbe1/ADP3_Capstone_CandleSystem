package ac.za.cput.service;
/*
 * Service.java
 * Service for Invoice
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/
public interface IService <T, ID> {
    T create(T t);
    T read(ID id);
    T update(T t);

}
