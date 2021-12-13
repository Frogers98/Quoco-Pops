package service.core;

import java.io.Serializable;

/**
 * Class to store the recipts returned by the borrow service
 * 
 * @author Hassan
 *
 */

public class Recipt implements Serializable{
		
	private String library;
	private String reference;
	private double price;

	public Recipt(String library, String reference, double price) {
		this.library = library;
		this.reference = reference;
		this.price = price;
		
	}
	public Recipt(){
		
	}
	public String getlibrary() {
		return library;
	}
	public void setlibrary(String library) {
		this.library = library;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
      public void add(Recipt Recipt) {
      }

}
