package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.mapper.DeptMapper;
import com.example.model.Dept;

@Controller
public class HomeController {

	@Autowired
	DeptMapper deptMapper;
	
	@GetMapping("/")
	@Transactional
	String home(Model model) {
		
		var list = deptMapper.selectByDeptno(10);
		
		model.addAttribute("list", list);
		
		
		return "home";
	}
	
	
	
}
