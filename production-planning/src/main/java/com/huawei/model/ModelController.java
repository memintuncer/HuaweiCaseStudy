package com.huawei.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelController {

	@Autowired 
	private ModelService modelService;
}
