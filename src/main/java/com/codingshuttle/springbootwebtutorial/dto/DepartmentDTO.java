package com.codingshuttle.springbootwebtutorial.dto;

import com.codingshuttle.springbootwebtutorial.annotation.PrimeNumberAnnotation;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    private Long id;
    @NotBlank(message = "Tile should not empty or Null")
    @Size(min = 3, max = 10, message = "Number of Characters in the title should be in the Range:[3,10]")
    private String title;
    private Boolean isActive;
    @FutureOrPresent(message = "Created Date should be present or future date")
    private LocalDate createdAt;
    @PrimeNumberAnnotation(message = "Quantity Number should be prime Number")
    private Integer quantity;
}
