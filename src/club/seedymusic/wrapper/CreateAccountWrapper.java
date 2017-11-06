package club.seedymusic.wrapper;

import club.seedymusic.jpa.bean.Account;

public class CreateAccountWrapper {
	private String accountName;
	private Account accountInfo;
	
	public CreateAccountWrapper() {
		
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Account getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(Account accountInfo) {
		this.accountInfo = accountInfo;
	}
}
