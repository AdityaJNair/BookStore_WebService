/**
 * 
 */
package library.content.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
/**
 * Embeddable Address class, stores the address information.
 * @author adijn
 *
 */
@Embeddable
@XmlRootElement(name="Address")
@XmlAccessorType(XmlAccessType.FIELD)
public class Address {
	
	//house number 
	@Column(nullable=false)
	private String houseNumber;
	
	//street name
	@Column(nullable=false)
	private String street;
	
	//suburb 
	@Column(nullable=false)
	private String suburb;
	
	//city
	@Column(nullable=false)
	private String city;
	
	@Column(nullable=false)
	private String country;
	
	//zip code
	@Column(nullable=false)
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
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(houseNumber).
	            append(street).
	            append(suburb).
	            append(city).
	            append(country).
	            append(zip).
	            toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Address))
            return false;
        if (obj == this)
            return true;
        Address ad = (Address) obj;
        
        return new EqualsBuilder().append(houseNumber, ad.houseNumber).append(street,ad.street).
        		append(suburb,ad.suburb).append(city,ad.city).append(country,ad.country).append(zip,ad.zip).isEquals();
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(houseNumber+ " ");
		buffer.append(street+ " ");
		buffer.append(suburb+ " ");
		buffer.append(city+ " ");
		buffer.append(country+ " ");
		buffer.append(zip);
		return buffer.toString();
	}
}
