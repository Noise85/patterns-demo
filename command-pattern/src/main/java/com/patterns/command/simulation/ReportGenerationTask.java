package com.patterns.command.simulation;

import java.time.LocalDate;

/**
 * Task for generating reports.
 */
public class ReportGenerationTask extends BaseTask {
    
    private final String reportType;
    private final LocalDate period;
    
    /**
     * Creates a report generation task.
     *
     * @param taskId unique task ID
     * @param reportType type of report to generate
     * @param period reporting period
     */
    public ReportGenerationTask(String taskId, String reportType, LocalDate period) {
        super(taskId, TaskPriority.NORMAL, true, 2);
        this.reportType = reportType;
        this.period = period;
    }
    
    @Override
    protected String doExecute() throws Exception {
        // TODO: Implement report generation simulation
        // Simulate generating report
        // Return message like: "Generated [reportType] report for [period]"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getDescription() {
        return String.format("Generate %s report for %s", reportType, period);
    }
    
    public String getReportType() {
        return reportType;
    }
    
    public LocalDate getPeriod() {
        return period;
    }
}
