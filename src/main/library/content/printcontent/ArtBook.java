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
public class ArtBook extends ContentPrintType {
	private List<String> _artists;
	
	public ArtBook(String title, Author author, Date year, String description, BigDecimal cost, PrintType printType, String publisher, List<String> artists){
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
