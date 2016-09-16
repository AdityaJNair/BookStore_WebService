package library.content.purchase;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.HashCodeBuilder;


@Entity
@XmlRootElement(name="User")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
	
	@Id
	@GeneratedValue(generator="ID-GENERATOR")
	private Long userId;
	
	@Column(name="USER_NAME", nullable=false)
	private String userName;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_OF_BIRTH", nullable=false)
	private Date userAge;
	
	@Embedded
	 @AttributeOverrides({
		 	@AttributeOverride(name="houseNumber", column=@Column(name="USER_HOUSENUMBER", nullable=false)),
		 	@AttributeOverride(name="street", column=@Column(name="USER_STREET", nullable=false)),
		 	@AttributeOverride(name="suburb", column=@Column(name="USER_SUBURB", nullable=false)),
		 	@AttributeOverride(name="city", column=@Column(name="USER_CITY", nullable=false)),
		 	@AttributeOverride(name="country", column=@Column(name="USER_COUNTRY", nullable=false)),
		 	@AttributeOverride(name="zip", column=@Column(name="USER_ZIP", nullable=false)),
	    
	 })
	private Address userAddress;
	
	@XmlElementWrapper(name="user-orders")
	@XmlElement(name="orders")
	@OneToMany(mappedBy="usersOrder", fetch=FetchType.LAZY)
	private Set<Orders> userOrders;
	
	@Column(nullable=false, name="Total_cost")
	private BigDecimal totalUserCost;
	
	public User(Address address, String name, Date age){
		userAddress=address;
		userOrders=new HashSet<Orders>();
		userName = name;
		userAge=age;
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
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long get_userId() {
		return userId;
	}

	public void set_userId(Long _userId) {
		this.userId = _userId;
	}
	

	public void setUserAge(Date userAge) {
		this.userAge = userAge;
	}

	public Date getUserAge() {
		return userAge;
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
	

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(userId.toString() +" ");
		buffer.append(userName+" ");
		buffer.append(userAge+" ");
		buffer.append(userAddress.toString()+" ");
		for(Orders o: userOrders){
			buffer.append(o.get_id()+" ");
		}
		buffer.append(totalUserCost.toString());
		return buffer.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User))
            return false;
        if (obj == this)
            return true;
        
        User usr = (User) obj;
        return userName.equals(usr.userName) && userAddress.equals(usr.userAddress) && userAge.equals(usr.userAge);
	}
	
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(userName).
	            append(userAddress).
	            append(userAge).
	            toHashCode();
	}


}
