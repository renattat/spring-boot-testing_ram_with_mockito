package ru.learningproject.spring_boot_testing_ram.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Service;
import ru.learningproject.spring_boot_testing_ram.exception.ResourceNotFoundException;
import ru.learningproject.spring_boot_testing_ram.model.Employee;
import ru.learningproject.spring_boot_testing_ram.repository.EmployeeRepository;
import ru.learningproject.spring_boot_testing_ram.service.impl.EmployeeServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp() {
//        employeeRepository = Mockito.mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstName("Renat")
                .lastName("Sim")
                .email("renat@gmail.com")
                .build();
    }

    @Test
    @DisplayName("saveEmployee method")
    public void givenEmployeeObject_whenSAveEmployee_thenReturnEmployeeObject() {
        // given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());

        given(employeeRepository.save(employee)).willReturn(employee);
        System.out.println(employeeRepository);
        System.out.println(employeeService);

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then - verify the output
        System.out.println(savedEmployee);
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    @DisplayName("saveEmployee method which throws exception")
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
        // given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        // when - action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,
                () -> given(employeeService.saveEmployee(employee)));

        // then - verify the output
        verify(employeeRepository, never()).save(any(Employee.class));
    }


    @Test
    @DisplayName("getAllEmployees method")
    public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList() {
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tony")
                .lastName("Stark")
                .email("tony@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        // when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("getAllEmployees method (negative scenario)")
    public void givenEmptyEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tony")
                .lastName("Stark")
                .email("tony@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("getEmployeeById method")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        // given - precondition or setup
        given(employeeRepository.findById(any())).willReturn(Optional.of(employee));
        // when - action or the behaviour that we are going test

        Optional<Employee> foundEmployee = employeeService.getEmployeeById(1L);

        // then - verify the output
        assertThat(foundEmployee).isNotNull();
    }

    @Test
    @DisplayName("updateEmployee method")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setFirstName("123Renat");
        employee.setEmail("123renat@gmail.com");
        // when - action or the behaviour that we are going test

        Employee updatedEmployee = employeeService.saveEmployee(employee);

        // then - verify the output
        assertThat(updatedEmployee.getFirstName()).isEqualTo("123Renat");
        assertThat(updatedEmployee.getEmail()).isEqualTo("123renat@gmail.com");
    }

    @Test
    @DisplayName("deleteEmployee method")
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        // given - precondition or setup
        long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(1L);

        // when - action or the behaviour that we are going test
        employeeService.deleteEmployee(employeeId);

        // then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }


}
