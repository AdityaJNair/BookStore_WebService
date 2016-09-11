/**
 * 
 */
package main.library.content.purchase;

import javax.xml.bind.annotation.*;
/**
 * @author adijn
 *
 */
public class Address {
	private String _houseNumber;
	private String _street;
	private String _suburb;
	private String _city;
	private String _country;
	private String _zip;
	
	
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
