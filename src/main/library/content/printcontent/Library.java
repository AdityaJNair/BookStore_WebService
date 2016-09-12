package main.library.content.printcontent;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Library")
@XmlAccessorType(XmlAccessType.FIELD)
public class Library {
	
	@XmlElementWrapper(name="Library_Books")
	@XmlElement(name="Books")
	private Set<ContentPrintType> _books;
	
	public Library(){
		_books = new HashSet<ContentPrintType>();
	}
	
	public void addPrintMedia(ContentPrintType book){
		_books.add(book);
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
