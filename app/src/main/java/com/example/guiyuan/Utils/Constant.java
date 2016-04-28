package com.example.guiyuan.Utils;

public interface Constant {
	String URL ="http://122.224.207.26:7000";
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
	//192.168.8.129

	//抽样称重界面接口信息
	String ITEM_URL = URL+"/PDA/getDetailByRfidCode/";
	String WEIGHT_URL = URL+"/PDA/getWeight/";
	String WEIGHT_SINGLE = URL+"/PDA/postSingleWeightRecord/";
	String WEIGHT_END = URL+"/PDA/postALLWeightRecord/";
	String Weight_PC = URL+"/PDA/getAllWeightRecord/";
}
