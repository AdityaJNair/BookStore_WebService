/**
 * 
 */
package main.library.content.printcontent.books;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import main.library.content.printcontent.Author;
import main.library.content.printcontent.BookGenre;
import main.library.content.printcontent.PrintType;

/**
 * @author adijn
 *
 */
public class BiographyBook extends ContentPrintType {
	
	public BiographyBook(){
	}
	
	public BiographyBook(String title, Author author, Date year, String description, BigDecimal cost, PrintType printType, String publisher){
		super(title,author,year,description,cost,printType,publisher,BookGenre.Biography);
	}

}
