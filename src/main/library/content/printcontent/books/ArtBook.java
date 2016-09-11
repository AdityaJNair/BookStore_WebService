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
	private List<String> _artists;
	
	public ArtBook(String title, Author author, Date year, String description, BigDecimal cost, PrintType printType, Publisher publisher, List<String> artists){
		super(title,author,year,description,cost,printType,publisher,BookGenre.Art);
		this._artists = artists;
	}
	
	public ArtBook(){
	}

	public List<String> get_artists() {
		return _artists;
	}

	public void set_artists(List<String> _artists) {
		this._artists = _artists;
	}

}
