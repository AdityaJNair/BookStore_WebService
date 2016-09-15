package library.content.purchase;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Publisher))
            return false;
        if (obj == this)
            return true;

        Publisher rhs = (Publisher) obj;
        return new EqualsBuilder().
            append(publisherName, rhs.publisherName).
            append(address, rhs.address).
            isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(publisherName).
	            append(address.toString()).
	            toHashCode();
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(publisherName+" ");
		buffer.append(address.toString());
		return buffer.toString();
	}
}
