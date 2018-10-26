package goldfish.express.service.utils;


import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class StringUtils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789";

    public long generate() {
        return generateRandomString(6);
    }

    public long generate(int length) {
        return generateRandomString(length);
    }

    private long generateRandomString(int length) {
        StringBuilder builder = new StringBuilder(length);

        for(int i = 0; i < length; i++) {
            builder.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return Long.valueOf(new String(builder));
    }

}
