package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.Dept;
import com.example.model.Emp;


@Mapper
public interface EmpMapper {
	
	@Select("select count(*) from emp")
	int countAll();
	
	@Select("select * from Emp")
	List<Emp> selectAll();
	
	@Select("""
			select * 
			from emp
			where empno=#{empno}
			""")
	Emp selectByEmpno(@Param("empno") int empno);
	
	@Insert("""
			insert into emp
			(empno,ename)
			values
			(#{empno},#{ename, jdbcType=VARCHAR})
			""")
	int insertByEmpnoWithEname(@Param("empno") int empno,@Param("ename") String ename);
	
	
	@Insert("""
			insert into emp
			values (
				#{e.empno},
				#{e.ename,    jdbcType=VARCHAR},
				#{e.gender,   jdbcType=VARCHAR},
				#{e.job,	  jdbcType=VARCHAR},
				#{e.mgr,      jdbcType=INTEGER},
				#{e.hiredate, jdbcType=DATE},
				#{e.sal,      jdbcType=DOUBLE},
				#{e.comm,     jdbcType=DOUBLE},
				#{e.deptno,   jdbcType=INTEGER}
			)
			""")
	int insertEmp(@Param("e") Emp emp); 
	
	@Update("""
			update emp
			set sal =    #{sal,jdbcType=DOUBLE}
			Where empno= #{empno}
			""")
	int updateByEmpnoWithSal(@Param("empno") int empno,@Param("sal") double sal);
	
	@Update("""
			update emp
			set deptno = #{deptno,jdbcType=INTEGER}
			Where empno= #{empno}
			""")
	int updateByEmpnoWithDeptno(@Param("empno") int empno,@Param("deptno") Integer deptno);
	
	@Update("""
			update emp
			set ename   =#{e.ename,    jdbcType=VARCHAR},
				gender  =#{e.gender,   jdbcType=VARCHAR},
				job     =#{e.job,	   jdbcType=VARCHAR},
				mgr	    =#{e.mgr,      jdbcType=INTEGER},
				hiredate=#{e.hiredate, jdbcType=DATE},
				sal  	=#{e.sal,      jdbcType=DOUBLE},
				comm	=#{e.comm,     jdbcType=DOUBLE},
				deptno	=#{e.deptno,   jdbcType=INTEGER}
			where empno=#{e.empno}
			""")
	int updateEmp(@Param("e") Emp emp);
	
	@Delete("""
			delete emp where empno=#{empno}
			""")
	int delete(@Param("empno")int empno);
	
}
	
	