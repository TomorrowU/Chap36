package com.example.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Emp;
import com.example.util.Gender;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class EmpMapperTest {
	
	@Autowired
	EmpMapper empMapper;
	
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	void selectAll() throws IOException {
		
		var list = empMapper.selectAll();
		System.out.println(list);
		assertThat(list.size()).isSameAs(14);
		
		objectMapper.createGenerator(System.out)
					.useDefaultPrettyPrinter()
					.writeObject(list);
			
		
	}
	
	@Test
	void countAll() {
		int count = empMapper.countAll();
		assertThat(count).isEqualTo(14);
		System.out.println(count);
		
	}
	
	@Test
	void selectByEmpno() {
		var emp = empMapper.selectByEmpno(1002);
		System.out.println(emp);
		assertThat(emp.getEmpno()).isEqualTo(1002);
		assertEquals(1002,emp.getEmpno());
		//assertSame(1002,emp.getEmpno()); //Same 은 참조를 비교
		//assertSame(1270, 1270);  //-127~128 까지 같은거래요 그걸 넘어가면 새로 new 해서 다른 객체를 참조하게 됨
		emp = empMapper.selectByEmpno(9000);
		System.out.println(emp);
		assertNull(emp);
	}
	
	@Test
	@Transactional
	//@Rollback(false)
	void insertByEmpnoWithEname() throws IOException {
		int cnt=empMapper.insertByEmpnoWithEname(9000, "박재필");
		assertEquals(1, cnt);
		System.out.println(empMapper.selectByEmpno(9000));
		
		assertThrows(DataIntegrityViolationException.class,()->{
			empMapper.insertByEmpnoWithEname(9100, null);
		});
		
		
		
		assertThatThrownBy(()->{
			empMapper.insertByEmpnoWithEname(9001,null);
		}).isInstanceOf(DataIntegrityViolationException.class);
		
		assertThrows(DuplicateKeyException.class, () -> {
			empMapper.insertByEmpnoWithEname(1001,"홍길동" );
		});
		
		objectMapper.createGenerator(System.out)
					.useDefaultPrettyPrinter()
					.writeObject(empMapper.selectAll());
		
	}
	
	@Test
	@Transactional
	void insertEmp() throws IOException {
		var emp = new Emp();
		emp.setEmpno(3000);
		emp.setEname("홍길동");
		
		int cnt = empMapper.insertEmp(emp);
		assertThat(cnt).isEqualTo(1);
		
		var emp2= empMapper.selectByEmpno(3000);
		System.out.println(emp2);
		assertThat(emp).isEqualTo(emp2);
		
		emp = new Emp();
		emp.setEmpno(3100);
		emp.setEname("박재필");
		emp.setGender(Gender.M);
		emp.setHiredate(LocalDate.now());
		empMapper.insertEmp(emp);
		
		emp2=empMapper.selectByEmpno(3100);
		
		assertThat(emp).isEqualTo(emp2);
		
		objectMapper.createGenerator(System.out)
					.useDefaultPrettyPrinter()
					.writeObject(emp2);
		
		assertThrows(DataIntegrityViolationException.class,()->{
		var	emp3 = new Emp();
			emp3.setEmpno(3200);
			emp3.setEname("최재필");
			emp3.setGender(Gender.F);
			emp3.setDeptno(50);
			empMapper.insertEmp(emp3);
			
		});
		
		emp2=empMapper.selectByEmpno(3200);
		
		objectMapper.createGenerator(System.out)
		.useDefaultPrettyPrinter()
		.writeObject(emp2);
		
		
	}
	
	@Test
	@Transactional
	void updateByEmpnoWithSal() throws IOException {
		int cnt = empMapper.updateByEmpnoWithSal(1001, 500.45);
		assertThat(cnt).isEqualTo(1);
		
		objectMapper.createGenerator(System.out)
					.useDefaultPrettyPrinter()
					.writeObject(empMapper.selectAll());
		
	}
	
	@Test
	@Transactional
	void updateByDeptnoWithEmpno() throws IOException {
		int cnt = empMapper.updateByEmpnoWithDeptno(1001,40);
		assertThat(cnt).isEqualTo(1);
		
		var emp = empMapper.selectByEmpno(1001);
		assertThat(emp.getDeptno()).isEqualTo(40);
		
		cnt = empMapper.updateByEmpnoWithDeptno(1002, null);
		
		assertThrows(DataIntegrityViolationException.class,()->{
			var cnt2 = empMapper.updateByEmpnoWithDeptno(1003, 50);	
		});
		
		
		objectMapper.createGenerator(System.out)
					.useDefaultPrettyPrinter()
					.writeObject(empMapper.selectAll());
	}
	
	@Test
	@Transactional
	void updateEmp() throws IOException {
		
		var emp = empMapper.selectByEmpno(1001);
		emp.setJob("developer");
		emp.setHiredate(LocalDate.now());
		emp.setDeptno(40);
		
		int cnt = empMapper.updateEmp(emp);
		assertThat(cnt).isEqualTo(1);
		
		
		objectMapper.createGenerator(System.out)
		.useDefaultPrettyPrinter()
		.writeObject(empMapper.selectByEmpno(1001));
	}
	
	@Test
	@Transactional
	void delete() throws IOException {
		int cnt = empMapper.delete(1001);
		assertThat(cnt).isEqualTo(1);
	
		
		
		objectMapper.createGenerator(System.out)
		.useDefaultPrettyPrinter()
		.writeObject(empMapper.selectAll());
	
	}
}