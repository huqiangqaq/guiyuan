package com.example.guiyuan.entity;

public class HistoryVertical {
	private String carnum;
	private String storenum;
	private String time;
	private int level;
	private String foodvari;
	private String foodtype;
	private float waterper;
	private float rongweight;
	private float zazhi;
	private float maozhong;
	private String storeman;
	private String qianyangman;
	public String getCarnum() {
		return carnum;
	}
	public void setCarnum(String carnum) {
		this.carnum = carnum;
	}
	public String getStorenum() {
		return storenum;
	}
	public void setStorenum(String storenum) {
		this.storenum = storenum;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getFoodvari() {
		return foodvari;
	}
	public void setFoodvari(String foodvari) {
		this.foodvari = foodvari;
	}
	public String getFoodtype() {
		return foodtype;
	}
	public void setFoodtype(String foodtype) {
		this.foodtype = foodtype;
	}
	public float getWaterper() {
		return waterper;
	}
	public void setWaterper(float waterper) {
		this.waterper = waterper;
	}
	public float getRongweight() {
		return rongweight;
	}
	public void setRongweight(float rongweight) {
		this.rongweight = rongweight;
	}
	public float getZazhi() {
		return zazhi;
	}
	public void setZazhi(float zazhi) {
		this.zazhi = zazhi;
	}
	public float getMaozhong() {
		return maozhong;
	}
	public void setMaozhong(float maozhong) {
		this.maozhong = maozhong;
	}
	public String getStoreman() {
		return storeman;
	}
	public void setStoreman(String storeman) {
		this.storeman = storeman;
	}
	public String getQianyangman() {
		return qianyangman;
	}
	public void setQianyangman(String qianyangman) {
		this.qianyangman = qianyangman;
	}
	public HistoryVertical(String carnum, String storenum, String time,
			int level, String foodvari, String foodtype, float waterper,
			float rongweight, float zazhi, float maozhong, String storeman,
			String qianyangman) {
		super();
		this.carnum = carnum;
		this.storenum = storenum;
		this.time = time;
		this.level = level;
		this.foodvari = foodvari;
		this.foodtype = foodtype;
		this.waterper = waterper;
		this.rongweight = rongweight;
		this.zazhi = zazhi;
		this.maozhong = maozhong;
		this.storeman = storeman;
		this.qianyangman = qianyangman;
	}
	public HistoryVertical() {
		super();
	}
	
	
	}
