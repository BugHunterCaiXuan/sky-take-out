package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.BaseException;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    //启用，禁用员工账号
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用员工账号")
    public Result<String> startOrStop(@PathVariable("status") Integer status, Long id){
        log.info("启用或禁用员工账号：{},{}",status,id);
        //调用service保存
        employeeService.startOrStop(status,id);
        return Result.success();
    }


    //分类分页查询
    @GetMapping("/page")
    @ApiOperation("员工分页查询")
    public Result<PageResult> page( EmployeePageQueryDTO pageQuery){
        log.info("分页查询：{}",pageQuery);
        PageResult pageResult = employeeService.pageQuery(pageQuery);
        return Result.success(pageResult);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    @PostMapping()
    public Result<String> save(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工：{}",employeeDTO);
        // 1. 参数校验
        if (StringUtils.isBlank(employeeDTO.getUsername())) {
            return Result.error("用户名不能为空");
        }
        if (StringUtils.isBlank(employeeDTO.getName())) {
            return Result.error("姓名不能为空");
        }
        if (StringUtils.isBlank(employeeDTO.getPhone())) {
            return Result.error("手机号不能为空");
        }
        if (StringUtils.isBlank(employeeDTO.getIdNumber())) {
            return Result.error("身份证号不能为空");
        }

        // 2. 调用service保存
        try {
            employeeService.save(employeeDTO);
            return Result.success("新增员工成功");
        } catch (BaseException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("新增员工失败：", e);
            return Result.error("新增员工失败");
        }
    }

}
