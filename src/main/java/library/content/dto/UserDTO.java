package library.content.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import library.content.purchase.Address;

@XmlRootElement(name="User")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDTO {
	
	private Long userId;
	
	private String userName;
	
	private Address userAddress;
	
	private BigDecimal totalUserCost;
	
	public UserDTO(Address address, String name){
		userAddress=address;
		totalUserCost = new BigDecimal("0");
		userName = name;
	}
	
	public UserDTO(){
	}
	
	
	public Address get_address() {
		return userAddress;
	}
	public void set_address(Address _address) {
		this.userAddress = _address;
	}
	public BigDecimal get_totalCost() {
		return totalUserCost;
	}
	public void set_totalCost(BigDecimal _totalCost) {
		this.totalUserCost = _totalCost;
	}
	public Long get_userId() {
		return userId;
	}

	public void set_userId(Long _userId) {
		this.userId = _userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
