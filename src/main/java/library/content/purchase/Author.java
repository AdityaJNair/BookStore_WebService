/**
 * 
 */
package library.content.purchase;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author adijn
 *
 */
@Entity
@XmlRootElement(name="Author")
@Access(AccessType.FIELD)
@XmlAccessorType(XmlAccessType.FIELD)
public class Author {
	
	@Id
	@GeneratedValue(generator="ID-GENERATOR")
	private Long authorId;

	@Column(nullable=false, name = "AUTHOR_NAME")
	private String authorName;
		
	@Column(nullable=false, name="AGE")
	private int authorAge;
		
	@Column(nullable=false, name="MAIN_GENRE")
	private BookGenre mostKnownForGenre;
	
	@OneToMany
	@JoinTable(name="COMMENT_AUTHOR", joinColumns=@JoinColumn(name="AUTHOR_ID",nullable=false), inverseJoinColumns=@JoinColumn(name="COMMENT_ID", nullable=false))
	private Set<Review> authorReviews;
	
	@Column(nullable=false, name="AUTHOR_DESCRIPTION")
	private String authorDescription;
	
	public Author(String name, int age, BookGenre mostKnownForGenre, String authorDescription){
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
	public int get_age() {
		return authorAge;
	}
	public void set_age(int _age) {
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
