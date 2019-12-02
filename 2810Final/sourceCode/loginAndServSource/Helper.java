//package finalJar;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;

public class Helper {
    // Variables
    final static int FRAME_WIDTH = 700;
    final static int FRAME_HEIGHT = 200;
    final static String[] LABELS = { "Username: ", "Password: ", "Confirm: " };
    final static int INPUT_LENGTH = 20;

    /**
     * Get the text in JTextField
     * 
     * @param tf JTextField to get the text from
     * @return return text as String
     */
    public static String getText(JTextField tf) {
        return tf.getText().trim();
    }

    /**
     * Get the password
     * 
     * @param pf JPasswordField to get password from
     * @return return password as String
     */
    public static String getPassword(JPasswordField pf) {
        return new String(pf.getPassword());
    }

    /**
     * Check if the password and confirm match
     * 
     * @param pw1 JPasswordField of the password
     * @param pw2 JPasswordField of the confirm password
     * @return return true if they match, otherwise false.
     */
    public static boolean pwMatch(JPasswordField pw1, JPasswordField pw2) {
        return pw1.getPassword().equals(pw2.getPassword());
    }

    /**
     * This hashing function uses PBKDF2 method.
     * 
     * @param pw String to hash
     * @return hashed password as an array of bytes
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @see https://www.baeldung.com/java-password-hashing
     */
    public static byte[] hashPBKDF2(String pw) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(pw.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        return factory.generateSecret(spec).getEncoded();
    }

	public static String getUsername(JTextField tf) {
		return tf.getText().trim();
	}
}