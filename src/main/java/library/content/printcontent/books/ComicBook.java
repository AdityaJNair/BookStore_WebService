/**
 * 
 */
package library.content.printcontent.books;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import library.content.printcontent.Author;
import library.content.printcontent.BookGenre;
import library.content.printcontent.ContentPrintType;
import library.content.printcontent.PrintType;
import library.content.printcontent.Publisher;

/**
 * @author adijn
 *
 */
@XmlRootElement(name="Comic-Book")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComicBook extends ContentPrintType {
	
	private BookGenre secondGenre;
	
	public ComicBook(String title, Author author, int year, String description, BigDecimal cost, PrintType printType, Publisher publisher, BookGenre b){
		super(title,author,year,description,cost,printType,publisher,b);
		secondGenre = b;
	}
	
	public ComicBook(){
		
	}
	
}
