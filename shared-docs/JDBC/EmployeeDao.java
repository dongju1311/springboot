package chapter20_JDBC;

import java.util.ArrayList;
import java.util.List;
import db.DBConn;

public class EmployeeDao extends DBConn{
	
	//3단계: CRUD 기능에 따라 메소드 생성
	public List<EmployeeVo> getList() {
		List<EmployeeVo> list = new ArrayList<EmployeeVo>();
		String sql = """
					select  emp_id,
							emp_name,
							hire_date,
							salary
					from employee
				""";
		try {
			getStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				EmployeeVo  employee = new EmployeeVo();
				employee.setEmpId(rs.getString(1));
				employee.setEmpName(rs.getString(2));
				employee.setHireDate(rs.getString(3));
				employee.setSalary(rs.getInt(4));	
				
				list.add(employee);
			}
		} catch (Exception e) { e.printStackTrace(); }
		
		close();
		
		return list;
	}
		
}

















