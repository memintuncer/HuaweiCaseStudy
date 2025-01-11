package com.huawei.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService{
	@Autowired
    private ProjectRepository projectRepository;
}
