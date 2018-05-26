package com.app.biller.services;

import java.util.ArrayList;
import java.util.List;

import com.app.biller.domain.ILCData;
import com.app.biller.domain.Record;
import com.app.biller.domain.SLAData;

public interface DataValidationService {

	List<ILCData> readILCData(String billCycle, int towerID , int accountId);

	List<ILCData> readCustomILCData(String billCycle, int towerID, int accountId, int key1, String [] val1, int key2, String [] val2, int key3, String [] val3, int key4, String [] val4, int key5, String [] val5);

	List<SLAData> readSLAData(String billCycle, int towerID, int accountId);

	List<SLAData> readCustomSLAData(String billCycle, int towerID, int accountId, int key1, String [] val1, int key2, String [] val2, int key3, String [] val3, int key4, String [] val4, int key5, String [] val5);

	void updateSLAData(String billCycle,  String userID, ArrayList<Record> records);

	void deleteSLAData(String billCycle, List<Integer> seqIDList);

	void createNewSLARecord(String billCycle, String userID, ArrayList<Record> records);	
}
