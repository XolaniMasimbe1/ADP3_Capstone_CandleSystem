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
import org.springframework.http.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RetailStoreControllerTest {


	private static final String STORE_URL = "/store";
	private static final String USER_URL = "/user";

	private static RetailStore retailStore;
	private static User user;

	@Autowired
	private TestRestTemplate restTemplate;

	@BeforeAll
	static void setUp() {
		user = UserFactory.createUser("er10", "rd8", UserRole.STORE);

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
		// create user
		restTemplate.postForEntity(USER_URL + "/create", user, User.class);

		// create store
		ResponseEntity<RetailStore> response = restTemplate.postForEntity(STORE_URL + "/create", retailStore, RetailStore.class);

		// FIX 2: Check for HTTP status and response body.
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		System.out.println("Created: " + response.getBody());
	}

	@Test
	@Order(2)
	void b_Read() {
		// read the same store
		String readUrl = STORE_URL + "/read/" + retailStore.getStoreNumber();
		ResponseEntity<RetailStore> response = restTemplate.getForEntity(readUrl, RetailStore.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(retailStore.getStoreNumber(), response.getBody().getStoreNumber());
		System.out.println("Read: " + response.getBody());
	}

	@Test
	@Order(3)
	void c_Update() {
		// update store
		RetailStore updatedStore = new RetailStore.Builder()
				.copy(retailStore)
				.setStoreName("PicknPay Updated")
				.build();

		// FIX 3: Use exchange with HttpMethod.PUT for updates.
		ResponseEntity<RetailStore> response = restTemplate.exchange(
				STORE_URL + "/update",
				HttpMethod.PUT,
				new HttpEntity<>(updatedStore),
				RetailStore.class
		);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("PicknPay Updated", response.getBody().getStoreName());
		System.out.println("Updated: " + response.getBody());
	}

	@Test
	@Order(4)
	void d_GetAll() {
		// get all stores
		ResponseEntity<RetailStore[]> response = restTemplate.getForEntity(STORE_URL + "/all", RetailStore[].class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().length > 0);
		System.out.println("All stores retrieved: " + response.getBody().length);
	}


}