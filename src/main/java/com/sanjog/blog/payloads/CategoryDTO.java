package com.sanjog.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {
	private int categoryId;
	@NotEmpty
	@Size(min=4)
	private String categoryTitle;
	@NotEmpty
	@Size(min=10)
	private String categoryDesc;

}
