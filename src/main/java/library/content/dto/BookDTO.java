package library.content.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElement;

import library.content.purchase.BookGenre;
import library.content.purchase.PrintType;
import library.content.purchase.Publisher;

/**
 * @author adijn
 *
 */

@XmlRootElement(name="Book")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookDTO {
	
	private Long bookId;
	
	private String title;
	
	@XmlElementWrapper(name="Book-Authors") 
	@XmlElement(name="authors")
	private Set<AuthorDTO> authors;
	
	private Date datePublished;
	
	private String description;
	
	private BigDecimal cost;
	
	private PrintType printType;
	
	private Publisher publisher;
	
	private BookGenre genre;
	
	private String isbn;
	
	private String language;
	
	@XmlElementWrapper(name="Book-Reviews") 
	@XmlElement(name="reviews")
	private Set<ReviewDTO> bookReviews;
	

	public BookDTO(String title, Date year, String description, BigDecimal cost, PrintType printType, Publisher publisher, BookGenre genre, String isbn, String language){
		this.title=title;
		this.authors=new HashSet<AuthorDTO>();
		this.datePublished=year;
		this.description=description;
		this.cost=cost;
		this.printType=printType;
		this.publisher=publisher;
		this.genre=genre;
		this.isbn=isbn;
		this.language=language;
		this.bookReviews = new HashSet<ReviewDTO>();
	}

	public BookDTO(){
		
	}
	
	public Long get_bookId() {
		return bookId;
	}
	public void set_bookId(Long _bookId) {
		this.bookId = _bookId;
	}
	public String get_title() {
		return title;
	}
	public void set_title(String _title) {
		this.title = _title;
	}
	public Set<AuthorDTO> get_author() {
		return authors;
	}
	public void set_author(Set<AuthorDTO> _author) {
		this.authors = _author;
	}
	public Date getDatePublished() {
		return datePublished;
	}
	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}
	public String get_description() {
		return description;
	}
	public void set_description(String _description) {
		this.description = _description;
	}
	public BigDecimal get_cost() {
		return cost;
	}
	public void set_cost(BigDecimal _cost) {
		this.cost = _cost;
	}
	public Publisher get_publisher() {
		return publisher;
	}
	public void set_publisher(Publisher _publisher) {
		this.publisher = _publisher;
	}
	public PrintType get_printType() {
		return printType;
	}
	public void set_printType(PrintType _printType) {
		this.printType = _printType;
	}
	public BookGenre get_genre() {
		return genre;
	}
	public void set_genre(BookGenre _genre) {
		this.genre = _genre;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public void addAuthorToSet(AuthorDTO a){
		authors.add(a);
	}
	
	
	public Set<ReviewDTO> getBookReviews() {
		return bookReviews;
	}

	public void setBookReviews(Set<ReviewDTO> bookReviews) {
		this.bookReviews = bookReviews;
	}

	//Do override toString, Equals and hashCode
	/*
	@Override
	public boolean equals(Object obj) {
	}
	
	@Override
	public int hashCode() {
	}
	*/
}
