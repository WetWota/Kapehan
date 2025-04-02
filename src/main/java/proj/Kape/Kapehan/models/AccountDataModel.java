package proj.Kape.Kapehan.models;

public class AccountDataModel {
	private int accountId;
    private String username;
    private String passwordHash;
    private String role;

    public AccountDataModel(int accountId, String username, String passwordHash, String role) {
        this.accountId = accountId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public int getAccountId() { return accountId; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getRole() { return role; }
}
