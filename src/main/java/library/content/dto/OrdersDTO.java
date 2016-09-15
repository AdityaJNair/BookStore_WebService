/**
 * 
 */
package library.content.dto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElement;


/**
 * @author adijn
 *
 */
@XmlRootElement(name="Orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrdersDTO {
		
	private long order_id;
	
	@XmlElementWrapper(name="Ordered-Books") 
	@XmlElement(name="books")
	private Collection<BookDTO> orderedBooks;

	private BigDecimal totalCost;
	
	private UserDTO usersOrder;
		
	public OrdersDTO(UserDTO user){
		totalCost = new BigDecimal("0");
		orderedBooks=new ArrayList<BookDTO>();
		usersOrder = user;
	}
	
	public OrdersDTO(){
		
	}
	public Collection<BookDTO> getBooks() {
		return orderedBooks;
	}
	public void setBooks(Collection<BookDTO> books) {
		this.orderedBooks = books;
	}
	public BigDecimal getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}
	public long get_id() {
		return order_id;
	}
	public void set_id(long _id) {
		this.order_id = _id;
	}
	public UserDTO get_user() {
		return usersOrder;
	}
	public void set_user(UserDTO _user) {
		this.usersOrder = _user;
	}
	public void addBookToOrder(BookDTO item){
		orderedBooks.add(item);
		totalCost = totalCost.add(item.get_cost());
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