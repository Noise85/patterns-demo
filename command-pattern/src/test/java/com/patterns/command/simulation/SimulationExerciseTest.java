package com.patterns.command.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Command Pattern - Simulation Exercise (Task Scheduling System).
 */
@DisplayName("Command Pattern - Task Scheduling System")
class SimulationExerciseTest {
    
    private TaskScheduler scheduler;
    
    @BeforeEach
    void setUp() {
        scheduler = new TaskScheduler();
    }
    
    @Test
    @DisplayName("Should execute data import task")
    void testExecuteDataImportTask() {
        Task task = new DataImportTask("T001", "customers.csv", 1000);
        
        TaskResult result = scheduler.executeTask(task);
        
        assertThat(result.success()).isTrue();
        assertThat(result.message()).contains("Imported").contains("1000").contains("customers.csv");
        assertThat(task.getStatus()).isEqualTo(TaskStatus.COMPLETED);
    }
    
    @Test
    @DisplayName("Should execute report generation task")
    void testExecuteReportTask() {
        Task task = new ReportGenerationTask("T002", "Sales", LocalDate.now());
        
        TaskResult result = scheduler.executeTask(task);
        
        assertThat(result.success()).isTrue();
        assertThat(result.message()).contains("Generated").contains("Sales");
        assertThat(task.getStatus()).isEqualTo(TaskStatus.COMPLETED);
    }
    
    @Test
    @DisplayName("Should execute email notification task")
    void testExecuteEmailTask() {
        Task task = new EmailNotificationTask("T003", "admin@company.com", "Import Complete");
        
        TaskResult result = scheduler.executeTask(task);
        
        assertThat(result.success()).isTrue();
        assertThat(result.message()).contains("Sent email").contains("admin@company.com");
        assertThat(task.getStatus()).isEqualTo(TaskStatus.COMPLETED);
    }
    
    @Test
   @DisplayName("Should execute API call task")
    void testExecuteApiCallTask() {
        Task task = new ApiCallTask("T004", "/api/users", "GET");
        
        TaskResult result = scheduler.executeTask(task);
        
        assertThat(result.success()).isTrue();
        assertThat(result.message()).contains("GET").contains("/api/users");
        assertThat(task.getStatus()).isEqualTo(TaskStatus.COMPLETED);
    }
    
    @Test
    @DisplayName("Should verify task priority levels")
    void testTaskPriorities() {
        Task lowPriority = new DataImportTask("T001", "data.csv", 100);
        Task normalPriority = new ReportGenerationTask("T002", "Report", LocalDate.now());
        Task highPriority = new EmailNotificationTask("T003", "admin@company.com", "Alert");
        
        assertThat(lowPriority.getPriority()).isEqualTo(TaskPriority.NORMAL);
        assertThat(normalPriority.getPriority()).isEqualTo(TaskPriority.NORMAL);
        assertThat(highPriority.getPriority()).isEqualTo(TaskPriority.HIGH);
    }
    
    @Test
    @DisplayName("Should verify task idempotency flags")
    void testTaskIdempotency() {
        Task dataImport = new DataImportTask("T001", "data.csv", 100);
        Task report = new ReportGenerationTask("T002", "Report", LocalDate.now());
        Task email = new EmailNotificationTask("T003", "admin@company.com", "Alert");
        Task apiGet = new ApiCallTask("T004", "/api/users", "GET");
        Task apiPost = new ApiCallTask("T005", "/api/users", "POST");
        
        assertThat(dataImport.isIdempotent()).isTrue();
        assertThat(report.isIdempotent()).isTrue();
        assertThat(email.isIdempotent()).isFalse();  // Don't send duplicate emails
        assertThat(apiGet.isIdempotent()).isTrue();  // GET is idempotent
        assertThat(apiPost.isIdempotent()).isFalse();  // POST creates new resources
    }
    
    @Test
    @DisplayName("Should track task execution status")
    void testTaskStatusTransitions() {
        Task task = new DataImportTask("T001", "data.csv", 100);
        
        assertThat(task.getStatus()).isEqualTo(TaskStatus.PENDING);
        
        scheduler.executeTask(task);
        
        assertThat(task.getStatus()).isEqualTo(TaskStatus.COMPLETED);
    }
    
    @Test
    @DisplayName("Should track task attempt count")
    void testTaskAttemptCount() {
        Task task = new DataImportTask("T001", "data.csv", 100);
        
        assertThat(task.getAttemptCount()).isZero();
        
        scheduler.executeTask(task);
        
        assertThat(task.getAttemptCount()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Should schedule tasks in priority queue")
    void testTaskScheduling() {
        Task task1 = new DataImportTask("T001", "data1.csv", 100);
        Task task2 = new DataImportTask("T002", "data2.csv", 200);
        
        scheduler.scheduleTask(task1);
        scheduler.scheduleTask(task2);
        
        assertThat(scheduler.getPendingTaskCount()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Should execute tasks from queue")
    void testExecuteTasksFromQueue() {
        scheduler.scheduleTask(new DataImportTask("T001", "data1.csv", 100));
        scheduler.scheduleTask(new DataImportTask("T002", "data2.csv", 200));
        
        TaskResult result1 = scheduler.executeNext();
        assertThat(result1.success()).isTrue();
        assertThat(scheduler.getPendingTaskCount()).isEqualTo(1);
        
        TaskResult result2 = scheduler.executeNext();
        assertThat(result2.success()).isTrue();
        assertThat(scheduler.getPendingTaskCount()).isZero();
    }
    
    @Test
    @DisplayName("Should record execution in history")
    void testExecutionHistory() {
        Task task = new DataImportTask("T001", "data.csv", 100);
        
        scheduler.executeTask(task);
        
        List<TaskExecutionRecord> history = scheduler.getExecutionHistory();
        assertThat(history).hasSize(1);
        
        TaskExecutionRecord record = history.get(0);
        assertThat(record.getTaskId()).isEqualTo("T001");
        assertThat(record.getStatus()).isEqualTo(TaskStatus.COMPLETED);
        assertThat(record.getAttemptCount()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Should measure task execution time")
    void testExecutionTiming() {
        Task task = new DataImportTask("T001", "data.csv", 100);
        
        TaskResult result = scheduler.executeTask(task);
        
        assertThat(result.durationMillis()).isGreaterThanOrEqualTo(0);
        assertThat(result.executionTime()).isNotNull();
    }
    
    @Test
    @DisplayName("Should cancel task before execution")
    void testTaskCancellation() {
        Task task = new DataImportTask("T001", "data.csv", 100);
        
        task.cancel();
        
        assertThat(task.getStatus()).isEqualTo(TaskStatus.CANCELLED);
        
        assertThatThrownBy(() -> scheduler.executeTask(task))
                .isInstanceOf(Exception.class);
    }
    
    @Test
    @DisplayName("Should execute batch task")
    void testBatchTaskExecution() {
        BatchTask batch = new BatchTask("BATCH001", List.of(
            new DataImportTask("T001", "data1.csv", 100),
            new DataImportTask("T002", "data2.csv", 200),
            new DataImportTask("T003", "data3.csv", 300)
        ), false);
        
        TaskResult result = scheduler.executeTask(batch);
        
        assertThat(result.success()).isTrue();
        assertThat(batch.getTasks()).hasSize(3);
    }
    
    @Test
    @DisplayName("Should verify batch contains correct tasks")
    void testBatchTaskContents() {
        Task task1 = new DataImportTask("T001", "data1.csv", 100);
        Task task2 = new DataImportTask("T002", "data2.csv", 200);
        
        BatchTask batch = new BatchTask("BATCH001", List.of(task1, task2), false);
        
        assertThat(batch.getTasks()).hasSize(2);
        assertThat(batch.isStopOnFailure()).isFalse();
    }
    
    @Test
    @DisplayName("Should provide task descriptions")
    void testTaskDescriptions() {
        Task dataImport = new DataImportTask("T001", "data.csv", 1000);
        assertThat(dataImport.getDescription()).contains("Import").contains("1000").contains("data.csv");
        
        Task report = new ReportGenerationTask("T002", "Sales", LocalDate.now());
        assertThat(report.getDescription()).contains("Generate").contains("Sales");
        
        Task email = new EmailNotificationTask("T003", "admin@company.com", "Alert");
        assertThat(email.getDescription()).contains("email").contains("admin@company.com").contains("Alert");
        
        Task api = new ApiCallTask("T004", "/api/users", "GET");
        assertThat(api.getDescription()).contains("GET").contains("/api/users");
    }
    
    @Test
    @DisplayName("Should verify max retry limits")
    void testMaxRetryLimits() {
        Task dataImport = new DataImportTask("T001", "data.csv", 100);
        Task report = new ReportGenerationTask("T002", "Report", LocalDate.now());
        Task email = new EmailNotificationTask("T003", "admin@company.com", "Alert");
        Task api = new ApiCallTask("T004", "/api/users", "GET");
        
        assertThat(dataImport.getMaxRetries()).isEqualTo(3);
        assertThat(report.getMaxRetries()).isEqualTo(2);
        assertThat(email.getMaxRetries()).isEqualTo(1);
        assertThat(api.getMaxRetries()).isEqualTo(3);
    }
    
    @Test
    @DisplayName("Should verify task properties")
    void testTaskProperties() {
        DataImportTask task = new DataImportTask("T001", "customers.csv", 5000);
        
        assertThat(task.getTaskId()).isEqualTo("T001");
        assertThat(task.getSource()).isEqualTo("customers.csv");
        assertThat(task.getRecordCount()).isEqualTo(5000);
        assertThat(task.getPriority()).isEqualTo(TaskPriority.NORMAL);
        assertThat(task.isIdempotent()).isTrue();
        assertThat(task.getMaxRetries()).isEqualTo(3);
    }
    
    @Test
    @DisplayName("Should verify report task properties")
    void testReportTaskProperties() {
        LocalDate period = LocalDate.of(2026, 4, 1);
        ReportGenerationTask task = new ReportGenerationTask("T002", "Monthly Sales", period);
        
        assertThat(task.getReportType()).isEqualTo("Monthly Sales");
        assertThat(task.getPeriod()).isEqualTo(period);
    }
    
    @Test
    @DisplayName("Should verify email task properties")
    void testEmailTaskProperties() {
        EmailNotificationTask task = new EmailNotificationTask("T003", "user@example.com", "Welcome");
        
        assertThat(task.getRecipient()).isEqualTo("user@example.com");
        assertThat(task.getSubject()).isEqualTo("Welcome");
        assertThat(task.isIdempotent()).isFalse();
    }
    
    @Test
    @DisplayName("Should verify API task properties")
    void testApiTaskProperties() {
        ApiCallTask getTask = new ApiCallTask("T004", "/api/users", "GET");
        ApiCallTask postTask = new ApiCallTask("T005", "/api/users", "POST");
        
        assertThat(getTask.getEndpoint()).isEqualTo("/api/users");
        assertThat(getTask.getMethod()).isEqualTo("GET");
        assertThat(getTask.isIdempotent()).isTrue();
        
        assertThat(postTask.isIdempotent()).isFalse();
    }
    
    @Test
    @DisplayName("Should verify task result fields")
    void testTaskResultFields() {
        Task task = new DataImportTask("T001", "data.csv", 100);
        
        TaskResult result = scheduler.executeTask(task);
        
        assertThat(result.success()).isNotNull();
        assertThat(result.message()).isNotEmpty();
        assertThat(result.executionTime()).isNotNull();
        assertThat(result.durationMillis()).isGreaterThanOrEqualTo(0);
    }
    
    @Test
    @DisplayName("Should verify execution record fields")
    void testExecutionRecordFields() {
        Task task = new DataImportTask("T001", "data.csv", 100);
        scheduler.executeTask(task);
        
        TaskExecutionRecord record = scheduler.getExecutionHistory().get(0);
        
        assertThat(record.getTaskId()).isEqualTo("T001");
        assertThat(record.getDescription()).isNotEmpty();
        assertThat(record.getStatus()).isEqualTo(TaskStatus.COMPLETED);
        assertThat(record.getScheduledTime()).isNotNull();
        assertThat(record.getStartTime()).isNotNull();
        assertThat(record.getEndTime()).isNotNull();
        assertThat(record.getAttemptCount()).isEqualTo(1);
        assertThat(record.getLastResult()).isNotNull();
    }
    
    @Test
    @DisplayName("Should calculate total duration in execution record")
    void testExecutionRecordDuration() {
        Task task = new DataImportTask("T001", "data.csv", 100);
        scheduler.executeTask(task);
        
        TaskExecutionRecord record = scheduler.getExecutionHistory().get(0);
        
        assertThat(record.getTotalDurationMillis()).isGreaterThanOrEqualTo(0);
    }
    
    @Test
    @DisplayName("Should handle empty queue")
    void testEmptyQueue() {
        assertThat(scheduler.getPendingTaskCount()).isZero();
    }
    
    @Test
    @DisplayName("Should verify dead letter queue is initially empty")
    void testInitialDeadLetterQueue() {
        assertThat(scheduler.getDeadLetterQueue()).isEmpty();
    }
    
    @Test
    @DisplayName("Should verify batch task description")
    void testBatchTaskDescription() {
        BatchTask batch = new BatchTask("BATCH001", List.of(
            new DataImportTask("T001", "data.csv", 100),
            new DataImportTask("T002", "data.csv", 200)
        ), false);
        
        assertThat(batch.getDescription()).contains("Batch").contains("2");
    }
    
    @Test
    @DisplayName("Should execute multiple tasks sequentially")
    void testMultipleTaskExecution() {
        scheduler.scheduleTask(new DataImportTask("T001", "data1.csv", 100));
        scheduler.scheduleTask(new DataImportTask("T002", "data2.csv", 200));
        scheduler.scheduleTask(new DataImportTask("T003", "data3.csv", 300));
        
        scheduler.executeNext();
        scheduler.executeNext();
        scheduler.executeNext();
        
        assertThat(scheduler.getPendingTaskCount()).isZero();
        assertThat(scheduler.getExecutionHistory()).hasSize(3);
    }
    
    @Test
    @DisplayName("Should handle batch with stop on failure flag")
    void testBatchStopOnFailure() {
        BatchTask batch = new BatchTask("BATCH001", List.of(
            new DataImportTask("T001", "data.csv", 100)
        ), true);
        
        assertThat(batch.isStopOnFailure()).isTrue();
    }
    
    @Test
    @DisplayName("Should verify empty execution history initially")
    void testInitialExecutionHistory() {
        assertThat(scheduler.getExecutionHistory()).isEmpty();
    }
    
    @Test
    @DisplayName("Should handle task status enumeration")
    void testTaskStatusValues() {
        assertThat(TaskStatus.values()).contains(
            TaskStatus.PENDING,
            TaskStatus.RUNNING,
            TaskStatus.COMPLETED,
            TaskStatus.FAILED,
            TaskStatus.CANCELLED,
            TaskStatus.RETRYING
        );
    }
    
    @Test
    @DisplayName("Should handle task priority enumeration")
    void testTaskPriorityValues() {
        assertThat(TaskPriority.values()).contains(
            TaskPriority.LOW,
            TaskPriority.NORMAL,
            TaskPriority.HIGH,
            TaskPriority.URGENT
        );
    }
}
