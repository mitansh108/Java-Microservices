package com.energy_expense_tracker.device_service.service;

import com.energy_expense_tracker.device_service.dto.DeviceDto;
import com.energy_expense_tracker.device_service.entity.Device;
import com.energy_expense_tracker.device_service.exception.DeviceNotFoundException;
import com.energy_expense_tracker.device_service.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository){
        this.deviceRepository = deviceRepository;
    }

    public DeviceDto createDevice(DeviceDto input){
        log.info("Creating device: {}", input.getName());
        Device device = Device.builder()
                .name(input.getName())
                .type(input.getType())
                .location(input.getLocation())
                .userId(input.getUserId())
                .build();

        Device saved = deviceRepository.save(device);
        log.info("Device created successfully with id: {}", saved.getId());
        return mapToDto(saved);
    }

    public DeviceDto getDeviceById(Long id){
        log.info("Fetching device with id: {}", id);
        Device device = deviceRepository.findById(id)
                .orElseThrow(() ->
                        new DeviceNotFoundException("Device Not Found with id " + id));
        return mapToDto(device);
    }

    public DeviceDto updateDevice(Long id, DeviceDto input){
        log.info("Updating device with id: {}", id);
        Device device = deviceRepository.findById(id)
                .orElseThrow(() ->
                        new DeviceNotFoundException("Device Not Found with id " + id));

        device.setName(input.getName());
        device.setType(input.getType());
        device.setLocation(input.getLocation());
        device.setUserId(input.getUserId());

        Device saved = deviceRepository.save(device);
        log.info("Device updated successfully with id: {}", saved.getId());
        return mapToDto(saved);
    }

    public void deleteDevice(Long id){
        log.info("Deleting device with id: {}", id);
        Device device = deviceRepository.findById(id)
                .orElseThrow(() ->
                        new DeviceNotFoundException("Device Not Found with id , Nakhya vagar kemnu male bhai device henh? madarchod! " + id));
        deviceRepository.delete(device);
        log.info("Device deleted successfully with id: {}", id);
    }

    private DeviceDto mapToDto(Device device){
        return DeviceDto.builder()
                .id(device.getId())
                .name(device.getName())
                .type(device.getType())
                .location(device.getLocation())
                .userId(device.getUserId())
                .build();
    }
}
