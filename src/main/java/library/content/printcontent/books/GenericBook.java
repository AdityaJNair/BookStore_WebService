/**
 * 
 */
package library.content.printcontent.books;

import java.math.BigDecimal;
import javax.xml.bind.annotation.*;

import library.content.printcontent.Author;
import library.content.printcontent.BookGenre;
import library.content.printcontent.ContentPrintType;
import library.content.printcontent.PrintType;
import library.content.printcontent.Publisher;

import java.util.Date;

/**
 * @author adijn
 *
 */
@XmlRootElement(name="Generic-Book")
@XmlAccessorType(XmlAccessType.FIELD)
public class GenericBook extends ContentPrintType {
	public GenericBook(String title, Author author, int year, String description, BigDecimal cost, PrintType printType, Publisher publisher, BookGenre genre){
		super(title,author,year,description,cost, printType,publisher, genre);
	}
	
	public GenericBook(){
		
	}
}
