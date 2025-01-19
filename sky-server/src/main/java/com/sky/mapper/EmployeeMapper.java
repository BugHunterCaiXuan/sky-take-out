package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 插入员工数据
     * @param employee
     */
    @Insert("insert into employee (name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user,status) " +
            "values " +
            "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Employee employee);

    Page<Employee> pageQuery(EmployeePageQueryDTO pageQuery);

    /**
     * 根据id修改员工信息
     * @param employee
     */

    void update(Employee employee);

    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);

    @Update("update employee set password = #{newPassword} where id = #{id}")
    void updatePassword(Employee employee);
}
