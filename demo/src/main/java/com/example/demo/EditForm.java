package com.example.demo;

import lombok.Data;
import javax.validation.Valid;
import com.example.demo.DemoEntity;

@Data
public class EditForm {

	private String path;

@Valid
	private DemoEntity entity;
//	private Integer pkid;
//	private String name;
//	private Integer wins;
//	private Integer best;
//	private Float size;
}
