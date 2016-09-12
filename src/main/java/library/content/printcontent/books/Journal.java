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
import java.util.List;

/**
 * @author adijn
 *
 */
@XmlRootElement(name="Journal")
@XmlAccessorType(XmlAccessType.FIELD)
public class Journal extends ContentPrintType {

	public Journal(String title, Author author, int year, String description, BigDecimal cost, PrintType printType, Publisher publisher){
		super(title,author,year,description,cost,printType,publisher,BookGenre.Journal);
	}
	
	public Journal(){
		
	}
}
