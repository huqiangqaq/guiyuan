package com.example.guiyuan.entity;

public class HistoryVertical {
	private String carnum;
	private String storenum;
	private String time;
	private String foodvari;
	private String foodtype;
	private String level;
	private String waterper;
	private String rongweight;
	private String zazhi;
	private String maozhong;
	private String storeman;
	private String qianyangman;

	public HistoryVertical() {
	}

	public HistoryVertical(String carnum, String storenum, String time, String foodvari, String foodtype, String level, String waterper, String rongweight, String zazhi, String maozhong, String storeman, String qianyangman) {
		this.carnum = carnum;
		this.storenum = storenum;
		this.time = time;
		this.foodvari = foodvari;
		this.foodtype = foodtype;
		this.level = level;
		this.waterper = waterper;
		this.rongweight = rongweight;
		this.zazhi = zazhi;
		this.maozhong = maozhong;
		this.storeman = storeman;
		this.qianyangman = qianyangman;
	}

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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getWaterper() {
		return waterper;
	}

	public void setWaterper(String waterper) {
		this.waterper = waterper;
	}

	public String getRongweight() {
		return rongweight;
	}

	public void setRongweight(String rongweight) {
		this.rongweight = rongweight;
	}

	public String getZazhi() {
		return zazhi;
	}

	public void setZazhi(String zazhi) {
		this.zazhi = zazhi;
	}

	public String getMaozhong() {
		return maozhong;
	}

	public void setMaozhong(String maozhong) {
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

	@Override
	public String toString() {
		return "HistoryVertical{" +
				"carnum='" + carnum + '\'' +
				", storenum='" + storenum + '\'' +
				", time='" + time + '\'' +
				", foodvari='" + foodvari + '\'' +
				", foodtype='" + foodtype + '\'' +
				", level='" + level + '\'' +
				", waterper=" + waterper +
				", rongweight=" + rongweight +
				", zazhi=" + zazhi +
				", maozhong=" + maozhong +
				", storeman='" + storeman + '\'' +
				", qianyangman='" + qianyangman + '\'' +
				'}';
	}
}
