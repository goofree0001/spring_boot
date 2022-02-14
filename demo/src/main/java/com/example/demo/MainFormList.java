package com.example.demo;

import lombok.Data;
import com.example.demo.DemoEntity;

@Data
public class MainFormList {

	public Integer[] selectCheckboxs;
	public Integer[] getSelectCheckboxs() {
		return selectCheckboxs;
	}
	public void setSelectCheckboxs(Integer[] selectCheckboxs) {
		this.selectCheckboxs = selectCheckboxs;
	}

	public DemoEntity entity;
//	private Integer pkid;
//	private String name;
//	private Integer wins;
//	private Integer best;
//	private Float size;

}
