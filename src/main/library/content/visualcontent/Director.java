/**
 * 
 */
package main.library.content.visualcontent;

import java.util.List;

import main.library.content.printcontent.ContentPrintType;
import main.library.content.printcontent.BookGenre;

/**
 * @author adijn
 *
 */
public class Director {
	private String _name;
	private int _age;
	private List<ContentPrintType> _books;
	private FilmGenre _mostKnownForGenre;
	
	public Director(String name, int age, List<ContentPrintType> books, FilmGenre mostKnownForGenre){
		_name=name;
		_age=age;
		_books=books;
		_mostKnownForGenre=mostKnownForGenre;
	}
	
	public Director(){
		
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
	public FilmGenre get_mostKnownForGenre() {
		return _mostKnownForGenre;
	}
	public void set_mostKnownForGenre(FilmGenre _mostKnownForGenre) {
		this._mostKnownForGenre = _mostKnownForGenre;
	}
}
