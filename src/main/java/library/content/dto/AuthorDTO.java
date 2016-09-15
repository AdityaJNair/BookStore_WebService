/**
 * 
 */
package library.content.dto;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElement;

import library.content.purchase.BookGenre;

/**
 * @author adijn
 *
 */
@XmlRootElement(name="Author")
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthorDTO {
	
	private Long authorId;


	private String authorName;
		

	private int authorAge;
		

	private BookGenre mostKnownForGenre;
	
	@XmlElementWrapper(name="Author-Review") 
	@XmlElement(name="review")
	private Set<ReviewDTO> authorReviews;
	

	private String authorDescription;
	
	public AuthorDTO(String name, int age, BookGenre mostKnownForGenre, String authorDescription){
		this.authorName=name;
		this.authorAge=age;
		this.mostKnownForGenre=mostKnownForGenre;
		this.authorDescription = authorDescription;
		this.authorReviews = new HashSet<ReviewDTO>();
	}
	
	public AuthorDTO(){
		
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
	public Set<ReviewDTO> getAuthorReviews() {
		return authorReviews;
	}

	public void setAuthorReviews(Set<ReviewDTO> authorReviews) {
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
