import javax.swing.*;

public class Helper {
    // Variables
    final static int FRAME_WIDTH = 700;
    final static int FRAME_HEIGHT = 200;
    final static String[] LABELS = { "Username: ", "Password: ", "Confirm: " };
    final static int INPUT_LENGTH = 20;

    /**
     * Get the text in JTextField
     * @param tf JTextField to get the text from
     * @return return text as String
     */
    public static String getText(JtextField tf) {
        return tf.getText().trim();
    }
    /**
     * Get the password
     * @param pf JPasswordField to get password from
     * @return return password as String
     */
    public static String getPassword(JPasswordField pf) {
        return new String(pf.getPassword());
    }
    /**
     * Check if the password and confirm match
     * @param pw1 JPasswordField of the password
     * @param pw2 JPasswordField of the confirm password
     * @return return true if they match, otherwise false.
     */
    public static boolean pwMatch(JPasswordField pw1, JPasswordField pw2){
        return pw1.getPassword().equals(pw2.getPassword());
    }

    /**
     * This hashing function uses PBKDF2 method.
     * @param pw  String to hash
     * @return hashed password as an array of bytes
     * @see https://www.baeldung.com/java-password-hashing
     */
    public static byte[] hashPassword(String pw){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        return factory.generateSecret(spec).getEncoded();
    }
}