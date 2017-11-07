package club.seedymusic.wrapper;

import club.seedymusic.jpa.bean.Account;
import club.seedymusic.jpa.bean.Order;

public class ConfirmOrderWrapper {
	private Order purchaseOrder;
	private Account shippingInfo;
	private String paymentInfo;
	
	public ConfirmOrderWrapper() {
		
	}
	
	public Order getPurchaseOrder() {
		return purchaseOrder;
	}
	public void setPurchaseOrder(Order purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
	public Account getShippingInfo() {
		return shippingInfo;
	}
	public void setShippingInfo(Account shippingInfo) {
		this.shippingInfo = shippingInfo;
	}
	public String getPaymentInfo() {
		return paymentInfo;
	}
	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
}
