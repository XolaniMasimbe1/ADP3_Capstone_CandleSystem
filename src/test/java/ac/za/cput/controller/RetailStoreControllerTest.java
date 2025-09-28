package ac.za.cput.controller;

import ac.za.cput.domain.RetailStore;
import ac.za.cput.domain.Enum.Province;
import ac.za.cput.factory.RetailStoreFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RetailStoreControllerTest {

	@LocalServerPort
	private int port;
	
	private static RetailStore retailStore;

	@Autowired
	private TestRestTemplate restTemplate;
	
	private String baseURL() {
		return "http://localhost:" + port + "/CandleSystem/store";
	}

	@BeforeEach
	void setUp() {
		// Generate unique data for each test to avoid constraint violations
		String timestamp = String.valueOf(System.currentTimeMillis());
		retailStore = RetailStoreFactory.createRetailStore(
				"PicknPay_" + timestamp,
				"info_" + timestamp + "@picknpay.com",
				"password123",
				"123",
				"Main Street",
				"CBD",
				"Cape Town",
				Province.WESTERN_CAPE,
				"8001",
				"South Africa",
				"John",
				"Doe",
				"john@picknpay.com",
				"+27123456789",
				"Jane",
				"Smith",
				"jane@picknpay.com",
				"+27987654321"
		);
	}



	private static RetailStore createdStore; // Store the created record for other tests

	@Test
	@Order(1)
	void a_Create() {
		// First, verify the retailStore is not null
		assertNotNull(retailStore, "RetailStore should not be null before sending to API");
		System.out.println("RetailStore to create: " + retailStore);
		
		String url = baseURL() + "/create";
		ResponseEntity<RetailStore> response = restTemplate.postForEntity(url, retailStore, RetailStore.class);
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getStoreId(), "Store ID should not be null after creation");
		assertNotNull(response.getBody().getStoreNumber(), "Store Number should not be null after creation");
		
		// Store the created record for other tests
		createdStore = response.getBody();
		System.out.println("Created: " + createdStore);
	}

	@Test
	@Order(2)
	void b_Read() {
		// Read the SAME record that was created in the previous test
		assertNotNull(createdStore, "Created store should not be null - run create test first");
		
		String readUrl = baseURL() + "/read/id/" + createdStore.getStoreId();
		ResponseEntity<RetailStore> response = restTemplate.getForEntity(readUrl, RetailStore.class);
		assertNotNull(response);
		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals(createdStore.getStoreId(), response.getBody().getStoreId());
		assertEquals(createdStore.getStoreNumber(), response.getBody().getStoreNumber());
		System.out.println("Read: " + response.getBody());
	}

	@Test
	@Order(3)
	void c_Update() {
		// Update the SAME record that was created in the first test
		assertNotNull(createdStore, "Created store should not be null - run create test first");

		// Create updated version of the same record
		RetailStore updatedStore = new RetailStore.Builder().copy(createdStore)
				.setStoreName("PicknPay Updated")
				.build();
		restTemplate.put(baseURL() + "/update", updatedStore);

		// Update our reference to the updated record
		createdStore = updatedStore;

		// Verify the update by reading with storeId
		RetailStore foundUpdated = restTemplate.getForObject(baseURL() + "/read/id/" + updatedStore.getStoreId(), RetailStore.class);
		assertNotNull(foundUpdated);
		assertEquals("PicknPay Updated", foundUpdated.getStoreName());
		assertEquals(createdStore.getStoreId(), foundUpdated.getStoreId());
		System.out.println("Updated: " + foundUpdated);
	}

	@Test
	@Order(4)
	void d_GetAll() {
		// Get all stores - should include the one we created and updated
		assertNotNull(createdStore, "Created store should not be null - run create test first");

		String getAllUrl = baseURL() + "/all";
		ResponseEntity<RetailStore[]> response = restTemplate.getForEntity(getAllUrl, RetailStore[].class);
		assertNotNull(response);
		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().length > 0);
		
		// Verify our created store is in the list
		boolean foundOurStore = false;
		for (RetailStore store : response.getBody()) {
			if (store.getStoreId().equals(createdStore.getStoreId())) {
				foundOurStore = true;
				break;
			}
		}
		assertTrue(foundOurStore, "Our created store should be in the getAll results");
		
		System.out.println("All stores retrieved: " + response.getBody().length);
	}
}