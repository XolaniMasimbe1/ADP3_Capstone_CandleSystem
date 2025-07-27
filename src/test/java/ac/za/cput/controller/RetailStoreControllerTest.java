package ac.za.cput.controller;
/*
 * RetailStoreControllerTest.java
 * Test for Invoice
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/
import ac.za.cput.domain.RetailStore;
import ac.za.cput.factory.RetailStoreFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class RetailStoreControllerTest {
    private static RetailStore retailStore;

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8080//CandleSystem/retailstore";

    @BeforeAll
    public static void setUp() {
         retailStore = RetailStoreFactory.createRetailStore("Tech haven","John Deo"
                ,"222410817@mycput.ac.za","0612773329","5555","123 Tech Street",
                "Cape Town","Western Cape","South Africa");

    }

    @Test
    void create() {
        String url = BASE_URL + "/create";
        ResponseEntity<RetailStore> postResponse = restTemplate.postForEntity(url, retailStore, RetailStore.class);
        assertNotNull(postResponse);
        RetailStore reatialSaved = postResponse.getBody();
        assertEquals(retailStore.getStoreNumber(), reatialSaved.getStoreNumber());
        System.out.println("Created: " + reatialSaved);
    }

    @Test
    void read() {
        String url = BASE_URL + "/read/" + retailStore.getStoreNumber();
        ResponseEntity<RetailStore> response = restTemplate.getForEntity(url, RetailStore.class);
        assertNotNull(response.getBody());
        assertEquals(retailStore.getStoreNumber(), response.getBody().getStoreNumber());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    void update() {
        RetailStore updatedRetailStore = new RetailStore.Builder().copy(retailStore)
                .setStoreName("Tech Haven Updated")
                .setContactPerson("Jane Doe")
                .build();

        String url = BASE_URL + "/update";
        ResponseEntity<RetailStore> response = restTemplate.postForEntity(url, updatedRetailStore, RetailStore.class);
        assertNotNull(response.getBody());
        assertEquals(updatedRetailStore.getStoreName(), response.getBody().getStoreName());
        System.out.println("Updated: " + response.getBody());
    }



    @Test
    void getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<RetailStore[]> response = restTemplate.getForEntity(url, RetailStore[].class);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Retail Stores: ");
        for (RetailStore store : response.getBody()) {
            System.out.println(store);
        }
    }
}