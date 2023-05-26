package com.example.mapper;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Dept;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
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
		System.out.println(dept);
		assertSame(10, dept.getDeptno());
		
		dept = deptMapper.selectByDeptno(90);
		System.out.println(dept);
		assertNull(dept);
	}
	
	@Test
	@Transactional
//	@Rollback(false)   //활성화 할시 Commit
	void insert() {
		deptMapper.insert(50, "개발부", "부산");
		System.out.println(deptMapper.selectByDeptno(50));
		
		deptMapper.insert(60, "개발2부", null);
		System.out.println(deptMapper.selectByDeptno(60));
		
		try {
			deptMapper.insert(50, "개발3부", "서울");
		} catch (DuplicateKeyException e) {
			System.out.println("50번 부서는 사용할 수 없습니다.");
			deptMapper.insert(51, "개발3부", "서울");
			System.out.println(deptMapper.selectByDeptno(51));
		}
		
		try {
			deptMapper.insert(70, null, null);
		} catch (DataIntegrityViolationException e) {
			System.out.println(e.getMessage());
		}
		
		//DataIntegrityViolationException이 발생하면 성공
		assertThrows(DataIntegrityViolationException.class,()->{
			deptMapper.insert(100,"총무부", null);
		});
		
		
		
		
		
	}
	
	@Test
	@Transactional
	void insertDept() throws IOException {
		var dept = new Dept(50,"개발1부","서울");
		int cnt = deptMapper.insertDept(dept);
		assertThat(cnt).isEqualTo(1);
		
		dept= new Dept(60,"개발2부","null");
		cnt =deptMapper.insertDept(dept);
		assertThat(cnt).isEqualTo(1);
		
		assertThrows(DuplicateKeyException.class,()->{
			deptMapper.insertDept(new Dept(60,"개발3부",null));
		});
		
		assertThrows(DataIntegrityViolationException.class,()->{
			deptMapper.insertDept(new Dept(200,"개발4부",null));
		});
		
		objectMapper.createGenerator(System.out)
					.useDefaultPrettyPrinter()
					.writeObject(deptMapper.selectAll());
		
	}
	
	@Test
	@Transactional
	void update() throws IOException {
		int cnt = deptMapper.update(10,"xxx","yyy");
		assertThat(cnt).isEqualTo(1);
		
		cnt = deptMapper.update(60,"xxx","yyy");
		assertThat(cnt).isEqualTo(0); //0이면 안된다고 확신 1이면 된다고 확신
		
		assertThrows(DataIntegrityViolationException.class, () -> {
			try {
				int c = deptMapper.update(20, null, null);
			} catch (UncategorizedSQLException e) {
				throw new DataIntegrityViolationException(e.getMessage());
			}
		});
		
		cnt=deptMapper.update(100,"개발4부" ,"부산");
		assertThat(cnt).isEqualTo(0);
		
		cnt=deptMapper.update(30,"개발5부", null);
		assertThat(cnt).isEqualTo(1);
		
		objectMapper.createGenerator(System.out)
					.useDefaultPrettyPrinter()
					.writeObject(deptMapper.selectAll());
		
	}
	
	@Test
	@Transactional
	void updateDept() throws IOException {
		var dept = new Dept(10,"xxx","yyy");
		int cnt = deptMapper.updateDept(dept);
		assertThat(cnt).isEqualTo(1);
		
		
		dept = new Dept(60,"zzz","aaa");
		cnt=deptMapper.updateDept(dept);
		assertThat(cnt).isEqualTo(0);
		
		objectMapper.createGenerator(System.out)
					.useDefaultPrettyPrinter()
					.writeObject(deptMapper.selectAll());
		
	}
	@Test
	@Transactional
	void delete() throws IOException {
		int cnt = deptMapper.delete(1001);
		assertThat(cnt).isEqualTo(1);
	
		
		cnt = deptMapper.delete(40);
		assertThat(cnt).isEqualTo(1);
		
		objectMapper.createGenerator(System.out)
		.useDefaultPrettyPrinter()
		.writeObject(deptMapper.selectAll());
		
		assertThrows(DataIntegrityViolationException.class,()->{
			deptMapper.delete(10);
		});
		
		
		
		
	
	}
	
	
}
