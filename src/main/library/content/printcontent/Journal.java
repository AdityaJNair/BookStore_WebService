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
public class Journal extends ContentPrintType {

	public Journal(String title, Author author, Date year, String description, BigDecimal cost, PrintType printType, String publisher){
		super(title,author,year,description,cost,printType,publisher,BookGenre.Journal);
	}
	
	public Journal(){
		
	}
}
