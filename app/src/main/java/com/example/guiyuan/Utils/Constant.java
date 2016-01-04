package com.example.guiyuan.Utils;

public interface Constant {
	public static final String URL ="http://192.168.1.138:7000/";
	public static final String HIS_ADDRESS=URL+"PDA/getCarRecordList/";
	public static final String FOODNAME_ADDRESS=URL+"PDA/getGrainVarietiesList/";
	public static final String FOODTYPE_ADDRESS=URL+"PDA/getGrainPropertyList/";
	public static final String STORE_ADDRESS=URL+"PDA/getCargoNoList/";
	public static final String BASE_ADDRESS="http://192.168.1.138:7000/PDA/";
	public static final String ZHIKU_ADDRESS=URL+"PDA/getCarInfomationByCardId";
	public static final String UPDATE_ADDRESS=URL+"PDA/updateCargoNoAndStatus";
	public static final String ALL_ADDRESS=URL+"PDA/getAllCargoDetail";
	public static final String CONTACT_ADDRESS=URL+"PDA/getSaleInfomation";
	public static final String TERMINATE_ADDRESS=URL+"PDA/terminateTrade";
	public static final String MENWEI_ADDRESS=URL+"PDA/getCarStatusByCardId";
}
