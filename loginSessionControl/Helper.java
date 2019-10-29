package loginSessionControl;

public class Helper {
    final static int FRAME_WIDTH = 700;
    final static int FRAME_HEIGHT = 200;
    final static String[] LABELS = { "Username: ", "Password: ", "Confirm: " };
    final static int INPUT_LENGTH = 20;

    public static String getUsername(JFrame owner) {
        return tfUsername.getText().trim();
    }

    public static String getPassword(JFrame owner) {
        return new String(owner.pfPassword.getPassword());
    }
    public static String getConfirm(JFrame owner) {
        return new String(owner.pfConfirm.getPassword());
    }

    public static boolean isTaken(String username) {
        // TODO
        return true;
    }
    public static boolean authenticate(String username, String pw) {
        // TODO
        return true;
    }
    public static boolean pwMatch(JFrame owner){
        return owner.getPassword().equals(owner.getConfirm());
    }
}