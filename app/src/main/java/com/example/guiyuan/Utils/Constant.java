package com.example.guiyuan.Utils;

public interface Constant {
	String URL ="http://192.168.1.3:7000";
	String HIS_ADDRESS=URL+"/PDA/getCarRecordList/";
	String FOODNAME_ADDRESS=URL+"/PDA/getGrainVarietiesList/";
	String FOODTYPE_ADDRESS=URL+"/PDA/getGrainPropertyList/";
	String STORE_ADDRESS=URL+"/PDA/getCargoNoList/";
	String HGT_ARDRESS = URL+"/PDA/getHgtNoList/";
	String BASE_ADDRESS="http://192.168.1.51:7000";
	String ZHIKU_ADDRESS=URL+"/PDA/getCarInfomationByCardId";
	String UPDATE_ADDRESS=URL+"/PDA/updateCargoNoAndStatus";
	String ALL_ADDRESS=URL+"/PDA/getAllCargoDetail";
	String CONTACT_ADDRESS=URL+"/PDA/getSaleInfomation";
	String TERMINATE_ADDRESS=URL+"/PDA/terminateTrade";
	String MENWEI_ADDRESS=URL+"/PDA/getCarStatusByCardId";
	String RECYCLE_CARD = URL+"/PDA/recycleCard";
	Boolean DEBUG_WITH_NO_NFC_DEVICE =false;
	Boolean IS_CANGKUNUMBER_OPPWITH_CANGKUID=false;
	//122.224.207.26

	//抽样称重界面接口信息
	public static String ITEM_URL = "http://122.224.207.26:7000/PDA/getDetailByRfidCode/";
	public static String WEIGHT_URL = "http://122.224.207.26:7000/PDA/getWeight/";
	public static String WEIGHT_SINGLE = "http://122.224.207.26:7000/PDA/postSingleWeightRecord/";
	public static String WEIGHT_END = "http://122.224.207.26:7000/PDA/postALLWeightRecord/";
}
