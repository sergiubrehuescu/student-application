package com.example.school.mapper;

import com.example.school.dto.DrivingLicense.DrivingLicenseDto;
import com.example.school.model.DrivingLicense.DrivingLicense;
import org.springframework.stereotype.Component;

@Component
public class DrivingLicenseMapper {

    public DrivingLicenseDto mapToDto(DrivingLicense license) {
        return new DrivingLicenseDto(license.getId(), license.getReleasedAt(), license.getExpireDate(), license.getCategory(), license.getImage(), license.getReleasedBy());
    }

}