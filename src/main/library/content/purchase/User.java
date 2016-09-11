package main.library.content.purchase;

import java.math.BigDecimal;
import java.util.List;

public class User {
	private Address _address;
	private List<Orders> _orders;
	private BigDecimal _totalCost;
	
	public User(Address address, List<Orders> orders, BigDecimal totalCost){
		_address=address;
		_orders=orders;
		_totalCost=totalCost;
	}
	
	public User(){
	}
	
	public Address get_address() {
		return _address;
	}
	public void set_address(Address _address) {
		this._address = _address;
	}
	public List<Orders> get_orders() {
		return _orders;
	}
	public void set_orders(List<Orders> _orders) {
		this._orders = _orders;
	}
	public BigDecimal get_totalCost() {
		return _totalCost;
	}
	public void set_totalCost(BigDecimal _totalCost) {
		this._totalCost = _totalCost;
	}
}
