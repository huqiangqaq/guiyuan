package com.example.guiyuan.Utils;

public class SpinnerDropDownList {
	
	private int id;
	private String value;
	private String label;
	
	public SpinnerDropDownList(int id,String value,String label) {
		this.setId(id);
		this.value=value;
		this.label=label;
	}
	
	public SpinnerDropDownList(int id,String[] arrayDropDownList) {
		this.setId(id);
		this.value=arrayDropDownList[0];
		this.label=arrayDropDownList[1];
	}
	
	@Override
	public String toString() {
		return this.label;
	}
	public String getValue() {
		return this.value;
	}
	
	public SpinnerDropDownList(int id,int type,String[] arrayDropDownList) {
		this.setId(id);
		if(type!=2&&type!=1) {
			this.value=arrayDropDownList[1];
			this.label=arrayDropDownList[0];
		}
		else {
			this.value=arrayDropDownList[0];
			this.label=arrayDropDownList[1];
		}
	}

	public int getId() {
		return this.id;
	}

	private void setId(int id) {
		this.id = id;
	}

}
