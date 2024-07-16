package com.codingshuttle.springbootwebtutorial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;

    @NotBlank(message = "The Name of the employee cannot be blank")
    @Size(min = 3, max = 10, message = "Number of Characters in the name should be in the Range:[3,10]")
    private String name;

    @NotBlank(message ="The Email of the employee cannot be blank")
    @Email(message = "Email should be a valid email")
    private String email;

    @NotNull(message = "Age of the Employee Cannot be blank")
    @Max(value = 80, message = "Age of Employee Cannot be greater than 80")
    @Min(value = 18, message = "Age of the Employee cannot be less tha 18")
    private Integer age;

    @NotBlank(message = "The roll of the employee cannot be blank")
    @Pattern(regexp = "^(ADMIN|USER)$", message = "Role of employee either be ADMIN or USER")
    private String role;

    @NotNull(message = "That the salary of the employee cannot be null")
    @Positive(message = "That the salary of the employee should be positive")
    @Digits(integer = 6, fraction = 3, message = "The salary can be in the form of xcxcxc.ewe" )
    @DecimalMin(value = "100.22")
    @DecimalMax(value = "100000.99")
    private Double salary;

    @PastOrPresent(message = "The employee date of joining either path or present date")
    private LocalDate dateOfJoining;

    @AssertTrue(message = "Employee Should be active")
    @JsonProperty("isActive")
    private Boolean isActive;
}
