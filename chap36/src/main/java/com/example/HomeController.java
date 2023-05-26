package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.mapper.CityMapper;
import com.example.mapper.DeptMapper;
import com.example.model.Dept;
import com.github.pagehelper.PageHelper;

@Controller
public class HomeController {

	@Autowired
	DeptMapper deptMapper;
	
	@Autowired
	CityMapper cityMapper;
	
	@GetMapping("/")
	@Transactional
	String home(Model model) {
		
//		var list = deptMapper.selectByDeptno(10);
//		
//		model.addAttribute("list", list);
//		
//		PageHelper.startPage(1,20);
//		var list2=cityMapper.selectPage();
//		
//		model.addAttribute("list2",list2);		
		
		return "home";
	}
	
	 @PostMapping("/homeController")
	    public String handleFormData(@RequestParam("inputValue") String inputValue ,Model model) {
	        // inputValue 변수에는 home.jsp에서 입력한 값이 전달됩니다.
	        // 이곳에서 필요한 로직을 수행하거나 값을 가공하여 다른 메소드로 전달할 수 있습니다.
	        
	        // 예를 들어, 입력값을 모델에 담아 다른 뷰로 전달하는 경우:
	        // model.addAttribute("inputValue", inputValue);
	        // return "otherView";
		 	var list =deptMapper.selectByDeptno(Integer.parseInt(inputValue));
		 	model.addAttribute(list);
		 
		 
	        return "home"; // 결과를 보여줄 뷰 이름을 리턴합니다.
	    }
	
	
}
