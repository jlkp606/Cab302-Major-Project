package Server;

import java.util.Random;
import java.time.LocalDateTime;

public class Token {
    private String sessionToken;
    private LocalDateTime expiryTime;

    Token(){
        sessionToken = generateToken();
        expiryTime = generateExpiryDate();
    }
    private String generateToken() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 20;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }
    private LocalDateTime generateExpiryDate(){
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.plusDays(1);
    }
}
