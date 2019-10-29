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
     * Check if the given username is already in the database
     * @param username  username to check from the database
     * @return return true if the name exists in the database, otherwise false.
     */
    public static boolean isTaken(String username) {
        // TODO
        return true;
    }
    /**
     * Check if the pw matches the username
     * @param username  id to check from the database
     * @param pw        password to compare
     * @return return true if the password matches the username, otherwise false.
     */
    public static boolean authenticate(String username, byte[] pw) {
        // TODO
        return true;
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
    public static byte[] passwordHashing(String pw){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        return factory.generateSecret(spec).getEncoded();
    }
}