package com.codingshuttle.springbootwebtutorial.services;

import com.codingshuttle.springbootwebtutorial.dto.DepartmentDTO;
import com.codingshuttle.springbootwebtutorial.entity.DepartmentEntity;
import com.codingshuttle.springbootwebtutorial.exceptions.ResourceNotFoundException;
import com.codingshuttle.springbootwebtutorial.repositories.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }


    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        DepartmentEntity departmentEntity = modelMapper.map(departmentDTO, DepartmentEntity.class);
        DepartmentEntity department =departmentRepository.save(departmentEntity);
        return modelMapper.map(department,DepartmentDTO.class);
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
         return departmentEntities.stream()
                .map(departmentEntity -> modelMapper.map(departmentEntity,DepartmentDTO.class))
                   .toList();
    }

    public Optional<DepartmentDTO> findByDepartmentId(Long departmentId) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);
       return departmentEntity.map(departmentEntity1 -> modelMapper.map(departmentEntity1,DepartmentDTO.class));
    }

    public DepartmentDTO updateDepartment(Long departmentId, DepartmentDTO inputRequest) {
           isExisted(departmentId);
           DepartmentEntity departmentEntity = modelMapper.map(inputRequest, DepartmentEntity.class);
           departmentEntity.setId(departmentId);
           DepartmentEntity updatedDepartmentEntity = departmentRepository.save(departmentEntity);
           return modelMapper.map(updatedDepartmentEntity,DepartmentDTO.class);
    }

    public boolean deleteById(Long departmentId) {
        isExisted(departmentId);
        departmentRepository.deleteById(departmentId);
        return true;
    }

    private boolean isExisted(Long departmentId) {
        boolean isExist = departmentRepository.existsById(departmentId);
        if (!isExist) throw new ResourceNotFoundException("Department with id " + departmentId + " not found");
        return true;
    }

    public DepartmentDTO updatePartiallyDepartment(Map<String, Object> updates, Long departmentId) {
        isExisted(departmentId);
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).orElseThrow(null);
        updates.forEach((field, value) -> {
          Field fieldToUpdated = ReflectionUtils.findRequiredField(DepartmentEntity.class, field);
           fieldToUpdated.setAccessible(true);
        });
        return modelMapper.map(departmentRepository.save(departmentEntity), DepartmentDTO.class);
    }
}
