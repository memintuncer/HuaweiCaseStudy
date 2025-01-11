package com.huawei.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl {
	
	@Autowired
	private LogRepository logRepository;
}
