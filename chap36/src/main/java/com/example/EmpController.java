package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.mapper.EmpMapper;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class EmpController {
	
	@Autowired
	EmpMapper empMapper;
	
	@GetMapping("/emp/list")
	void list(HttpServletRequest request) {
		var list = empMapper.selectAll();
		System.out.println(list);
		request.setAttribute("list",list);
	}
}
