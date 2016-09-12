/**
 * 
 */
package library.content.printcontent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.*;


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
	
	
	@XmlElementWrapper(name="Ordered-Books")
	@XmlElement(name="Books")
	@ElementCollection
	@CollectionTable(name="Ordered Books")
	@Column(name = "Books")
	@org.hibernate.annotations.CollectionId(
			columns = @Column( name = "Ordered Book_ID" ),
			type = @org.hibernate.annotations.Type( type = "long"),
			generator = "ID_GENERATOR")
	private Collection<ContentPrintType> orderedBooks;
	
	@XmlElement(name="Order_Cost")
	@Column(nullable=false, name="Order Cost")
	private BigDecimal totalCost;
	
	
	@XmlTransient
	@ManyToOne(fetch=FetchType.LAZY)
	private User usersOrder;
	
	
	public Orders(){
		
	}
	
	public Orders(User user){
		usersOrder = user;
		totalCost = new BigDecimal("0");
		orderedBooks=new ArrayList<ContentPrintType>();
	}
	
	
	public Collection<ContentPrintType> getBooks() {
		return orderedBooks;
	}
	public void setBooks(Collection<ContentPrintType> books) {
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
	public void addBookToOrder(ContentPrintType item){
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