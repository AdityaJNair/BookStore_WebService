/**
 * 
 */
package main.library.content.purchase;
import java.math.BigDecimal;
import java.util.List;

import main.library.content.printcontent.ContentPrintType;


/**
 * @author adijn
 *
 */
public class Orders {
	private List<ContentPrintType> books;
	private BigDecimal totalCost;
	
	
	public List<ContentPrintType> getBooks() {
		return books;
	}
	public void setBooks(List<ContentPrintType> books) {
		this.books = books;
	}
	public BigDecimal getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}
}