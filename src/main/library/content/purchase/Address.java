/**
 * 
 */
package main.library.content.purchase;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
/**
 * @author adijn
 *
 */
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class Address {
	
	@Column(nullable=false, name="House Number")
	private String _houseNumber;
	
	@Column(nullable=false, name="Street Name")
	private String _street;
	
	@Column(nullable=false, name="Suburb")
	private String _suburb;
	
	@Column(nullable=false, name="City")
	private String _city;
	
	@Column(nullable=false, name="Country")
	private String _country;
	
	@Column(nullable=false, name="Zip Code")
	private String _zip;
	
	
	public Address(String houseNumber, String street, String suburb, String city, String country, String zip) {
		_houseNumber=houseNumber;
		_street=street;
		_suburb=suburb;
		_city=city;
		_country=country;
		_zip=zip;
	}
	
	public Address(){
		
	}
	
	public String get_houseNumber() {
		return _houseNumber;
	}
	public void set_houseNumber(String _houseNumber) {
		this._houseNumber = _houseNumber;
	}
	public String get_street() {
		return _street;
	}
	public void set_street(String _street) {
		this._street = _street;
	}
	public String get_suburb() {
		return _suburb;
	}
	public void set_suburb(String _suburb) {
		this._suburb = _suburb;
	}
	public String get_city() {
		return _city;
	}
	public void set_city(String _city) {
		this._city = _city;
	}
	public String get_country() {
		return _country;
	}
	public void set_country(String _country) {
		this._country = _country;
	}
	public String get_zip() {
		return _zip;
	}
	public void set_zip(String _zip) {
		this._zip = _zip;
	}

}
