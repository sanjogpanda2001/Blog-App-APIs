package com.sanjog.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDTO {
//consider these as beans
	private int id;
	
	@NotEmpty
	@Size(min=4,message="name must be of 4 characters")
	private String name;
	@Email(message="provide valid email")
	private String email;
	@NotEmpty
	@Size(min=4,message="min 4 char")
	private String password;
	@NotEmpty
	private String about;
}
