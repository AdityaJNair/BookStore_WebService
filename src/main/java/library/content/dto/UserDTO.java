package library.content.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import library.content.purchase.Address;

@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDTO {

	private Long userId;

	private String userName;

	private Date userBirth;
	
	private Address userAddress;

	private BigDecimal totalUserCost;
	
	private String email;



	public UserDTO(Address address, String name, Date age,String email) {
		userAddress = address;
		totalUserCost = new BigDecimal("0");
		userName = name;
		userBirth=age;
		this.email = email;
	}

	public UserDTO() {
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
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	

	public Date getUserBirth() {
		return userBirth;
	}

	public void setUserBirth(Date userBirth) {
		this.userBirth = userBirth;
	}

	@Override
	public int hashCode() {
		HashCodeBuilder b = new HashCodeBuilder(17, 31).append(userId).append(userName).append(userAddress)
				.append(totalUserCost).append(userBirth);
		return b.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UserDTO))
			return false;
		if (obj == this)
			return true;

		UserDTO rhs = (UserDTO) obj;
		EqualsBuilder b = new EqualsBuilder().append(userId, rhs.get_userId()).append(userName, rhs.getUserName())
				.append(userAddress, rhs.get_address()).append(totalUserCost, rhs.get_totalCost()).append(userBirth,rhs.userBirth);
		return b.isEquals();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(userName + " ");
		buffer.append(userBirth + " ");
		buffer.append(userAddress.toString() + " ");
		buffer.append(totalUserCost.toString());
		return buffer.toString();
	}

}
