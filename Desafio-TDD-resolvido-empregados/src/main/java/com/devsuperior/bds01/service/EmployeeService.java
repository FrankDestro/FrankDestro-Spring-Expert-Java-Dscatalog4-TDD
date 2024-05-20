package com.devsuperior.bds01.service;

import com.devsuperior.bds01.dto.DepartmentDTO;
import com.devsuperior.bds01.dto.EmployeeDTO;
import com.devsuperior.bds01.entities.Department;
import com.devsuperior.bds01.entities.Employee;
import com.devsuperior.bds01.repositories.DepartmentRepository;
import com.devsuperior.bds01.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findAllPaged(Pageable pageable) {
        Page<Employee> list = employeeRepository.findAll(pageable);
        return list.map(x -> new EmployeeDTO(x));
    }

    @Transactional
    public EmployeeDTO insert(EmployeeDTO dto) {
        Employee entity = new Employee();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setDepartment(new Department(dto.getDepartmentId(), "Sales"));

        System.out.println(entity.getDepartment().id);


        entity = employeeRepository.save(entity);
        return new EmployeeDTO(entity);
    }
}

