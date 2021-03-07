package com.example.school.controller;

import com.example.school.dto.DrivingLicense.DrivingLicenseDto;
import com.example.school.mapper.DrivingLicenseMapper;
import com.example.school.mapper.StudentMapper;
import com.example.school.model.DrivingLicense.DrivingLicense;
import com.example.school.service.DrivingLicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DrivingLicenseController {

    @Autowired
    private DrivingLicenseService drivingLicenseService;

    @Autowired
    private DrivingLicenseMapper mapper;

    @Autowired
    private StudentMapper personMapper;

    @GetMapping("/licenseById/{id}")
    public DrivingLicenseDto getLicenseById(@PathVariable("id") Integer licenseId) {
        DrivingLicense drivingLicense = drivingLicenseService.findById(licenseId);
        DrivingLicenseDto dto = mapper.mapToDto(drivingLicense);
        dto.setOwner(personMapper.mapToDto(drivingLicense.getOwner()));
        return dto;
    }
}
