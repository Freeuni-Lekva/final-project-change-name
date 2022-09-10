package bandfinder.services;

import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Config;
import bandfinder.infrastructure.ServiceValueSetter;
import bandfinder.services.HashingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class HashingServiceTests {
    @AutoInjectable
    private HashingService hashingService;

    @BeforeEach
    public void setUp() {
        Config.ConfigureServices();
        ServiceValueSetter.setAutoInjectableFieldValues(this);
        Assertions.assertNotNull(hashingService);
    }

    @Test
    public void testHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String wrongPassword = "WrongPassword";

        String password1 = "";
        String hash1 = hashingService.hash(password1);
        Assertions.assertTrue(hashingService.validateHash(password1, hash1));
        Assertions.assertFalse(hashingService.validateHash(wrongPassword, hash1));

        String password2 = "password";
        String hash2 = hashingService.hash(password2);
        Assertions.assertTrue(hashingService.validateHash(password2, hash2));
        Assertions.assertFalse(hashingService.validateHash(wrongPassword, hash2));

        String password3 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer " +
                "took a galley of type and scrambled it to make a type specimen book. It has survived not only five " +
                "centuries, but also the leap into electronic typesetting, remaining essentially unchanged. " +
                "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, " +
                "and more recently with desktop publishing software like Aldus PageMaker including " +
                "versions of Lorem Ipsum.";
        String hash3 = hashingService.hash(password3);
        Assertions.assertTrue(hashingService.validateHash(password3, hash3));
        Assertions.assertFalse(hashingService.validateHash(wrongPassword, hash3));
    }
}
