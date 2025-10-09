package ac.za.cput.domain;

/**
 * Simple User interface for JWT authentication
 * This follows DDD principles by providing a common contract
 * for authentication across different user types
 */
public interface User {
    String getId();
    String getEmail();
    String getPasswordHash();
    String getRole();
    String getName();
}
