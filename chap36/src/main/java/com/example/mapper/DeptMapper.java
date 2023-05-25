package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.model.Dept;

@Mapper
public interface DeptMapper {
	
	@Select("select * from dept")
	List<Dept> selectAll();
	
	
	@Select("select * from dept where deptno=#{deptno}")
	Dept selectByDeptno(@Param("deptno")int deptno);
	
	@Insert("insert into dept values(#{deptno},#{dname},#{loc})")
	int insert(@Param("deptno") int deptno, @Param("dname") String dname, @Param("loc") String loc);

	@Select("select * from dept where deptno = #{deptno}")
	Dept getDept(@Param("deptno") int deptno);
		
	
}
