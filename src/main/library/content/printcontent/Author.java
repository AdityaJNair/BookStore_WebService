/**
 * 
 */
package main.library.content.printcontent;

import java.util.List;

import main.library.content.printcontent.books.ContentPrintType;

/**
 * @author adijn
 *
 */
public class Author {
	private String _name;
	private int _age;
	private List<ContentPrintType> _books;
	private BookGenre _mostKnownForGenre;
	
	
	public Author(String name, int age, List<ContentPrintType> books, BookGenre mostKnownForGenre){
		_name=name;
		_age=age;
		_books=books;
		_mostKnownForGenre=mostKnownForGenre;
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
	public List<ContentPrintType> get_books() {
		return _books;
	}
	public void set_books(List<ContentPrintType> _books) {
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
