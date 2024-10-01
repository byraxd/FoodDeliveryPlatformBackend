package com.example.demo.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "Token")
public class Token {

    @Transient
    public static final String SEQUENCE_NAME = "token_sequence";

    @Id
    private Long id;

    private String token;
    private boolean loggedOut;
    private User user;

}
