package com.patterns.composite.simulation;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

/**
 * Tests for Exercise 2: Organization Chart Composite
 */
class SimulationExerciseTest {

    @Test
    void employee_returnsSalaryBudget() {
        Employee employee = new Employee("Alice", "Engineer", 80000);

        assertThat(employee.getSalaryBudget()).isEqualTo(80000);
    }

    @Test
    void employee_countsAsOne() {
        Employee employee = new Employee("Bob", "Designer", 70000);

        assertThat(employee.getEmployeeCount()).isEqualTo(1);
    }

    @Test
    void employee_findsItselfByName() {
        Employee employee = new Employee("Charlie", "Manager", 90000);

        OrganizationComponent found = employee.findByName("Charlie");

        assertThat(found).isSameAs(employee);
    }

    @Test
    void employee_returnsNullForDifferentName() {
        Employee employee = new Employee("Diana", "Analyst", 65000);

        OrganizationComponent found = employee.findByName("Eve");

        assertThat(found).isNull();
    }

    @Test
    void department_startsEmpty() {
        Department department = new Department("Engineering");

        assertThat(department.getSalaryBudget()).isEqualTo(0);
        assertThat(department.getEmployeeCount()).isEqualTo(0);
        assertThat(department.getMembers()).isEmpty();
    }

    @Test
    void department_addsEmployees() {
        Department department = new Department("Sales");
        Employee emp1 = new Employee("Frank", "Sales Rep", 50000);
        Employee emp2 = new Employee("Grace", "Sales Rep", 52000);

        department.add(emp1);
        department.add(emp2);

        assertThat(department.getMembers()).hasSize(2);
        assertThat(department.getMembers()).contains(emp1, emp2);
    }

    @Test
    void department_removesEmployees() {
        Department department = new Department("HR");
        Employee emp = new Employee("Henry", "Recruiter", 60000);

        department.add(emp);
        department.remove(emp);

        assertThat(department.getMembers()).isEmpty();
    }

    @Test
    void department_calculatesSalaryBudget() {
        Department department = new Department("Marketing");
        department.add(new Employee("Ivy", "Marketer", 55000));
        department.add(new Employee("Jack", "Content Writer", 48000));
        department.add(new Employee("Kelly", "Social Media Manager", 52000));

        assertThat(department.getSalaryBudget()).isEqualTo(155000);
    }

    @Test
    void department_countsEmployees() {
        Department department = new Department("Support");
        department.add(new Employee("Liam", "Support Agent", 45000));
        department.add(new Employee("Mia", "Support Agent", 46000));
        department.add(new Employee("Noah", "Support Lead", 55000));

        assertThat(department.getEmployeeCount()).isEqualTo(3);
    }

    @Test
    void department_containsSubdepartments() {
        Department parent = new Department("Technology");
        Department child = new Department("DevOps");

        child.add(new Employee("Olivia", "DevOps Engineer", 85000));
        parent.add(child);

        assertThat(parent.getMembers()).contains(child);
        assertThat(parent.getSalaryBudget()).isEqualTo(85000);
        assertThat(parent.getEmployeeCount()).isEqualTo(1);
    }

    @Test
    void nestedDepartments_calculateBudgetRecursively() {
        Department company = new Department("Company");
        Department engineering = new Department("Engineering");
        Department qa = new Department("QA");

        engineering.add(new Employee("Paul", "Senior Engineer", 95000));
        engineering.add(new Employee("Quinn", "Junior Engineer", 60000));

        qa.add(new Employee("Rita", "QA Lead", 75000));
        qa.add(new Employee("Sam", "QA Engineer", 55000));

        company.add(engineering);
        company.add(qa);
        company.add(new Employee("Tom", "CEO", 150000));

        // Total: 95000 + 60000 + 75000 + 55000 + 150000 = 435000
        assertThat(company.getSalaryBudget()).isCloseTo(435000, within(0.01));
    }

    @Test
    void nestedDepartments_countEmployeesRecursively() {
        Department company = new Department("Company");
        Department sales = new Department("Sales");
        Department marketing = new Department("Marketing");

        sales.add(new Employee("Uma", "Sales Rep", 50000));
        sales.add(new Employee("Victor", "Sales Rep", 51000));

        marketing.add(new Employee("Wendy", "Marketer", 54000));

        company.add(sales);
        company.add(marketing);
        company.add(new Employee("Xavier", "CFO", 140000));

        assertThat(company.getEmployeeCount()).isEqualTo(4);
    }

    @Test
    void findByName_findsEmployeeInDepartment() {
        Department department = new Department("Research");
        Employee target = new Employee("Yara", "Researcher", 70000);
        department.add(target);
        department.add(new Employee("Zane", "Researcher", 72000));

        OrganizationComponent found = department.findByName("Yara");

        assertThat(found).isSameAs(target);
    }

    @Test
    void findByName_findsDepartmentByName() {
        Department parent = new Department("Parent");
        Department target = new Department("TargetDept");
        parent.add(target);

        OrganizationComponent found = parent.findByName("TargetDept");

        assertThat(found).isSameAs(target);
    }

    @Test
    void findByName_searchesRecursively() {
        Department root = new Department("Root");
        Department level1 = new Department("Level1");
        Department level2 = new Department("Level2");
        Employee deepEmployee = new Employee("DeepPerson", "Worker", 50000);

        level2.add(deepEmployee);
        level1.add(level2);
        root.add(level1);

        OrganizationComponent found = root.findByName("DeepPerson");

        assertThat(found).isSameAs(deepEmployee);
    }

    @Test
    void findByName_returnsNullWhenNotFound() {
        Department department = new Department("Empty");

        OrganizationComponent found = department.findByName("NonExistent");

        assertThat(found).isNull();
    }

    @Test
    void printStructure_displaysHierarchy() {
        ByteArrayOutputStream output = captureSystemOut();
        Department company = new Department("Company");
        Department engineering = new Department("Engineering");

        engineering.add(new Employee("Alice", "Engineer", 80000));
        engineering.add(new Employee("Bob", "Engineer", 82000));
        company.add(engineering);
        company.add(new Employee("Carol", "CEO", 150000));

        company.printStructure(0);

        String result = output.toString();
        assertThat(result).contains("Company");
        assertThat(result).contains("Engineering");
        assertThat(result).contains("Alice");
        assertThat(result).contains("Bob");
        assertThat(result).contains("Carol");
    }

    @Test
    void complexOrganization_calculatesCorrectly() {
        Department company = new Department("TechCorp");
        
        Department engineering = new Department("Engineering");
        Department backend = new Department("Backend");
        Department frontend = new Department("Frontend");
        
        backend.add(new Employee("Dev1", "Backend Dev", 80000));
        backend.add(new Employee("Dev2", "Backend Dev", 82000));
        
        frontend.add(new Employee("Dev3", "Frontend Dev", 75000));
        frontend.add(new Employee("Dev4", "Frontend Dev", 77000));
        
        engineering.add(backend);
        engineering.add(frontend);
        engineering.add(new Employee("EngManager", "Engineering Manager", 100000));
        
        Department sales = new Department("Sales");
        sales.add(new Employee("Sales1", "Sales Rep", 55000));
        sales.add(new Employee("Sales2", "Sales Rep", 56000));
        
        company.add(engineering);
        company.add(sales);
        company.add(new Employee("CEO", "Chief Executive", 200000));

        // Total: 80k + 82k + 75k + 77k + 100k + 55k + 56k + 200k = 725k
        assertThat(company.getSalaryBudget()).isCloseTo(725000, within(0.01));
        
        // Count: 2 + 2 + 1 + 2 + 1 = 8 employees
        assertThat(company.getEmployeeCount()).isEqualTo(8);
    }

    private ByteArrayOutputStream captureSystemOut() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        return outputStream;
    }
}
