/**
 * 
 */
package main.library.content.printcontent;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author adijn
 *
 */
public class ComicBook extends ContentPrintType {
	
	public ComicBook(String title, Author author, Date year, String description, BigDecimal cost, PrintType printType, String publisher){
		super(title,author,year,description,cost,printType,publisher,BookGenre.Comic);
	}
	
	public ComicBook(){
		
	}
	
}
