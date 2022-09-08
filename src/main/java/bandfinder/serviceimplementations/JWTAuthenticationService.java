package bandfinder.serviceimplementations;

import bandfinder.infrastructure.Constants;
import bandfinder.services.AuthenticationService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTAuthenticationService implements AuthenticationService {
    @Override
    public int authenticate(String token) {
        JWTVerifier verifier = JWT.require(Constants.ENCRYPT_ALGO)
                .withIssuer("auth0")
                .build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME).asInt();
        } catch (JWTDecodeException decodeException) {
            return Constants.NO_ID;
        }
    }

    @Override
    public String generateToken(int userId) {
        return JWT.create()
                .withIssuer("auth0")
                .withClaim(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME, userId)
                .sign(Constants.ENCRYPT_ALGO);
    }
}
