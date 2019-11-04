public class User {
    private String username;
    private byte[] hashedPW;
    private String[] accessibleTimes;

    User(String name, byte[] pw, String[] access) {
        this.username = name;
        this.hashedPW = pw.clone();
        this.accessibleTimes = access.clone();
    }
    User(String name, byte[] pw) {
        this.username = name;
        this.hashedPW = pw.clone();
        this.accessibleTimes = null;
    }
    public String getName() {
        return this.username;
    }
    public byte[] getHashedPW() {
        return this.hashedPW;
    }
    public String[] getAccessibleTime() {
        return this.accessibleTimes;
    }
    public void setAccessibleTime(String[] time) {
        this.accessibleTimes = time.clone();
    }
}