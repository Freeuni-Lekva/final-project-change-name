package bandfinder.services;

public interface AuthenticationService {
    /**
        @return the ID of logged-in user or Constants.NO_ID if the token is empty.
     */
    public int authenticate(String token);
    public String generateToken(int userId);
}
