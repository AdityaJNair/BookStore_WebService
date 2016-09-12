package library.content.printcontent;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * @author adijn
 *
 */
@Entity
@XmlRootElement(name="Publisher")
@XmlAccessorType(XmlAccessType.FIELD)
public class Publisher {
	
	@XmlID
	@XmlAttribute(name="xml-id-publisher")
	private String _xml_id;

	@Id
	@GeneratedValue(generator="ID-GENERATOR")
	private long publisher_id;
	
	@Embedded
	@Column(nullable=false, name="Address")
	private Address address;
	
	@Column(nullable=false, name="Name")
	private String publisherName;
	
	public Publisher(){
	}
	
	public Publisher(Address address, String publisherName){
		this.address=address;
		this.publisherName=publisherName;
	}
	
	public Address get_address() {
		return address;
	}
	public void set_address(Address _address) {
		this.address = _address;
	}
	public String get_publisherName() {
		return publisherName;
	}
	public void set_publisherName(String _publisherName) {
		this.publisherName = _publisherName;
	}
	public long get_id() {
		return publisher_id;
	}

	public void set_id(long _id) {
		this.publisher_id = _id;
		this._xml_id = getClass().getName()+_id;
		
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
