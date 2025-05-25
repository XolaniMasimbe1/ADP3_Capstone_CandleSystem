package ac.za.cput.repository;

import ac.za.cput.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/*
 * InvoiceRepository.java
 * Repository for Invoice
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 25 June 2025
 **/
import java.util.Optional;
@Repository
public interface InoviceRepository extends JpaRepository<Invoice, String> {
    Optional<Invoice> findById(String invoiceNumber);

}
