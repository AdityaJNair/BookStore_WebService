package main.library.content.printcontent;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name="User")
public class User {
	
	@Id
	@GeneratedValue(generator="ID-GENERATOR")
	private Long _userId;
	
	@Embedded
	@Column(nullable=false, name="Address")
	private Address _address;
	
	@OneToMany(mappedBy="_users", fetch=FetchType.LAZY)
	private Set<Orders> _orders;
	
	@Column(nullable=false, name="Total cost")
	private BigDecimal _totalCost;
	
	public User(Address address){
		_address=address;
		_orders=new HashSet<Orders>();
		calculateCost();
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
	
	public void addOrder(Orders x){
		_orders.add(x);
		calculateCost();
	}
	
	public void removeOrder(Orders x){
		_orders.remove(x);
		calculateCost();
	}
	
	public Long get_userId() {
		return _userId;
	}

	public void set_userId(Long _userId) {
		this._userId = _userId;
	}
	
	private void calculateCost(){
		if(this._orders == null || this._orders.isEmpty()){
			_totalCost = new BigDecimal("0");
		} else {
			BigDecimal tmp = new BigDecimal("0");
			for(Orders s: _orders){
				tmp.add(s.getTotalCost());
			}
			_totalCost = tmp;
		}
		
	}
	
	/*
	//Do override toString, Equals and hashCode
	@Override
	public String toString() {
	}
	
	@Override
	public boolean equals(Object obj) {
	}
	
	@Override
	public int hashCode() {
	}
	*/
}
