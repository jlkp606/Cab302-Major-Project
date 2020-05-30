package Server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Hash {

    public static String getHash(String password) throws NoSuchAlgorithmException {
        String algorithm = "SHA-256";
        return generateHash(password, algorithm);
    }

    private static String generateHash(String password, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.reset();
        byte[] hash = digest.digest(password.getBytes());
        return bytesToStringHex(hash);
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private static String bytesToStringHex(byte[] bytes){
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 +1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String hi = getHash("hi");
                System.out.println(hi);
    }
}
