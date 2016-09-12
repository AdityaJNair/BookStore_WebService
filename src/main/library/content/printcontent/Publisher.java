package main.library.content.printcontent;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * @author adijn
 *
 */
@Entity
@XmlRootElement(name="Publisher")
@XmlAccessorType(XmlAccessType.FIELD)
public class Publisher {
	
	@XmlElement(name="Publisher_Address")
	@Embedded
	@Column(nullable=false, name="Address")
	private Address _address;
	
	@XmlElement(name="Publisher_Name")
	@Column(nullable=false, name="Name")
	private String _publisherName;
	
	public Publisher(){
	}
	
	public Publisher(Address address, String publisherName){
		_address=address;
		_publisherName=publisherName;
	}
	
	public Address get_address() {
		return _address;
	}
	public void set_address(Address _address) {
		this._address = _address;
	}
	public String get_publisherName() {
		return _publisherName;
	}
	public void set_publisherName(String _publisherName) {
		this._publisherName = _publisherName;
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
