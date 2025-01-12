package com.huawei.part.service;

import java.util.List;

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
    
    @Override
    public Part getPartById(Long id) {
        return partRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Part> getAllParts() {
        return partRepository.findAll();
    }
}
