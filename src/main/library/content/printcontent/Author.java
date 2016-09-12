/**
 * 
 */
package main.library.content.printcontent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
	@XmlElement(name="Author_ID")
	private Long _authorId;
	
	@XmlElement(name="Author_Name")
	@Column(nullable=false, name = "Name")
	private String _name;
	
	@XmlElement(name="Age")
	@Column(nullable=false, name="Age")
	private int _age;
	
	@XmlElement(name="Books_Authored")
	@ElementCollection
	@CollectionTable(name="Books_Authored",
		joinColumns = @JoinColumn(name="Author_iD"))
	@Column(name="Books")
	private Set<ContentPrintType> _books;
	
	@XmlElement(name="Main_Genre")
	@Column(nullable=false, name="Main genre")
	private BookGenre _mostKnownForGenre;
	
	@XmlElement(name="Author_Description")
	@Column(nullable=false, name="Author_Description")
	private String _description;
	
	public Author(String name, int age, BookGenre mostKnownForGenre){
		_name=name;
		_age=age;
		_mostKnownForGenre=mostKnownForGenre;
		_books = new HashSet<ContentPrintType>();
	}
	
	public Author(){
		
	}
	
	
	public Long get_authorId() {
		return _authorId;
	}
	public void set_authorId(Long _authorId) {
		this._authorId = _authorId;
	}
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public int get_age() {
		return _age;
	}
	public void set_age(int _age) {
		this._age = _age;
	}
	public Set<ContentPrintType> get_books() {
		return _books;
	}
	public void set_books(Set<ContentPrintType> _books) {
		this._books = _books;
	}
	public BookGenre get_mostKnownForGenre() {
		return _mostKnownForGenre;
	}
	public void set_mostKnownForGenre(BookGenre _mostKnownForGenre) {
		this._mostKnownForGenre = _mostKnownForGenre;
	}
	public String get_description() {
		return _description;
	}
	public void set_description(String _description) {
		this._description = _description;
	}
	
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

}
