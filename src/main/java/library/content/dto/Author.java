/**
 * 
 */
package library.content.dto;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import library.content.printcontent.BookGenre;

/**
 * @author adijn
 *
 */
@Entity
@XmlRootElement(name="Author")
@Access(AccessType.FIELD)
@XmlAccessorType(XmlAccessType.FIELD)
public class Author {
	
	@XmlID
	@XmlAttribute(name="xml-id-author")
	private String _xml_id;
	
	@Id
	@GeneratedValue(generator="ID-GENERATOR")
	private Long authorId;

	@Column(nullable=false, name = "Name")
	private String authorName;
	
	@Column(nullable=false, name="Age")
	private int authorAge;
		
	@Column(nullable=false, name="Main genre")
	private BookGenre mostKnownForGenre;
	

	@Column(nullable=false, name="Author_Description")
	private String authorDescription;
	
	public Author(String name, int age, BookGenre mostKnownForGenre, String authorDescription){
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
		this._xml_id = getClass().getName()+_authorId;
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
