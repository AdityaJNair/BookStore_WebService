package main.library.content.purchase;

import java.util.ArrayList;
import java.util.List;

import main.library.content.printcontent.ContentPrintType;

public class Library {
	
	private List<ContentPrintType> _books;
	
	public Library(){
		_books = new ArrayList<ContentPrintType>();
	}
	
	public void addPrintMedia(ContentPrintType book){
		_books.add(book);
	}

}
