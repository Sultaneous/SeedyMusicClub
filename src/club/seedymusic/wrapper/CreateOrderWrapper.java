package club.seedymusic.wrapper;

import club.seedymusic.ecom.ShoppingCart;
import club.seedymusic.jpa.bean.Account;

public class CreateOrderWrapper {
	private ShoppingCart shoppingCartInfo;
	private Account shippingInfo;
	
	public CreateOrderWrapper() {
		
	}
	
	public ShoppingCart getShoppingCartInfo() {
		return shoppingCartInfo;
	}
	public void setShoppingCartInfo(ShoppingCart shoppingCartInfo) {
		this.shoppingCartInfo = shoppingCartInfo;
	}
	public Account getShippingInfo() {
		return shippingInfo;
	}
	public void setShippingInfo(Account shippingInfo) {
		this.shippingInfo = shippingInfo;
	}
}
