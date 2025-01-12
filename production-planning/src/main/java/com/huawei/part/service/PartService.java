package com.huawei.part.service;

import java.util.List;

import com.huawei.part.entity.Part;

public interface PartService {
    Part createPart(Part part);
    Part getPartById(Long id);
    List<Part> getAllParts();
}
