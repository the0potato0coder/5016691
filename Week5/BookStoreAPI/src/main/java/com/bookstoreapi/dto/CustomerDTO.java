package com.bookstoreapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Email cannot be null")
    @Size(min = 5, max = 100, message = "Email must be between 5 and 100 characters")
    private String email;

    @NotNull(message = "Address cannot be null")
    @Size(min = 10, max = 200, message = "Address must be between 10 and 200 characters")
    private String address;

//    @Min(value = 18, message = "Age must be at least 18")
//    private Integer age;  // Example of a field that must be at least 18
}