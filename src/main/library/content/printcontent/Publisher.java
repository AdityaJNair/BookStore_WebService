package main.library.content.printcontent;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import main.library.content.purchase.Address;
/**
 * @author adijn
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Publisher {
	private Address _address;
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
}
