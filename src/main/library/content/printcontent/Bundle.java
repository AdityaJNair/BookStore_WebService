/**
 * 
 */
package main.library.content.printcontent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

/**
 * @author adijn
 *
 */
@Entity
@XmlRootElement(name="Bundle")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bundle {
	
	@Id
	@GeneratedValue(generator="ID-GENERATOR")
	private Long _bundleId;
	
	@XmlElement(name="books")
	@ElementCollection
	@CollectionTable(name="Bundle")
	@Column(name = "BundleList")
	@org.hibernate.annotations.CollectionId(
			columns = @Column( name = "Bundle_ID" ),
			type = @org.hibernate.annotations.Type( type = "long"),
			generator = "ID_GENERATOR")
	private Collection<ContentPrintType> _bundleList;
	
	@XmlElement(name="Cost")
	@Column(name="Bundle Cost")
	private int _bundleAmount;
	
	public Bundle(int bundleAmount){
		_bundleList = new ArrayList<ContentPrintType>();
		_bundleAmount = bundleAmount;
	}
	
	public Bundle(){
	}
	
	
	public Collection<ContentPrintType> get_bundleList() {
		return _bundleList;
	}
	public void set_bundleList(Collection<ContentPrintType> _bundleList) {
		this._bundleList = _bundleList;
	}
	public int get_bundleAmount() {
		return _bundleAmount;
	}
	public void set_bundleAmount(int _bundleAmount) {
		this._bundleAmount = _bundleAmount;
	}
	public Long get_bundleId() {
		return _bundleId;
	}
	public void set_bundleId(Long _bundleId) {
		this._bundleId = _bundleId;
	}
	
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
}
