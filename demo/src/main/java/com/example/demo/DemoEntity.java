package com.example.demo;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DemoEntity {
	private Integer pkid;
	@NotBlank(message = "input name")
	private String name;
	@NotNull(message = "input wins")
	private Integer wins;
	private Integer best;
	private Float size;
}
