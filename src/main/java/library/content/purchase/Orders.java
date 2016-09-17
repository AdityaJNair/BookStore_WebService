/**
 * 
 */
package library.content.purchase;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author adijn
 *
 */
@Entity
public class Orders {
	
	@Id
	@GeneratedValue(generator="ID-GENERATOR")
	private long order_id;
	
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	@JoinTable(name="ORDERED_BOOKS", joinColumns=@JoinColumn(name="ORDER_ID",nullable=false), inverseJoinColumns=@JoinColumn(name="BOOK_ID", nullable=false))
	private Set<Book> orderedBooks;
	
	@Column(nullable=false, name="ORDER_COST")
	private BigDecimal totalCost;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID",nullable=false)
	private User usersOrder;
		
	public Orders(User user){
		totalCost = new BigDecimal("0");
		orderedBooks=new HashSet<Book>();
		usersOrder = user;
	}
	
	public Orders(){
		
	}
	
	public Set<Book> getBooks() {
		return orderedBooks;
	}
	public void setBooks(Set<Book> books) {
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
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Orders))
            return false;
        if (obj == this)
            return true;

        Orders other = (Orders)obj;
        return order_id == other.order_id;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(order_id).
	            toHashCode();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(order_id+" ");
		for(Book b: orderedBooks){
			buffer.append(b.toString()+ " ");
		}
		buffer.append(totalCost+" ");
		buffer.append(usersOrder.toString()+" ");
		return buffer.toString();
	}
	

}