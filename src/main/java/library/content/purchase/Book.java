package library.content.purchase;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import library.content.purchase.enums.BookGenre;
import library.content.purchase.enums.PrintType;

/**
 * @author adijn
 *
 */

@Entity
public class Book {

	@Id
	@GeneratedValue(generator="ID-GENERATOR")
	private Long bookId;
	
	@Column(nullable=false,name="BOOK_TITLE")
	private String title;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name="AUTHOR_ID", nullable=false)
	private Author author;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false,name="DATE_CREATED")
	private Date datePublished;
	
	@Column(nullable=false, name="BOOK_DESCRIPTION")
	private String description;
	
	@Column(nullable=false, name="BOOK_COST")
	private BigDecimal cost;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false, name="PRINT_TYPE")
	private PrintType printType;
	
	@Embedded
	private Publisher publisher;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false, name="GENRE")
	private BookGenre genre;
	
	@Column(nullable=false, name="ISBN", unique=true)
	private String isbn;
	
	@Column(nullable=false, name="LANGUAGE")
	private String language;


	public Book(String title, Date year, Author a,String description, BigDecimal cost, PrintType printType, Publisher publisher, BookGenre genre, String isbn, String language){
		this.title=title;
		this.author=a;
		this.datePublished=year;
		this.description=description;
		this.cost=cost;
		this.printType=printType;
		this.publisher=publisher;
		this.genre=genre;
		this.isbn=isbn;
		this.language=language;
	}

	public Book(){
		
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
	public Author get_author() {
		return author;
	}
	public void set_author(Author _author) {
		this.author = _author;
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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Book))
            return false;
        if (obj == this)
            return true;

        Book other = (Book)obj;
        return isbn.equals(other.isbn);
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(bookId).
	            toHashCode();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(bookId+" ");
		buffer.append(title + " ");
		buffer.append(author+" ");
		buffer.append(datePublished.toString()+ " ");
		buffer.append(description+" ");
		buffer.append(cost.toString()+ " ");
		buffer.append(printType+" ");
		buffer.append(publisher.toString()+ " ");
		buffer.append(genre + " ");
		buffer.append(isbn+ " ");
		buffer.append(language + " ");
		return buffer.toString();
	}
	
}
