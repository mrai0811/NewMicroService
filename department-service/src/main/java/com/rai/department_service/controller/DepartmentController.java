package com.rai.department_service.controller;

import com.rai.department_service.client.EmployeeClient;
import com.rai.department_service.model.Department;
import com.rai.department_service.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);


    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeClient employeeClient;

    @PostMapping
    public Department add (@RequestBody Department department){
        LOGGER.info("Department add: {}", department);
        return departmentRepository.addDepartment(department);
    }

    @GetMapping
    public List<Department> findAll(){
        LOGGER.info("Department find");
        return departmentRepository.findAll();
    }

//    @GetMapping("/{id}")
//    public Department findById(@PathVariable Long id){
//        return departmentRepository.findById(id);
//    }

    @GetMapping("/with-employees")
    public List<Department> findAllWithEmployees(){
        LOGGER.info("Department find");
        List<Department> departments = departmentRepository.findAll();

        departments.forEach(department ->
                department.setEmployees(employeeClient.findByDepartment(department.getId())));
        return departments;
    }
}
