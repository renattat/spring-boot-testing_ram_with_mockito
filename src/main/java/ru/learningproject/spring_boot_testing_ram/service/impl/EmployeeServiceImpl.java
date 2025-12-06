package ru.learningproject.spring_boot_testing_ram.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.learningproject.spring_boot_testing_ram.exception.ResourceNotFoundException;
import ru.learningproject.spring_boot_testing_ram.model.Employee;
import ru.learningproject.spring_boot_testing_ram.repository.EmployeeRepository;
import ru.learningproject.spring_boot_testing_ram.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());

        if (savedEmployee.isPresent()) {
            throw new ResourceNotFoundException("Employee already exist with given email:" + employee.getEmail());
        }

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(Employee updatedEmployee) {
        return employeeRepository.save(updatedEmployee);
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }


}
