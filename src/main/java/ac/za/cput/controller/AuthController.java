package ac.za.cput.controller;

import ac.za.cput.domain.*;
import ac.za.cput.domain.Enum.UserRole;
import ac.za.cput.factory.UserFactory;
import ac.za.cput.factory.AdminFactory;
import ac.za.cput.service.RetailStoreService;
import ac.za.cput.service.UserService;
import ac.za.cput.service.AdminService;
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
	private final AdminService adminService;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private final RetailStoreService retailStoreService;
	
	@Autowired
	public AuthController(UserService userService, AdminService adminService, RetailStoreService retailStoreService) {
		this.userService = userService;
		this.adminService = adminService;
		this.retailStoreService = retailStoreService;
	}

	@Transactional
	@PostMapping("/register/store")
	public ResponseEntity<?> registerStore(@RequestBody Map<String, String> payload) {
		try {
			// Extract all fields from payload
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

			// Check if username exists
			if (userService.findByUsername(username).isPresent()) {
				return ResponseEntity.badRequest().body("Username already exists");
			}

			// 1. Create and save User first
			User user = UserFactory.createUser(username, password, UserRole.STORE);
			User savedUser = userService.create(user);

			// 2. Create ContactDetails
			ContactDetails contactDetails = new ContactDetails(
					email, phoneNumber, postalCode, city, country, province, street);

			// 3. Create RetailStore
			String storeNumber = "STORE-" + UUID.randomUUID().toString().substring(0, 8);
			RetailStore retailStore = new RetailStore.Builder()
					.setStoreNumber(storeNumber)
					.setStoreName(storeName)
					.setContactDetails(contactDetails)
					.setUser(savedUser)
					.build();

			// 4. Save RetailStore (declare variable properly)
			RetailStore savedStore = retailStoreService.create(retailStore);

			// 5. Update User with RetailStore reference
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
	public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
		String username = payload.get("username");
		String password = payload.get("password");

		// First check if it's an admin
		Optional<Admin> optionalAdmin = adminService.findByUsername(username);
		if (optionalAdmin.isPresent()) {
			Admin admin = optionalAdmin.get();
			if (passwordEncoder.matches(password, admin.getPasswordHash())) {
				return ResponseEntity.ok(Map.of(
					"message", "Login successful",
					"adminId", admin.getAdminId(),
					"username", admin.getUsername(),
					"type", "ADMIN",
					"redirectTo", "/admin/dashboard"
				));
			}
		}

		// Then check if it's a user
		Optional<User> optionalUser = userService.findByUsername(username);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (passwordEncoder.matches(password, user.getPasswordHash())) {
				return ResponseEntity.ok(Map.of(
					"message", "Login successful",
					"userId", user.getUserId(),
					"username", user.getUsername(),
					"role", user.getRole().toString(),
					"redirectTo", getRedirectPath(user.getRole())
				));
			}
		}
		return ResponseEntity.badRequest().body("Invalid username or password");
	}

	@Transactional
	@PostMapping("/register/admin")
	public ResponseEntity<?> registerAdmin(@RequestBody Map<String, String> payload) {
		try {
			// Extract fields from payload
			String username = payload.get("username");
			String password = payload.get("password");
			String email = payload.get("email");
			String phoneNumber = payload.get("phoneNumber");

			// Check if username exists in both user and admin tables
			if (userService.findByUsername(username).isPresent() || 
				adminService.findByUsername(username).isPresent()) {
				return ResponseEntity.badRequest().body("Username already exists");
			}

			// Check if email exists in admin table
			if (adminService.findByEmail(email).isPresent()) {
				return ResponseEntity.badRequest().body("Email already exists");
			}

			// Create admin using AdminFactory
			Admin admin = AdminFactory.createAdmin(username, password, email, phoneNumber);
			Admin savedAdmin = adminService.create(admin);

			return ResponseEntity.ok(Map.of(
					"message", "Admin registration successful",
					"adminId", savedAdmin.getAdminId(),
					"username", savedAdmin.getUsername(),
					"type", "ADMIN"
			));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Admin registration failed: " + e.getMessage());
		}
	}

	private String getRedirectPath(UserRole role) {
		switch (role) {
			case ADMIN:
				return "/admin/dashboard";
			case STORE:
				return "/store/dashboard";
			case DRIVER:
				return "/driver/dashboard";
			default:
				return "/dashboard";
		}
	}
}