package com.ibrahim.drop_shop.services.user.DTO;


import lombok.Data;

@Data
public class CreateUserDto {
   private String firstName;
   private String lastName;
   private String email;
   private String password;
}
