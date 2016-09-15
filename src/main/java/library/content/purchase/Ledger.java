package library.content.purchase;

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
	private Set<Book> books;
	

	public Ledger(){
		books = new HashSet<Book>();
	}
	
	public void addPrintMedia(Book book){
		books.add(book);
	}
	
	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
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
