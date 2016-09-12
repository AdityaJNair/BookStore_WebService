package library.content.printcontent;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import library.content.printcontent.books.ArtBook;
import library.content.printcontent.books.BiographyBook;
import library.content.printcontent.books.ComicBook;
import library.content.printcontent.books.EducationalBook;
import library.content.printcontent.books.GenericBook;
import library.content.printcontent.books.Journal;
import library.content.printcontent.books.Magazine;

/**
 * @author adijn
 *
 */

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@XmlRootElement(name="ContentPrintType")
@XmlSeeAlso({ArtBook.class, BiographyBook.class, ComicBook.class, EducationalBook.class, GenericBook.class, Journal.class, Magazine.class} )
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ContentPrintType {
	
	@XmlID
	@XmlAttribute(name="xml-id-printmedia")
	private String _xml_id;

	@Id
	@GeneratedValue(generator="ID-GENERATOR")
	private Long bookId;
	
	@Column(nullable=false,name="Book_Title")
	private String title;
	
	@Embedded
	@OneToMany(mappedBy="_books", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	private Author author;
	
	@Column(nullable=false,name="Date")
	private int year;
	
	@Column(nullable=false, name="Book_Description")
	private String description;
	
	@Column(nullable=false, name="Book_Cost")
	private BigDecimal cost;
	
	@Enumerated
	@Column(nullable=false, name="Print_Type")
	private PrintType printType;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="Publisher ID", nullable=false)
	private Publisher publisher;
	
	@Enumerated
	@Column(nullable=false, name="Genre")
	private BookGenre genre;
	
	public ContentPrintType(String title, Author author, int year, String description, BigDecimal cost, PrintType printType, Publisher publisher, BookGenre genre){
		this.title=title;
		this.author=author;
		this.year=year;
		this.description=description;
		this.cost=cost;
		this.printType=printType;
		this.publisher=publisher;
		this.genre=genre;
	}

	public ContentPrintType(){
		
	}
	
	public Long get_bookId() {
		return bookId;
	}
	public void set_bookId(Long _bookId) {
		this.bookId = _bookId;
		this._xml_id = getClass().getName()+_bookId;
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
	public int get_year() {
		return year;
	}
	public void set_year(int _year) {
		this.year = _year;
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
	
	//Do override toString, Equals and hashCode
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(_xml_id);
		buffer.append("   ");
		buffer.append(bookId);
		buffer.append("   ");
		buffer.append(title);
		buffer.append("   ");
		buffer.append(author.toString());
		buffer.append("   ");
		buffer.append(year);
		buffer.append("   ");
		buffer.append(description);
		buffer.append("   ");
		buffer.append(cost);
		buffer.append("   ");
		buffer.append(printType);
		buffer.append("   ");
		buffer.append(publisher.toString());
		buffer.append("   ");
		buffer.append(genre);
		return buffer.toString();
		
	}
	/*
	@Override
	public boolean equals(Object obj) {
	}
	
	@Override
	public int hashCode() {
	}
	*/
}
