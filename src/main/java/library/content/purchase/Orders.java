/**
 * 
 */
package library.content.purchase;
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
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * @author adijn
 *
 */
@Entity
@XmlRootElement(name="Orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class Orders {
	
	@XmlID
	@XmlAttribute(name="xml-id-orders")
	private String _xml_id;
	
	@Id
	@GeneratedValue(generator="ID-GENERATOR")
	private long order_id;
	
	
	@XmlElement(name="Books")
	@OneToMany
	@JoinTable(name="ORDERED_BOOKS", joinColumns=@JoinColumn(name="ORDER_ID",nullable=false), inverseJoinColumns=@JoinColumn(name="BOOK_ID", nullable=false))
	private Collection<Book> orderedBooks;
	
	@XmlElement(name="Order_Cost")
	@Column(nullable=false, name="ORDER_COST")
	private BigDecimal totalCost;
	
	
	@XmlTransient
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID",nullable=false)
	private User usersOrder;
		
	public Orders(User user){
		totalCost = new BigDecimal("0");
		orderedBooks=new ArrayList<Book>();
		usersOrder = user;
	}
	
	public Orders(){
		
	}
	
	public Collection<Book> getBooks() {
		return orderedBooks;
	}
	public void setBooks(Collection<Book> books) {
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
		this._xml_id = getClass().getName()+_id;
	}
	public User get_user() {
		return usersOrder;
	}
	public void set_user(User _user) {
		this.usersOrder = _user;
	}
	public void addBookToOrder(Book item){
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