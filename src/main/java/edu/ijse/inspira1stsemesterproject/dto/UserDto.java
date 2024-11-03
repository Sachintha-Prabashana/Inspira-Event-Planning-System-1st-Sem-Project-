package edu.ijse.inspira1stsemesterproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserDto {
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}
