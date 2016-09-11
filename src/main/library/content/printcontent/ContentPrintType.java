package main.library.content.printcontent;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

/**
 * @author adijn
 *
 */
@Entity
public abstract class ContentPrintType {
	
	@Id
	private Long _bookId;
	
	@Column(nullable=false,name="Book Title")
	private String _title;
	
	@Embedded
	@OneToMany
	private Author _author;
	
	@Column(nullable=false,name="Date")
	private Date _year;
	
	@Column(nullable=false, name="Book Description")
	private String _description;
	
	@Column(nullable=false, name="Book cost")
	private BigDecimal _cost;
	
	@Column(nullable=false, name="Print Type")
	private PrintType _printType;
	
	@OneToOne
	private Publisher _publisher;
	
	@Column(nullable=false, name="Genre")
	private BookGenre _genre;
	
	public ContentPrintType(String title, Author author, Date year, String description, BigDecimal cost, PrintType printType, Publisher publisher, BookGenre genre){
		_title=title;
		_author=author;
		_year=year;
		_description=description;
		_cost=cost;
		_printType=printType;
		_publisher=publisher;
		_genre=genre;
	}

	public ContentPrintType(){
		
	}
	
	public Long get_bookId() {
		return _bookId;
	}
	public void set_bookId(Long _bookId) {
		this._bookId = _bookId;
	}
	public String get_title() {
		return _title;
	}
	public void set_title(String _title) {
		this._title = _title;
	}
	public Author get_author() {
		return _author;
	}
	public void set_author(Author _author) {
		this._author = _author;
	}
	public Date get_year() {
		return _year;
	}
	public void set_year(Date _year) {
		this._year = _year;
	}
	public String get_description() {
		return _description;
	}
	public void set_description(String _description) {
		this._description = _description;
	}
	public BigDecimal get_cost() {
		return _cost;
	}
	public void set_cost(BigDecimal _cost) {
		this._cost = _cost;
	}
	public Publisher get_publisher() {
		return _publisher;
	}
	public void set_publisher(Publisher _publisher) {
		this._publisher = _publisher;
	}
	public PrintType get_printType() {
		return _printType;
	}
	public void set_printType(PrintType _printType) {
		this._printType = _printType;
	}
	public BookGenre get_genre() {
		return _genre;
	}
	public void set_genre(BookGenre _genre) {
		this._genre = _genre;
	}
}
