package com.codingshuttle.springbootwebtutorial.controller;

import com.codingshuttle.springbootwebtutorial.dto.DepartmentDTO;
import com.codingshuttle.springbootwebtutorial.exceptions.ResourceNotFoundException;
import com.codingshuttle.springbootwebtutorial.repositories.DepartmentRepository;
import com.codingshuttle.springbootwebtutorial.services.DepartmentService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final ModelMapper modelMapper;
    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentService departmentService, ModelMapper modelMapper, DepartmentRepository departmentRepository) {
        this.departmentService = departmentService;
        this.modelMapper = modelMapper;
        this.departmentRepository = departmentRepository;
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody @Valid DepartmentDTO inputRequest) {
        DepartmentDTO departmentDTO = departmentService.saveDepartment(inputRequest);
        return new ResponseEntity<>(departmentDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> departmentDTOList = departmentService.getAllDepartments();
        return new ResponseEntity<>(departmentDTOList, HttpStatus.OK);
    }

    @GetMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long departmentId) {
         Optional<DepartmentDTO> departmentDTO = departmentService.findByDepartmentId(departmentId);
         return departmentDTO.map(ResponseEntity::ok)
                 .orElseThrow(()-> new ResourceNotFoundException("Department with id: " + departmentId + " not found"));
    }

    @PutMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long departmentId, @RequestBody DepartmentDTO inputRequest) {
         DepartmentDTO departmentDTO =  departmentService.updateDepartment(departmentId,inputRequest);
         return ResponseEntity.ok(departmentDTO);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Boolean> deleteDepartment(@PathVariable Long departmentId) {
        boolean getDeleted = departmentService.deleteById(departmentId);
        if (getDeleted) return ResponseEntity.ok(getDeleted);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> updatePartiallyDepartment(@RequestBody Map<String,Object> updates,@PathVariable Long departmentId) {
       DepartmentDTO departmentDTO =  departmentService.updatePartiallyDepartment(updates,departmentId);
       if (departmentDTO != null) return ResponseEntity.ok(departmentDTO);
       return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
