package club.seedymusic.wrapper;

import club.seedymusic.jpa.bean.Account;

public class GetAccountWrapper {
	private String accountName;
	private String accountPassword;
	private Account accountInfo;
	
	public GetAccountWrapper() {
		
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}

	public Account getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(Account accountInfo) {
		this.accountInfo = accountInfo;
	}
}
