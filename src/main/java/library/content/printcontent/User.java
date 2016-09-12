package library.content.printcontent;

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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name="User")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
	
	@XmlID
	@XmlAttribute(name="xml-id-user")
	private String _xml_id;
	
	@Id
	@GeneratedValue(generator="ID-GENERATOR")
	private Long userId;
	
	@Embedded
	@Column(nullable=false, name="Address")
	private Address userAddress;
	
	@XmlElementWrapper(name="user-orders")
	@XmlElement(name="orders")
	@OneToMany(mappedBy="_users", fetch=FetchType.LAZY)
	private Set<Orders> userOrders;
	
	@Column(nullable=false, name="Total_cost")
	private BigDecimal totalUserCost;
	
	public User(Address address){
		userAddress=address;
		userOrders=new HashSet<Orders>();
		calculateCost();
	}
	
	public User(){
	}
	
	
	public Address get_address() {
		return userAddress;
	}
	public void set_address(Address _address) {
		this.userAddress = _address;
	}
	
	public Set<Orders> get_orders() {
		return userOrders;
	}
	public void set_orders(Set<Orders> _orders) {
		this.userOrders = _orders;
	}
	
	public BigDecimal get_totalCost() {
		return totalUserCost;
	}
	public void set_totalCost(BigDecimal _totalCost) {
		this.totalUserCost = _totalCost;
	}
	
	public void addOrder(Orders x){
		userOrders.add(x);
		calculateCost();
	}
	
	public void removeOrder(Orders x){
		userOrders.remove(x);
		calculateCost();
	}
	
	public Long get_userId() {
		return userId;
	}

	public void set_userId(Long _userId) {
		this.userId = _userId;
		this._xml_id = getClass().getName()+_userId;
	}
	
	private void calculateCost(){
		if(this.userOrders == null || this.userOrders.isEmpty()){
			totalUserCost = new BigDecimal("0");
		} else {
			BigDecimal tmp = new BigDecimal("0");
			for(Orders s: userOrders){
				tmp.add(s.getTotalCost());
			}
			totalUserCost = tmp;
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
