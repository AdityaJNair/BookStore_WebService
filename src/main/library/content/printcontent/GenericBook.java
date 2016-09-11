/**
 * 
 */
package main.library.content.printcontent;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author adijn
 *
 */
public class GenericBook extends ContentPrintType {
	public GenericBook(String title, Author author, Date year, String description, BigDecimal cost, PrintType printType, String publisher, BookGenre genre){
		super(title,author,year,description,cost, printType,publisher, genre);
	}
	
	public GenericBook(){
		
	}
}
