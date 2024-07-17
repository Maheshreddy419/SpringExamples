package com.codingshuttle.springbootwebtutorial.services;

import com.codingshuttle.springbootwebtutorial.dto.EmployeeDTO;
import com.codingshuttle.springbootwebtutorial.entity.EmployeeEntity;
import com.codingshuttle.springbootwebtutorial.exceptions.ResourceNotFoundException;
import com.codingshuttle.springbootwebtutorial.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository,
                           ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<EmployeeDTO> findById(Long id) {
        Optional<EmployeeEntity> employee = employeeRepository.findById(id);
        return employee.map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class));
    }

    public List<EmployeeDTO> findAll() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO save(EmployeeDTO inputEmployee) {
        EmployeeEntity convertToEmployeeEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(convertToEmployeeEntity);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(EmployeeDTO employeeDTO, Long id) {
        isExistsByEmployeeId(id);
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(id);
        EmployeeEntity employee = employeeRepository.save(employeeEntity);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public void isExistsByEmployeeId(Long id) {
        boolean exists = employeeRepository.existsById(id);
        if (!exists) throw new ResourceNotFoundException("Employee Not Found With EmployeeId:" + id);
    }

    public boolean deleteEmployeeById(Long id) {
        isExistsByEmployeeId(id);
        employeeRepository.deleteById(id);
        return true;
    }

    public EmployeeDTO updatePartiallyByEmployeeById(Map<String, Object> updates, Long id) {
        isExistsByEmployeeId(id);
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        updates.forEach((field, value) -> {
            Field filedToUpdate = ReflectionUtils.findRequiredField(EmployeeEntity.class, field);
            filedToUpdate.setAccessible(true);
            ReflectionUtils.setField(filedToUpdate, employeeEntity, value);
        });
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }
}
