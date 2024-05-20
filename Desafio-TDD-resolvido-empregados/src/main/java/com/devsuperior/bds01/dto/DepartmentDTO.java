package com.devsuperior.bds01.dto;

import com.devsuperior.bds01.entities.Department;
import com.devsuperior.bds01.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DepartmentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long id;
    public String name;

    public DepartmentDTO(Department entity) {
        id = entity.getId();
        name = entity.getName();
    }
}
