import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* unit test instructions: 
1. Create new password: Password password = new Password("myPassword123");
2. Check if correct:
if (password.verify("myPassword123")) {
    // Password is correct
} else {
    // Password is incorrect
} */

public class Password {
    private static final String SALT = "yourSaltHere";
    private String passwordHash;

    public Password(String password) {
        passwordHash = generateHash(password);
    }

    public boolean verify(String password) {
        String hash = generateHash(password);
        return hash.equals(passwordHash);
    }

    private String generateHash(String password) {
        String saltedPassword = SALT + password;
        String hash = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(saltedPassword.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            hash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }
}
