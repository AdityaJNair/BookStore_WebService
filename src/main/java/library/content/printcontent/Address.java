/**
 * 
 */
package library.content.printcontent;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
/**
 * @author adijn
 *
 */
@Embeddable
@XmlRootElement(name="Address")
@XmlAccessorType(XmlAccessType.FIELD)
public class Address {
	
	@Column(nullable=false, name="House_Number")
	private String houseNumber;
	
	@Column(nullable=false, name="Street_Name")
	private String street;
	
	@Column(nullable=false, name="Suburb")
	private String suburb;
	
	@Column(nullable=false, name="City")
	private String city;
	
	@Column(nullable=false, name="Country")
	private String country;
	
	@Column(nullable=false, name="Zip_Code")
	private String zip;
	
	
	public Address(String houseNumber, String street, String suburb, String city, String country, String zip) {
		this.houseNumber=houseNumber;
		this.street=street;
		this.suburb=suburb;
		this.city=city;
		this.country=country;
		this.zip=zip;
	}
	
	public Address(){
		
	}
	
	public String get_houseNumber() {
		return houseNumber;
	}
	public void set_houseNumber(String _houseNumber) {
		this.houseNumber = _houseNumber;
	}
	public String get_street() {
		return street;
	}
	public void set_street(String _street) {
		this.street = _street;
	}
	public String get_suburb() {
		return suburb;
	}
	public void set_suburb(String _suburb) {
		this.suburb = _suburb;
	}
	public String get_city() {
		return city;
	}
	public void set_city(String _city) {
		this.city = _city;
	}
	public String get_country() {
		return country;
	}
	public void set_country(String _country) {
		this.country = _country;
	}
	public String get_zip() {
		return zip;
	}
	public void set_zip(String _zip) {
		this.zip = _zip;
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
