package Homework22.service;

import Homework22.exception.EmployeeAlreadyAddedException;
import Homework22.exception.EmployeeNotFoundException;
import Homework22.exception.EmployeeNotValidationTextException;
import Homework22.exception.EmployeeStorageIsFullException;
import Homework22.model.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {
    private final int maxEmployees = 30;

    private Map<String, Employee> employees = new HashMap<>();

    public void changeFirstLetter(String firstName, String lastName) {
        firstName = StringUtils.capitalize(firstName);
        lastName = StringUtils.capitalize(lastName);
    }

    public void validationSymbolInFirstAndLastName(String firstName, String lastName) {
        if (!StringUtils.isAlpha(firstName) || !StringUtils.isAlpha(lastName)) {
            throw new EmployeeNotValidationTextException();
        }
    }
    public Employee addEmployee(String firstName, String lastName, Integer departmentId, Double salary) {
        firstName = StringUtils.capitalize(firstName);
        lastName = StringUtils.capitalize(lastName);
        validationSymbolInFirstAndLastName(firstName, lastName);
        if (employees.containsKey(buildKey(firstName, lastName))) {
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size() >= maxEmployees) {
            throw new EmployeeStorageIsFullException();
        }
        Employee employee = new Employee(firstName, lastName, departmentId, salary);
        return employees.put(buildKey(firstName, lastName), employee);
    }

    public Employee removeEmployee(String firstName, String lastName) {
        firstName = StringUtils.capitalize(firstName);
        lastName = StringUtils.capitalize(lastName);
        validationSymbolInFirstAndLastName(firstName, lastName);
        if (!employees.containsKey(buildKey(firstName, lastName))) {
            throw new EmployeeNotFoundException();
        }
        return employees.remove(buildKey(firstName, lastName));
    }

    public Employee findEmployee(String firstName, String lastName) {
        firstName = StringUtils.capitalize(firstName);
        lastName = StringUtils.capitalize(lastName);
        validationSymbolInFirstAndLastName(firstName, lastName);
        if (!employees.containsKey(buildKey(firstName, lastName))) {
            throw new EmployeeNotFoundException();
        }
        return employees.get(buildKey(firstName, lastName));
    }

    public Collection<Employee> findAll() {
        return employees.values();
    }

    private String buildKey(String firstName, String lastName) {
        firstName = StringUtils.capitalize(firstName);
        lastName = StringUtils.capitalize(lastName);
        validationSymbolInFirstAndLastName(firstName, lastName);
        return firstName + lastName;
    }
}
