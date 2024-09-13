package com.example.demo.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "Pizza")
public class Pizza {

    @Transient
    public static final String SEQUENCE_NAME = "pizza_sequence";

    @Id
    private Long id;
    private String name;
    private String description;
    private Long price;
    private Boolean isAvailable;

}
