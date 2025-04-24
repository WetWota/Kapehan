 package proj.Kape.Kapehan.utils;

public class SessionManager {
    private static int accountId;
    private static String username;
    private static String role;

    public static void setUser(int id, String user, String userRole) {
        accountId = id;
        username = user;
        role = userRole;
    }

    public static int getAccountId() {return accountId;}
    public static void setUsername(String user) {
        username = user;
    }
    public static String getUsername() {return username;}
    public static String getRole() {return role;}
    
    public static void clearSession() {
        accountId = 0;
        username = null;
        role = null;
    }
}
