package Server;

import java.io.Serializable;
import java.util.Random;
import java.time.LocalDateTime;

public class Token implements Serializable {
    private String sessionToken;
    private LocalDateTime expiryTime;

    public Token(){
        sessionToken = generateRandomString();
        expiryTime = generateExpiryDate();
    }

    public static String generateRandomString() {
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

    public String getToken(){
        return sessionToken;
    }

    public LocalDateTime getExpiry(){
        return expiryTime;
    }

}
