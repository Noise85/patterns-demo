package com.patterns.composite.simulation;

/**
 * Leaf component representing an employee.
 */
public class Employee implements OrganizationComponent {
    private final String name;
    private final String role;
    private final double salary;

    public Employee(String name, String role, double salary) {
        this.name = name;
        this.role = role;
        this.salary = salary;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public double getSalaryBudget() {
        // TODO: Return individual salary
        throw new UnsupportedOperationException("TODO: Implement Employee.getSalaryBudget()");
    }

    @Override
    public int getEmployeeCount() {
        // TODO: Return 1 (individual employee)
        throw new UnsupportedOperationException("TODO: Implement Employee.getEmployeeCount()");
    }

    @Override
    public OrganizationComponent findByName(String searchName) {
        // TODO: Return this if name matches, otherwise null
        throw new UnsupportedOperationException("TODO: Implement Employee.findByName()");
    }

    @Override
    public void printStructure(int depth) {
        // TODO: Print employee with indentation
        // Format: "  " repeated depth times, then "name (role) - $salary"
        throw new UnsupportedOperationException("TODO: Implement Employee.printStructure()");
    }
}
