package edu.sabanciuniv.springweb.controller;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private List<Employee> employees = new ArrayList<>();

    @PostConstruct
    private void generateData() {
        employees.add(new Employee(1, "Batuhan", "Bozdag"));
        employees.add(new Employee(2, "Ayse", "Tufan"));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employees);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {

        Employee result = new Employee();

        for (Employee employee : employees) {
            if (employee.getId() == id) {
                result = employee;
            }
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Object> createEmployee(@RequestBody(required = false) EmployeeRequestModel employeeRequestModel) {
        Assert.notNull(employeeRequestModel,"Employee request model cannot be null!");
        Employee employee = new Employee();
        employee.setName(employeeRequestModel.getName());
        employee.setLastname(employeeRequestModel.getLastname());
        employee.setId(employees.stream().map(Employee::getId).max(Comparator.naturalOrder()).orElseThrow() + 1);
        employees.add(employee);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(employee.getId())
                .toUri();

        return ResponseEntity.status(HttpStatus.CREATED).location(uri).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable long id) {
        Employee employeeToDelete = new Employee();

    if(!employees.stream().map(Employee::getId).collect(Collectors.toList()).contains(id)){
        throw new NoSuchElementException(String.format("Employee with id %d cannot be found in the database!",id));
    }

        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employeeToDelete = employee;
                break;
            }
        }



        employees.remove(employeeToDelete);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}