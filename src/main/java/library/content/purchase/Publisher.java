package library.content.purchase;
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
@Embeddable
@XmlRootElement(name="Publisher")
@XmlAccessorType(XmlAccessType.FIELD)
public class Publisher {
	
	@Embedded
	 @AttributeOverrides({
		 	@AttributeOverride(name="houseNumber", column=@Column(name="PUBLISHER_HOUSENUMBER", nullable=false)),
		 	@AttributeOverride(name="street", column=@Column(name="PUBLISHER_STREET", nullable=false)),
		 	@AttributeOverride(name="suburb", column=@Column(name="PUBLISHER_SUBURB", nullable=false)),
		 	@AttributeOverride(name="city", column=@Column(name="PUBLISHER_CITY", nullable=false)),
		 	@AttributeOverride(name="country", column=@Column(name="PUBLISHER_COUNTRY", nullable=false)),
		 	@AttributeOverride(name="zip", column=@Column(name="PUBLISHER_ZIP", nullable=false)),
	    
	 })
	private Address address;
	
	@Column(nullable=false, name="PUBLISHER_NAME")
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
