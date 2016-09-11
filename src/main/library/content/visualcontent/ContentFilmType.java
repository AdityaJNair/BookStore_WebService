package main.library.content.visualcontent;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author adijn
 *
 */
public abstract class ContentFilmType {
	private String _title;
	private Director _director;
	private Date _year;
	private Rating _rating;
	private String _ratingDescription;
	private int _length;
	private String _description;
	private BigDecimal _cost;
	private FilmGenre _genre;

	public ContentFilmType(String title, Director director, Date year, Rating rating, int length, String description){
		_title=title;
		_director=director;
		_year=year;
		_rating=rating;
		_ratingDescription=rating.ratingDescription();
		_length=length;
		_description=description;
	}
	
	public ContentFilmType(){	
	}
	
	
	public String get_title() {
		return _title;
	}
	public void set_title(String _title) {
		this._title = _title;
	}
	public Director get_director() {
		return _director;
	}
	public void set_director(Director _director) {
		this._director = _director;
	}
	public Date get_year() {
		return _year;
	}
	public void set_year(Date _year) {
		this._year = _year;
	}
	public Rating get_rating() {
		return _rating;
	}
	public void set_rating(Rating _rating) {
		this._rating = _rating;
	}
	public String get_ratingDescription() {
		return _ratingDescription;
	}
	public void set_ratingDescription(String _ratingDescription) {
		this._ratingDescription = _ratingDescription;
	}
	public int get_length() {
		return _length;
	}
	public void set_length(int _length) {
		this._length = _length;
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
	public FilmGenre get_genre() {
		return _genre;
	}

	public void set_genre(FilmGenre _genre) {
		this._genre = _genre;
	}
}
