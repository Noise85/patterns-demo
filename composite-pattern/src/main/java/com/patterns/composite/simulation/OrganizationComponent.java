package com.patterns.composite.simulation;

/**
 * Component interface for organization elements.
 * Both employees and departments implement this interface.
 */
public interface OrganizationComponent {
    /**
     * Gets the name of the component.
     *
     * @return Component name
     */
    String getName();

    /**
     * Gets the total salary budget.
     * For employees, returns individual salary.
     * For departments, returns sum of all members' budgets.
     *
     * @return Total salary budget
     */
    double getSalaryBudget();

    /**
     * Gets the total employee count.
     * For employees, returns 1.
     * For departments, returns sum of all members' counts.
     *
     * @return Total employee count
     */
    int getEmployeeCount();

    /**
     * Finds a component by name (recursive search).
     *
     * @param name Name to search for
     * @return Component if found, null otherwise
     */
    OrganizationComponent findByName(String name);

    /**
     * Prints the organization structure with indentation.
     *
     * @param depth Indentation depth
     */
    void printStructure(int depth);
}
