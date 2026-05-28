package org.example.model;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    private UUID uuid;

    private String name;

    private String phone;
}

