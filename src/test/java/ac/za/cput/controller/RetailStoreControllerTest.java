package ac.za.cput.controller;

import ac.za.cput.domain.Enum.UserRole;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.domain.User;
import ac.za.cput.factory.RetailStoreFactory;
import ac.za.cput.factory.UserFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RetailStoreControllerTest {

	private static final String BASE_URL = "http://localhost:8080/CandleSystem";
	private static final String STORE_URL = BASE_URL + "/store";
	private static final String USER_URL = BASE_URL + "/user";
	private static RetailStore retailStore;
	private static User user;

	@Autowired
	private TestRestTemplate restTemplate;

	@BeforeEach
	void setUp() {

		user = UserFactory.createUser("testUser", "password", UserRole.STORE);

		// Create a RetailStore object for the test
		retailStore = RetailStoreFactory.createRetailStore(
				"PicknPay",
				"info@picknpay.com",
				"0211234567",
				"1234",
				"Test Road",
				"Cape Town",
				"Western Cape",
				"South Africa",
				user
		);
	}

	@Test
	@Order(1)
	void a_Create() {
		// Persist user first
		restTemplate.postForEntity(USER_URL + "/create", user, User.class);

		String url = STORE_URL + "/create";
		ResponseEntity<RetailStore> response = restTemplate.postForEntity(url, retailStore, RetailStore.class);
		assertNotNull(response.getBody());
		System.out.println("Created: " + response.getBody());
	}

	@Test
	@Order(2)
	void b_Read() {
		// Persist user first
		restTemplate.postForEntity(USER_URL + "/create", user, User.class);

		// First, create the object
		String createUrl = STORE_URL + "/create";
		RetailStore createdStore = restTemplate.postForObject(createUrl, retailStore, RetailStore.class);
		assertNotNull(createdStore);

		// Then, read it
		String readUrl = STORE_URL + "/read/" + createdStore.getStoreNumber();
		ResponseEntity<RetailStore> response = restTemplate.getForEntity(readUrl, RetailStore.class);
		assertNotNull(response);
		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals(createdStore.getStoreNumber(), response.getBody().getStoreNumber());
		System.out.println("Read: " + response.getBody());
	}

	@Test
	@Order(3)
	void c_Update() {
		// Persist user first
		restTemplate.postForEntity(USER_URL + "/create", user, User.class);

		// First, create the object
		String createUrl = STORE_URL + "/create";
		RetailStore createdStore = restTemplate.postForObject(createUrl, retailStore, RetailStore.class);
		assertNotNull(createdStore);

		// Then, update it
		RetailStore updatedStore = new RetailStore.Builder().copy(createdStore)
				.setStoreName("PicknPay Updated")
				.build();
		restTemplate.put(STORE_URL + "/update", updatedStore);

		// Verify the update
		RetailStore foundUpdated = restTemplate.getForObject(STORE_URL + "/read/" + updatedStore.getStoreNumber(), RetailStore.class);
		assertNotNull(foundUpdated);
		assertEquals("PicknPay Updated", foundUpdated.getStoreName());
		System.out.println("Updated: " + foundUpdated);
	}

	@Test
	@Order(4)
	void d_GetAll() {
		// Persist user first
		restTemplate.postForEntity(USER_URL + "/create", user, User.class);

		// Create at least one object to ensure the list is not empty
		String createUrl = STORE_URL + "/create";
		restTemplate.postForObject(createUrl, retailStore, RetailStore.class);

		// Retrieve all stores
		String getAllUrl = STORE_URL + "/all";
		ResponseEntity<RetailStore[]> response = restTemplate.getForEntity(getAllUrl, RetailStore[].class);
		assertNotNull(response);
		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().length > 0);
		System.out.println("All stores retrieved: " + response.getBody().length);
	}
}