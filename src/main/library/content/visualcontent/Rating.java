/**
 * 
 */
package main.library.content.visualcontent;

/**
 * @author adijn
 *
 */
public enum Rating {
	G , PG, M , R13, R15, R16, R18, RP13, RP16, R;
	
	public String ratingDescription(){
		switch (this){
		case G:
			return "Suitable for General Audiences";
		case PG:
			return "Perental Guidance Recommended for Younger Viewers.";
		case M:
			return "Suitable for Mature Audiences 16 Years and over.";
		case R13:
			return "Restricted to persons 13 Years and over.";
		case R15:
			return "Restricted to persons 15 Years and over.";
		case R16:
			return "Restricted to persons 16 Years and over.";
		case R18:
			return "Restricted to persons 18 Years and over.";
		case RP13:
			return "Restricted to persons 13 Years and over unless accompanied by a Parent/Guardian.";
		case RP16:
			return "Restricted to persons 16 Years and over unless accompanied by a Parent/Guardian.";
		case R:
			return "Restricted.";
		default:
			return "Rating unknown.";
		}
	}
}
