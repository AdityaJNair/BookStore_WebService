/**
 * 
 */
package main.library.content.printcontent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author adijn
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Author {
	private String _name;
	private int _age;
	private Set<ContentPrintType> _books;
	private BookGenre _mostKnownForGenre;
	
	
	public Author(String name, int age, BookGenre mostKnownForGenre){
		_name=name;
		_age=age;
		_mostKnownForGenre=mostKnownForGenre;
		_books = new HashSet<ContentPrintType>();
	}
	
	public Author(){
		
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
	private String _description;
}
