package com.example.school.service;

import com.example.school.exception.ResourceNotFoundException;
import com.example.school.model.DrivingLicense.DrivingLicense;
import com.example.school.repo.DrivingLicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrivingLicenseService {

    @Autowired
    private DrivingLicenseRepository drivingLicenseRepository;

    public DrivingLicense findById(Integer id) {
        return drivingLicenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drivign license with id " + id + " was not found"));
    }
}
