/**
 * 
 */
package library.content.printcontent.books;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.*;

import library.content.printcontent.Author;
import library.content.printcontent.BookGenre;
import library.content.printcontent.ContentPrintType;
import library.content.printcontent.PrintType;
import library.content.printcontent.Publisher;
/**
 * @author adijn
 *
 */
@XmlRootElement(name="Biography-Book")
@XmlAccessorType(XmlAccessType.FIELD)
public class BiographyBook extends ContentPrintType {
	
	public BiographyBook(){
	}
	
	public BiographyBook(String title, Author author, int year, String description, BigDecimal cost, PrintType printType, Publisher publisher){
		super(title,author,year,description,cost,printType,publisher,BookGenre.Biography);
	}

}
