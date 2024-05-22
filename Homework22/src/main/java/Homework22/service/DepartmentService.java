package Homework22.service;

import Homework22.exception.EmployeeNotFoundException;
import Homework22.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class DepartmentService {
    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Employee findEmployeeWithMaxSalaryInDepartment (Integer departmentId) {
        return employeeService.findAll().stream()
                .filter(e -> e.getDepartmentId().equals(departmentId))
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public Employee findEmployeeWithMinSalaryInDepartment(Integer departmentId) {
        return employeeService.findAll().stream()
                .filter(e -> e.getDepartmentId().equals(departmentId))
                .min(Comparator.comparingDouble(Employee::getSalary))
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public Collection<Employee> findEmployeesByDepartment(Integer departmentId) {
        return employeeService.findAll().stream()
                .filter(e -> e.getDepartmentId().equals(departmentId))
                .collect(Collectors.toList());
    }

    public Map<Integer, List<Employee>> findEmployeesAllDepartment() {
        return employeeService.findAll().stream()
                .collect(groupingBy(Employee::getDepartmentId));

    }
}
