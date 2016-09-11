/**
 * 
 */
package main.library.content.printcontent.books;

import java.math.BigDecimal;
import java.util.Date;

import main.library.content.printcontent.Author;
import main.library.content.printcontent.BookGenre;
import main.library.content.printcontent.ContentPrintType;
import main.library.content.printcontent.PrintType;

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
