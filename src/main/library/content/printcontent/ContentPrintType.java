package main.library.content.printcontent;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author adijn
 *
 */
public abstract class ContentPrintType {
	private String _title;
	private String _author;
	private Date _year;
	private String _description;
	private BigDecimal _cost;
	private PrintType _printType;
	private String _publisher;
	
	public ContentPrintType(String title, String author, Date year, String description, BigDecimal cost, PrintType printType, String publisher){
		_title=title;
		_author=author;
		_year=year;
		_description=description;
		_cost=cost;
		_printType=printType;
		_publisher=publisher;
	}
	


	public ContentPrintType(){
		
	}
	
	
	public String get_title() {
		return _title;
	}
	public void set_title(String _title) {
		this._title = _title;
	}
	public String get_author() {
		return _author;
	}
	public void set_author(String _author) {
		this._author = _author;
	}
	public Date get_year() {
		return _year;
	}
	public void set_year(Date _year) {
		this._year = _year;
	}
	public String get_description() {
		return _description;
	}
	public void set_description(String _description) {
		this._description = _description;
	}
	public BigDecimal get_cost() {
		return _cost;
	}
	public void set_cost(BigDecimal _cost) {
		this._cost = _cost;
	}
	public String get_publisher() {
		return _publisher;
	}
	public void set_publisher(String _publisher) {
		this._publisher = _publisher;
	}
	public PrintType get_printType() {
		return _printType;
	}
	public void set_printType(PrintType _printType) {
		this._printType = _printType;
	}
}