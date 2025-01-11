package com.huawei.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartServiceImpl {

	@Autowired
    private PartRepository partRepository;
	
}
