package bandfinder.services;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface HashingService {
    String hash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException;
    boolean validateHash(String password, String hash) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
