package com.patterns.composite.simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite component representing a department.
 * Can contain employees and other departments.
 */
public class Department implements OrganizationComponent {
    private final String name;
    private final List<OrganizationComponent> members = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }

    /**
     * Adds a member (employee or department) to this department.
     *
     * @param member Member to add
     */
    public void add(OrganizationComponent member) {
        // TODO: Add member to members list
        throw new UnsupportedOperationException("TODO: Implement Department.add()");
    }

    /**
     * Removes a member from this department.
     *
     * @param member Member to remove
     */
    public void remove(OrganizationComponent member) {
        // TODO: Remove member from members list
        throw new UnsupportedOperationException("TODO: Implement Department.remove()");
    }

    /**
     * Gets all members of this department.
     *
     * @return List of members
     */
    public List<OrganizationComponent> getMembers() {
        // TODO: Return members list (defensive copy recommended)
        throw new UnsupportedOperationException("TODO: Implement Department.getMembers()");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getSalaryBudget() {
        // TODO: Calculate total budget by summing all members' budgets
        // Hint: use stream().mapToDouble(OrganizationComponent::getSalaryBudget).sum()
        throw new UnsupportedOperationException("TODO: Implement Department.getSalaryBudget()");
    }

    @Override
    public int getEmployeeCount() {
        // TODO: Calculate total employee count by summing all members' counts
        // Hint: use stream().mapToInt(OrganizationComponent::getEmployeeCount).sum()
        throw new UnsupportedOperationException("TODO: Implement Department.getEmployeeCount()");
    }

    @Override
    public OrganizationComponent findByName(String searchName) {
        // TODO: Search for component by name
        // 1. Check if this department's name matches
        // 2. If not, recursively search through all members
        // 3. Return first match found, or null if not found
        throw new UnsupportedOperationException("TODO: Implement Department.findByName()");
    }

    @Override
    public void printStructure(int depth) {
        // TODO: Print department name with indentation
        // Format: "  " repeated depth times, then "[DEPT: name]"
        // Then recursively print all members with depth + 1
        throw new UnsupportedOperationException("TODO: Implement Department.printStructure()");
    }
}
