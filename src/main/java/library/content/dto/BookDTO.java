package library.content.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElement;

import library.content.purchase.Author;
import library.content.purchase.Publisher;
import library.content.purchase.Review;
import library.content.purchase.enums.BookGenre;
import library.content.purchase.enums.PrintType;

/**
 * @author adijn
 *
 */

@XmlRootElement(name="Book")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookDTO {
	
	private Long bookId;
	
	private String title;
	
	private String description;
	
	private Date datePublished;
	
	private BigDecimal cost;
	
	private PrintType printType;
	
	private Publisher publisher;
	
	private BookGenre genre;
	
	private String isbn;
	
	private String language;
	
	private AuthorDTO author;


	public BookDTO(String title, Date year, AuthorDTO a,String description, BigDecimal cost, PrintType printType, Publisher publisher, BookGenre genre, String isbn, String language){
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

	public BookDTO(){
		
	}
	
	public Date getdatePublished(){
		return datePublished;
	}
	public void setdatePublished(Date date){
		datePublished= date;
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
	public AuthorDTO get_author() {
		return author;
	}
	public void set_author(AuthorDTO _author) {
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
	
	@Override
	public int hashCode() {
		HashCodeBuilder b = new HashCodeBuilder(17, 31). 
	            append(bookId).
	            append(title).
	            append(description).
	            append(datePublished).
		        append(cost).
		        append(printType).
		        append(publisher.toString()).
		        append(genre).
		        append(isbn).
		        append(language).append(author);
		return b.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BookDTO))
			return false;
		if (obj == this)
			return true;

		BookDTO rhs = (BookDTO) obj;
		EqualsBuilder b = new EqualsBuilder().append(bookId, rhs.bookId).append(title, rhs.title)
				.append(description, rhs.description).append(datePublished, rhs.datePublished).append(cost, rhs.cost)
						.append(printType, rhs.printType)
						.append(publisher, rhs.publisher).append(genre, rhs.genre).append(isbn, rhs.isbn).append(language,rhs.language).append(author, rhs.author);
		return b.isEquals();
	}
}
