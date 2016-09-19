/**
 * 
 */
package library.content.dto;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


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
	private Set<BookDTO> orderedBooks;

	private BigDecimal totalCost;
	
	private UserDTO usersOrder;
		
	public OrdersDTO(UserDTO user){
		totalCost = new BigDecimal("0");
		orderedBooks=new HashSet<BookDTO>();
		usersOrder = user;
	}
	
	public OrdersDTO(){
		
	}
	public Set<BookDTO> getBooks() {
		return orderedBooks;
	}
	public void setBooks(Set<BookDTO> books) {
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
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(order_id+" ");
		buffer.append(totalCost+" ");
		buffer.append(usersOrder.toString()+" ");
		return buffer.toString();
	}
	
	@Override
	public int hashCode() {
		HashCodeBuilder b = new HashCodeBuilder(17, 31). 
	            append(totalCost).
	            append(usersOrder.toString());
		Iterator<BookDTO> it1 = this.getBooks().iterator();
		while (it1.hasNext()) {
			b.append(it1.next().toString());
		}
		return b.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof OrdersDTO))
			return false;
		if (obj == this)
			return true;

		OrdersDTO rhs = (OrdersDTO) obj;
		EqualsBuilder b = new EqualsBuilder().append(totalCost, rhs.totalCost)
				.append(usersOrder, rhs.usersOrder);
		Iterator<BookDTO> it1 = this.orderedBooks.iterator();
		Iterator<BookDTO> it2 = rhs.orderedBooks.iterator();
		while (it1.hasNext() && it2.hasNext()) {
			b.append(it1.next(), it2.next());
		}
		return b.isEquals();
	}

}