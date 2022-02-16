package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DemoEntity;
import com.example.demo.DemoMapper;

@Service
public class DemoService {

	@Autowired
	DemoMapper demoMapper;

	public List<DemoEntity> findAll(){
		List<DemoEntity> list = demoMapper.findAllOrderById();
		return list;
	}

	public DemoEntity findById(Integer pkid){
		DemoEntity demoEntity = demoMapper.findById(pkid);
		return demoEntity;
	}

	public void create(DemoEntity demoEntity) {
		demoMapper.create(demoEntity);
	}

	public void update(DemoEntity demoEntity) {
		demoMapper.update(demoEntity);
	}

	public void delete(Integer pkid){
		demoMapper.delete(pkid);
	}

}
