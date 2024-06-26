package com.sanjog.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
	
	private List<PostDTO> content;
	private int pageSize;
	private int pageNumber;
	private long totalEelements;
	private int totalPages;
	private boolean lastPage;

}
