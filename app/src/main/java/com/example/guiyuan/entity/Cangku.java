package com.example.guiyuan.entity;

public class Cangku {
	private String storenum;
	private String foodname;
	private String level;
	private String water;
	private String weight;
	public String getStorenum() {
		return storenum;
	}
	public void setStorenum(String storenum) {
		this.storenum = storenum;
	}
	public String getFoodname() {
		return foodname;
	}
	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getWater() {
		return water;
	}
	public void setWater(String water) {
		this.water = water;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public Cangku(String storenum, String foodname, String level, String water,
			String weight) {
		super();
		this.storenum = storenum;
		this.foodname = foodname;
		this.level = level;
		this.water = water;
		this.weight = weight;
	}
}
