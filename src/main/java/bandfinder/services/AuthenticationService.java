package bandfinder.services;

public interface AuthenticationService {
    /**
        @return the ID of logged-in user or Constants.NO_ID if the token is invalid/empty.
     */
    int authenticate(String token);
    String generateToken(int userId);
}
