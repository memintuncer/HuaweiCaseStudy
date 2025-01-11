package com.huawei.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PartController {
	@Autowired
	private PartService partService;
	
}
