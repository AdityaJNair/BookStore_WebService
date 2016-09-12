/**
 * 
 */
package main.library.content.printcontent.books;

import java.math.BigDecimal;
import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.List;

import main.library.content.printcontent.Author;
import main.library.content.printcontent.BookGenre;
import main.library.content.printcontent.ContentPrintType;
import main.library.content.printcontent.PrintType;
import main.library.content.printcontent.Publisher;

/**
 * @author adijn
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ArtBook extends ContentPrintType {
	
	public ArtBook(String title, Author author, Date year, String description, BigDecimal cost, PrintType printType, Publisher publisher){
		super(title,author,year,description,cost,printType,publisher,BookGenre.Art);
	}
	
	public ArtBook(){
	}
}
