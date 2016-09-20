/**
 * 
 */
package library.content.domain;
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

import library.content.domain.enums.BookGenre;

/**
 * 
 * Author entity that is stored in the database.
 * @author adijn
 *
 */
@Entity
@Access(AccessType.FIELD)
public class Author {
	
	//id for author
	@Id
	@GeneratedValue(generator="ID-GENERATOR")
	private Long authorId;

	//name of author, is unique
	@Column(nullable=false, name = "AUTHOR_NAME",unique=true)
	private String authorName;
	
	//birth date for author
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_OF_BIRTH",nullable=false)
	private Date authorAge;
	
	//most known genre for author
	@Column(nullable=false, name="MAIN_GENRE")
	private BookGenre mostKnownForGenre;
		
	//description for author
	@Column(nullable=false, name="AUTHOR_DESCRIPTION")
	private String authorDescription;
	
	public Author(String name, Date age, BookGenre mostKnownForGenre, String authorDescription){
		this.authorName=name;
		this.authorAge=age;
		this.mostKnownForGenre=mostKnownForGenre;
		this.authorDescription = authorDescription;
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


	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(authorName+" ");
		buffer.append(authorAge+" ");
		buffer.append(mostKnownForGenre+" ");
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
        return authorName.equals(other.authorName);
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(authorName).
	            toHashCode();
	}
	
}
