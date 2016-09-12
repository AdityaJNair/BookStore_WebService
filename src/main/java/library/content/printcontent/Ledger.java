package library.content.printcontent;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Ledger")
@XmlAccessorType(XmlAccessType.FIELD)
public class Ledger {
	
	@XmlElementWrapper(name="Books")
	@XmlElement(name="Book")
	private Set<ContentPrintType> books;
	

	public Ledger(){
		books = new HashSet<ContentPrintType>();
	}
	
	public void addPrintMedia(ContentPrintType book){
		books.add(book);
	}
	
	public Set<ContentPrintType> getBooks() {
		return books;
	}

	public void setBooks(Set<ContentPrintType> books) {
		this.books = books;
	}

	/*
	//Do override toString, Equals and hashCode
	@Override
	public String toString() {
	}
	
	@Override
	public boolean equals(Object obj) {
	}
	
	@Override
	public int hashCode() {
	}
	*/
}
