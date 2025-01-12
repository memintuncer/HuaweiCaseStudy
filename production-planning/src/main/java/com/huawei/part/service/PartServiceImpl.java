package com.huawei.part.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huawei.part.entity.Part;
import com.huawei.part.repository.PartRepository;

@Service
public class PartServiceImpl implements PartService {

    @Autowired
    private PartRepository partRepository;

    @Override
    public Part createPart(Part part) {
        return partRepository.save(part);
    }
}
