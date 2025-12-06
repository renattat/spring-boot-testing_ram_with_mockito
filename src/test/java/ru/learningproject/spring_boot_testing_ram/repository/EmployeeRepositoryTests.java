package ru.learningproject.spring_boot_testing_ram.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.learningproject.spring_boot_testing_ram.model.Employee;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public  void setUp(){
        employee = Employee.builder()
                .firstName("Renat")
                .lastName("Sim")
                .email("renat@gmail.com")
                .build();
    }

    // JUnit test for save employee operation
    @DisplayName("JUnit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Renat")
//                .lastName("Sim")
//                .email("renat@gmail.com")
//                .build();

        // when - action or behaviour we are going test
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    // Junit test for you
    @DisplayName("Junit test for get all employees operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenEmployeesList() {
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .firstName("Renat")
                .lastName("Sim")
                .email("renat@gmail.com")
                .build();

        Employee employee2 = Employee.builder()
                .firstName("John")
                .lastName("Cenna")
                .email("cenna@gmail.com")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Get employee by id operation")
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Renat")
//                .lastName("Sim")
//                .email("renat@gmail.com")
//                .build();
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    @Test
    @DisplayName("Get employee by email operation")
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Renat")
//                .lastName("Sim")
//                .email("renat@gmail.com")
//                .build();
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    @Test
    @DisplayName("Junit test for update employee operation")
    public void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Renat")
//                .lastName("Sim")
//                .email("renat@gmail.com")
//                .build();
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();
        employeeDB.setEmail("123renat@mail.com");
        Employee updatedEmployee = employeeRepository.save(employeeDB);

        // then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("123renat@mail.com");
    }

    @Test
    @DisplayName("test for delete employee operation")
    public void givenEmployee_whenDeleteEmployee_thenRemoveEmployee() {
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Renat")
//                .lastName("Sim")
//                .email("renat@gmail.com")
//                .build();
        employeeRepository.save(employee);
        // when - action or the behaviour that we are going test
        employeeRepository.delete(employee);
//        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // then - verify the output
        assertThat(employeeOptional).isEmpty();
    }


    @Test
    @DisplayName("test for custom query using JPQL with index")
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployee() {
        // given - precondition or setup
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Renat")
//                .lastName("Sim")
//                .email("renat@gmail.com")
//                .build();
        employeeRepository.save(employee);
        String firstName = "Renat";
        String lastName = "Sim";

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.fidByJPQL(firstName, lastName);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();

    }

}
