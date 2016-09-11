package main.library.content.purchase;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import main.library.content.printcontent.ContentPrintType;

@XmlRootElement(name="Library")
@XmlAccessorType(XmlAccessType.FIELD)
public class Library {
	
	private Set<ContentPrintType> _books;
	
	public Library(){
		_books = new HashSet<ContentPrintType>();
	}
	
	public void addPrintMedia(ContentPrintType book){
		_books.add(book);
	}

}
