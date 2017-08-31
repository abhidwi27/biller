package com.app.biller.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SystemAdminServiceImpl implements SystemAdminService {

	private static final Logger logger = LoggerFactory.getLogger(SystemAdminServiceImpl.class);

	@Override
	public String unLockData(String billCycle) {
		return null;
	}	
}
