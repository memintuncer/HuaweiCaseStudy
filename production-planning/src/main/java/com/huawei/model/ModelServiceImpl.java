package com.huawei.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl {

	@Autowired
    private ModelRepository modelRepository;
}
