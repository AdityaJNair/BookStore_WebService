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
public class EducationalBook extends ContentPrintType {
	
	public EducationalBook(String title, Author author, Date year, String description, BigDecimal cost, PrintType printType, String publisher, BookGenre genre){
		super(title,author,year,description,cost, printType,publisher, genre);
	}
	
	public EducationalBook(){
		
	}

}
