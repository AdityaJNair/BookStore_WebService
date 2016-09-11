/**
 * 
 */
package main.library.content.printcontent;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author adijn
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Bundle {
	private List<ContentPrintType> _bundleList;
	private int _bundleAmount;
	
	public Bundle(List<ContentPrintType> bundleList, int bundleAmount){
		_bundleList = bundleList;
		_bundleAmount = bundleAmount;
	}
	
	
	public List<ContentPrintType> get_bundleList() {
		return _bundleList;
	}
	public void set_bundleList(List<ContentPrintType> _bundleList) {
		this._bundleList = _bundleList;
	}
	public int get_bundleAmount() {
		return _bundleAmount;
	}
	public void set_bundleAmount(int _bundleAmount) {
		this._bundleAmount = _bundleAmount;
	}
}
