package com.codingshuttle.springbootwebtutorial.controller;

import com.codingshuttle.springbootwebtutorial.dto.EmployeeDTO;
import com.codingshuttle.springbootwebtutorial.entity.EmployeeEntity;
import com.codingshuttle.springbootwebtutorial.services.EmployeeService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private final EmployeeService employeeService;
  private final ModelMapper modelMapper;

    public EmployeeController(EmployeeService employeeService,ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeDTO inputEmployee){
        EmployeeDTO savedEmployee = employeeService.save(inputEmployee);
        return new ResponseEntity<>(savedEmployee,HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") Long Id){
            Optional<EmployeeDTO> employeeDTO  =employeeService.findById(Id);
              return employeeDTO
                      .map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false,name = "inputAge") Integer age,
                                                             @RequestParam(required = false) String sortBy){
        List<EmployeeDTO> listOfEmployees =  employeeService.findAll();
        return new ResponseEntity<>(listOfEmployees,HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody EmployeeDTO employee,@PathVariable Long id){

            EmployeeDTO employeeDTO =  employeeService.updateEmployeeById(employee,id);

            return ResponseEntity.ok(employeeDTO);
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable(name = "employeeId") Long id){
        boolean gotDeleted = employeeService.deleteEmployeeById(id);
        if (gotDeleted) return ResponseEntity.ok(gotDeleted);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartiallyByEmployeeById(@RequestBody Map<String, Object> updates,@PathVariable(name = "employeeId") Long id){
         EmployeeDTO employeeDTO = employeeService.updatePartiallyByEmployeeById(updates,id);
         if (employeeDTO ==null) return ResponseEntity.notFound().build();
         return ResponseEntity.ok(employeeDTO);
    }
}
