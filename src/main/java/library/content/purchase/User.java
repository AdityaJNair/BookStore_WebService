package library.content.purchase;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * User class for 'users' in the online book store
 */
@Entity
public class User {
	
	//ID for user
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userId;
	
	//name for user
	@Column(name="USER_NAME", nullable=false)
	private String userName;
	
	//birth date
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_OF_BIRTH", nullable=false)
	private Date userAge;
	
	//unique email for user
	@Column(name="USER_EMAIL", nullable=false, unique=true)
	private String email;
	
	//reviews the user makes for the books
	@ElementCollection
	private Set<Review> reviews;
	
	//User address
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
	
	//Set of all books the user purchased
	@OneToMany(fetch=FetchType.LAZY)
	private Set<Book> usersBooks;
	
	//Total purchase account cost for this user
	@Column(nullable=false, name="Total_cost")
	private BigDecimal totalUserCost;
	
	
	public User(Address address, String name, Date age, String email){
		userAddress=address;
		usersBooks=new HashSet<Book>();
		userName = name;
		userAge=age;
		reviews= new HashSet<Review>();
		totalUserCost = new BigDecimal("0");
		this.email = email;
	}
	
	public User(){
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
	
	public void addBook(Book x){
		usersBooks.add(x);
		totalUserCost = totalUserCost.add(x.get_cost());
	}
	
	public void removeBook(Book x){
		usersBooks.remove(x);
		totalUserCost = totalUserCost.subtract(x.get_cost());
	}
	
	
	public Set<Book> getUsersBooks() {
		return usersBooks;
	}

	public void setUsersBooks(Set<Book> usersBooks) {
		this.usersBooks = usersBooks;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
	
	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public void addReview(Review r){
		reviews.add(r);
	}


	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(userId.toString() +" ");
		buffer.append(userName+" ");
		buffer.append(userAge+" ");
		buffer.append(userAddress.toString()+" ");
		buffer.append(totalUserCost.toString());
		for(Book b: usersBooks){
			buffer.append(b.toString()+" ");
		}
		return buffer.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User))
            return false;
        if (obj == this)
            return true;
        
        User usr = (User) obj;
        return new EqualsBuilder().append(userName,usr.userName).append(userAddress,usr.userAddress).append(userAge,usr.userAge).append(usersBooks,usr.usersBooks).isEquals();
	}
	
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(userName).
	            append(userAddress).
	            append(userAge.toString()).
	            append(usersBooks).
	            toHashCode();
	}


}
