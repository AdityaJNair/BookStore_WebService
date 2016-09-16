/**
 * 
 */
package library.content.dto;

import java.util.Date;
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

import library.content.purchase.BookGenre;

/**
 * @author adijn
 *
 */
@XmlRootElement(name = "Author")
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthorDTO {

	private Long authorId;

	private String authorName;

	private Date authorAge;

	private BookGenre mostKnownForGenre;

	@XmlElementWrapper(name = "Author-Review")
	@XmlElement(name = "review")
	private Set<ReviewDTO> authorReviews;

	private String authorDescription;

	public AuthorDTO(String name, Date age, BookGenre mostKnownForGenre, String authorDescription) {
		this.authorName = name;
		this.authorAge = age;
		this.mostKnownForGenre = mostKnownForGenre;
		this.authorDescription = authorDescription;
		this.authorReviews = new HashSet<ReviewDTO>();
	}

	public AuthorDTO() {

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

	public Set<ReviewDTO> getAuthorReviews() {
		return authorReviews;
	}

	public void setAuthorReviews(Set<ReviewDTO> authorReviews) {
		this.authorReviews = authorReviews;
	}

	@Override
	public int hashCode() {
		HashCodeBuilder b = new HashCodeBuilder(17, 31). 
	            append(authorId).
	            append(authorName).
	            append(authorAge).
	            append(mostKnownForGenre);
		Iterator<ReviewDTO> it1 = this.getAuthorReviews().iterator();
		while (it1.hasNext()) {
			b.append(it1.next());
		}
		return b.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AuthorDTO))
			return false;
		if (obj == this)
			return true;

		AuthorDTO rhs = (AuthorDTO) obj;
		EqualsBuilder b = new EqualsBuilder().append(authorId, rhs.get_authorId()).append(authorName, rhs.get_name())
				.append(authorAge, rhs.get_age()).append(mostKnownForGenre, rhs.get_mostKnownForGenre());
		Iterator<ReviewDTO> it1 = this.getAuthorReviews().iterator();
		Iterator<ReviewDTO> it2 = rhs.getAuthorReviews().iterator();
		while (it1.hasNext() && it2.hasNext()) {
			b.append(it1.next(), it2.next());
		}
		return b.isEquals();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(authorId + " ");
		buffer.append(authorName + " ");
		buffer.append(authorAge + " ");
		buffer.append(mostKnownForGenre + " ");
		for (ReviewDTO r : authorReviews) {
			buffer.append(r.toString() + " ");
		}
		buffer.append(authorDescription);
		return buffer.toString();
	}
}
