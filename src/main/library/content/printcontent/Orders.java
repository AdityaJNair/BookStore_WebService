/**
 * 
 */
package main.library.content.printcontent;
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
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author adijn
 *
 */
@Entity
@XmlRootElement(name="Orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class Orders {
	
	@XmlID
	@XmlAttribute(name="xml-id")
	private String _xmlid;
	
	@Id
	@GeneratedValue(generator="ID-GENERATOR")
	@XmlAttribute(name="ID")
	private long _id;
	
	@XmlElement(name="Ordered_Books")
	@ElementCollection
	@CollectionTable(name="Ordered Books")
	@Column(name = "Books")
	@org.hibernate.annotations.CollectionId(
			columns = @Column( name = "Ordered Book_ID" ),
			type = @org.hibernate.annotations.Type( type = "long"),
			generator = "ID_GENERATOR")
	private Collection<ContentPrintType> _books;
	
	@XmlElement(name="Order_Cost")
	@Column(nullable=false, name="Order Cost")
	private BigDecimal totalCost;
	
	@XmlElement(name="Order_User")
	@ManyToOne(fetch=FetchType.LAZY)
	private User _user;
	
	
	public Orders(){
		
	}
	
	public Orders(User user){
		_user = user;
		_books=new ArrayList<ContentPrintType>();
	}
	
	

	@XmlElementWrapper(name="Books")
	public Collection<ContentPrintType> getBooks() {
		return _books;
	}
	public void setBooks(Collection<ContentPrintType> books) {
		this._books = books;
	}
	@XmlElement(name="Total_Cost")
	public BigDecimal getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}
	public long get_id() {
		return _id;
	}
	public void set_id(long _id) {
		this._id = _id;
		this._xmlid = getClass().getName()+_id;
	}
	public User get_user() {
		return _user;
	}
	public void set_user(User _user) {
		this._user = _user;
	}
	
	
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
}