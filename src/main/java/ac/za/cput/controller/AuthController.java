package ac.za.cput.controller;

import ac.za.cput.domain.*;
import ac.za.cput.domain.Enum.UserRole;
import ac.za.cput.factory.UserFactory;
import ac.za.cput.service.RetailStoreService;
import ac.za.cput.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {
	private final UserService userService;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private final RetailStoreService retailStoreService;
	@Autowired
	public AuthController(UserService userService, RetailStoreService retailStoreService) {
		this.userService = userService;
		this.retailStoreService = retailStoreService;
	}

	@Transactional
	@PostMapping("/register/store")
	public ResponseEntity<?> registerStore(@RequestBody Map<String, String> payload) {
		try {
			String username = payload.get("username");
			String password = payload.get("password");
			String storeName = payload.get("storeName");
			String email = payload.get("email");
			String phoneNumber = payload.get("phoneNumber");
			String postalCode = payload.get("postalCode");
			String street = payload.get("street");
			String city = payload.get("city");
			String province = payload.get("province");
			String country = payload.get("country");

			if (userService.findByUsername(username).isPresent()) {
				return ResponseEntity.badRequest().body("Username already exists");
			}


			User user = UserFactory.createUser(username, password, UserRole.STORE);
			User savedUser = userService.create(user);


			ContactDetails contactDetails = new ContactDetails(
					email, phoneNumber, postalCode, city, country, province, street);


			String storeNumber = "STORE-" + UUID.randomUUID().toString().substring(0, 8);
			RetailStore retailStore = new RetailStore.Builder()
					.setStoreNumber(storeNumber)
					.setStoreName(storeName)
					.setContactDetails(contactDetails)
					.setUser(savedUser)
					.build();


			RetailStore savedStore = retailStoreService.create(retailStore);

			savedUser.setRetailStore(savedStore);
			userService.update(savedUser);

			return ResponseEntity.ok(Map.of(
					"message", "Registration successful",
					"userId", savedUser.getUserId(),
					"storeNumber", savedStore.getStoreNumber()
			));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Registration failed: " + e.getMessage());
		}
	}

	@PostMapping("/login")
	public String login(@RequestBody Map<String, String> payload) {
		String username = payload.get("username");
		String password = payload.get("password");

		Optional<User> optionalUser = userService.findByUsername(username);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (passwordEncoder.matches(password, user.getPasswordHash())) {
				return "Login successful for user: " + user.getUsername();
			}
		}
		return "Invalid username or password";
	}
}