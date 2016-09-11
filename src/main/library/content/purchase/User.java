package main.library.content.purchase;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="User")
public class User {
	private Address _address;
	private Set<Orders> _orders;
	private BigDecimal _totalCost;
	
	public User(Address address, Set<Orders> orders, BigDecimal totalCost){
		_address=address;
		_orders=orders;
		_totalCost=totalCost;
	}
	
	public User(){
	}
	
	@XmlElement(name="Address")
	public Address get_address() {
		return _address;
	}
	public void set_address(Address _address) {
		this._address = _address;
	}
	
	@XmlIDREF 
	@XmlElement(name="Orders")
	public Set<Orders> get_orders() {
		return _orders;
	}
	public void set_orders(Set<Orders> _orders) {
		this._orders = _orders;
	}
	
	@XmlElement(name="Total_Cost")
	public BigDecimal get_totalCost() {
		return _totalCost;
	}
	public void set_totalCost(BigDecimal _totalCost) {
		this._totalCost = _totalCost;
	}
}
