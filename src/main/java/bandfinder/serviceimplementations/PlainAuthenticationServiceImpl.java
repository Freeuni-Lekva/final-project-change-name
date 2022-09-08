package bandfinder.serviceimplementations;

import bandfinder.infrastructure.Constants;
import bandfinder.services.AuthenticationService;

public class PlainAuthenticationServiceImpl implements AuthenticationService {
    @Override
    public int authenticate(String token) {
        if(token == null || token.isBlank()) {
            return Constants.NO_ID;
        }
        return Integer.parseInt(token);
    }

    @Override
    public String generateToken(int userId) {
        return String.valueOf(userId);
    }
}
