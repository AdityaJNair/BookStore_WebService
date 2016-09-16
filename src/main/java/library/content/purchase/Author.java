/**
 * 
 */
package library.content.purchase;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author adijn
 *
 */
@Entity
@Access(AccessType.FIELD)
public class Author {
	
	@Id
	@GeneratedValue(generator="ID-GENERATOR")
	private Long authorId;

	@Column(nullable=false, name = "AUTHOR_NAME")
	private String authorName;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_OF_BIRTH",nullable=false)
	private Date authorAge;
		
	@Column(nullable=false, name="MAIN_GENRE")
	private BookGenre mostKnownForGenre;
		
	@OneToMany
	@JoinTable(name="REVIEW_AUTHOR", joinColumns=@JoinColumn(name="AUTHOR_ID",nullable=false), inverseJoinColumns=@JoinColumn(name="REVIEW_ID", nullable=false))
	private Set<Review> authorReviews;
	
	@Column(nullable=false, name="AUTHOR_DESCRIPTION")
	private String authorDescription;
	
	public Author(String name, Date age, BookGenre mostKnownForGenre, String authorDescription){
		this.authorName=name;
		this.authorAge=age;
		this.mostKnownForGenre=mostKnownForGenre;
		this.authorDescription = authorDescription;
		this.authorReviews = new HashSet<Review>();
	}
	
	public Author(){
		
	}
	
	
	public Long get_authorId() {
		return authorId;
	}
	public void set_authorId(Long _authorId) {
		this.authorId = _authorId;
	}
	public String get_name() {
		return authorName;
	}
	public void set_name(String _name) {
		this.authorName = _name;
	}
	public Date get_age() {
		return authorAge;
	}
	public void set_age(Date _age) {
		this.authorAge = _age;
	}
	public BookGenre get_mostKnownForGenre() {
		return mostKnownForGenre;
	}
	public void set_mostKnownForGenre(BookGenre _mostKnownForGenre) {
		this.mostKnownForGenre = _mostKnownForGenre;
	}
	public String get_description() {
		return authorDescription;
	}
	public void set_description(String _description) {
		this.authorDescription = _description;
	}
	public Set<Review> getAuthorReviews() {
		return authorReviews;
	}

	public void setAuthorReviews(Set<Review> authorReviews) {
		this.authorReviews = authorReviews;
	}

	


	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(authorName+" ");
		buffer.append(authorAge+" ");
		buffer.append(mostKnownForGenre+" ");
		for(Review r: authorReviews){
			buffer.append(r.toString()+ " ");
		}
		buffer.append(authorDescription);
		return buffer.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Author))
            return false;
        if (obj == this)
            return true;

        Author other = (Author)obj;
        return authorName.equals(other.authorName) && authorAge.equals(other.authorAge);
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(authorName).
	            append(authorAge).
	            toHashCode();
	}
	
}
