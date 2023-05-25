package com.example.mapper;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
//@Transactional()
public class DeptMapperTest {
	
	@Autowired
	DeptMapper deptMapper;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	void selectAll() throws IOException {
		var list = deptMapper.selectAll();
		System.out.println(list);
		objectMapper.createGenerator(System.out)
					.useDefaultPrettyPrinter()
					.writeObject(list);
	}
	
	@Test
	void selectByDeptno() {
		var dept = deptMapper.selectByDeptno(10);
		//System.out.println(dept);
		assertSame(10, dept.getDeptno());
		
		
		dept = deptMapper.selectByDeptno(90);
		assertNull(dept);
	}
	
	@Test
	//@Transactional				//auto commit 방지
	void insert() {
		
		deptMapper.insert(50, "개발부", "부산");
		System.out.println(deptMapper.selectByDeptno(50));
		//throw new RuntimeException();
	}
}
