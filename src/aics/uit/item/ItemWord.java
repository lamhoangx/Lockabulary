package aics.uit.item;

import java.io.Serializable;

public class ItemWord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private int id;
	private String word;
	private String mean;
	private String type;
	private String pronu;
	private int pass;

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getMean() {
		return mean;
	}

	public void setMean(String mean) {
		this.mean = mean;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPronu() {
		return pronu;
	}

	public void setPronu(String pronu) {
		this.pronu = pronu;
	}

	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}

}
