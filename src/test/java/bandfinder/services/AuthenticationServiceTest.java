package bandfinder.services;

import bandfinder.infrastructure.Constants;
import bandfinder.serviceimplementations.JWTAuthenticationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthenticationServiceTest {
    private AuthenticationService authService;

    @BeforeEach
    public void init() {
        this.authService = new JWTAuthenticationServiceImpl();
    }

    @Test
    public void testSimple() {
        int user1Id = 1;
        String token1 = authService.generateToken(user1Id);
        Assertions.assertEquals(user1Id, authService.authenticate(token1));
        Assertions.assertEquals(Constants.NO_ID, authService.authenticate(""));
    }

    @Test
    public void testWrong() {
        int user1Id = 2;

        int user2Id = 3;
        String user2Token = authService.generateToken(user2Id);
        int user3Id = 22;
        String user3Token = authService.generateToken(user3Id);

        Assertions.assertNotEquals(user1Id, authService.authenticate(user2Token));
        Assertions.assertNotEquals(user1Id, authService.authenticate(user3Token));
    }
}