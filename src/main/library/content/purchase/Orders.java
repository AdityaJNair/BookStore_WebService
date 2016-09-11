/**
 * 
 */
package main.library.content.purchase;
import java.math.BigDecimal;
import javax.xml.bind.annotation.*;
import java.util.List;

import main.library.content.printcontent.ContentPrintType;


/**
 * @author adijn
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Orders {
	@XmlID
	@XmlAttribute(name="xml-id")
	private String _xmlid;
	
	@XmlAttribute(name="ID")
	private long _id;
	
	private List<ContentPrintType> books;
	private BigDecimal totalCost;
	private User _user;
	

	@XmlElementWrapper(name="Books")
	public List<ContentPrintType> getBooks() {
		return books;
	}
	public void setBooks(List<ContentPrintType> books) {
		this.books = books;
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
}