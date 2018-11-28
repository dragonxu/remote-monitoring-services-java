// Copyright (c) Microsoft. All rights reserved.

package com.microsoft.azure.iotsolutions.iothubmanager.services.models;

import com.microsoft.azure.iotsolutions.iothubmanager.services.exceptions.ExternalDependencyException;
import com.microsoft.azure.sdk.iot.service.jobs.*;

import java.util.*;

public class JobServiceModel {

    private String jobId;
    private String queryCondition;
    private Date createdTimeUtc;
    private Date startTimeUtc;
    private Date endTimeUtc;
    private Long maxExecutionTimeInSeconds;
    private JobType jobType;
    private JobStatus jobStatus;
    private MethodParameterServiceModel methodParameter;
    private TwinServiceModel updateTwin;
    private String failureReason;
    private String statusMessage;
    private JobStatistics resultStatistics;
    private List<DeviceJobServiceModel> devices;

    public JobServiceModel() {}

    public JobServiceModel(JobResult jobResult, List<JobResult> deviceJobs) throws ExternalDependencyException {
        this.jobId = jobResult.getJobId();
        this.queryCondition = jobResult.getQueryCondition();
        this.createdTimeUtc = jobResult.getCreatedTime();
        this.startTimeUtc = jobResult.getStartTime();
        this.endTimeUtc = jobResult.getEndTime();
        this.maxExecutionTimeInSeconds = jobResult.getMaxExecutionTimeInSeconds();
        this.jobType = JobType.fromAzureJobType(jobResult.getJobType());
        this.jobStatus = JobStatus.fromAzureJobStatus(jobResult.getJobStatus());

        if (jobResult.getCloudToDeviceMethod() != null) {
            this.methodParameter = new MethodParameterServiceModel(jobResult.getCloudToDeviceMethod());
        }

        if (jobResult.getUpdateTwin() != null) {
            this.updateTwin = new TwinServiceModel(jobResult.getUpdateTwin());
        }

        this.failureReason = jobResult.getFailureReason();
        this.statusMessage = jobResult.getStatusMessage();

        this.resultStatistics = new JobStatistics(jobResult.getJobStatistics());

        if (deviceJobs == null) {
            this.devices = null;
        } else {
            this.devices = new ArrayList<>();
            for(JobResult job : deviceJobs) {
                this.devices.add(new DeviceJobServiceModel(job));
            }
        }
    }

    public String getJobId() {
        return this.jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getQueryCondition() {
        return this.queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    public Date getCreatedTimeUtc() {
        return this.createdTimeUtc;
    }

    public void setCreatedTimeUtc(Date createdTimeUtc) {
        this.createdTimeUtc = createdTimeUtc;
    }

    public Date getStartTimeUtc() {
        return this.startTimeUtc;
    }

    public void setStartTimeUtc(Date startTimeUtc) {
        this.startTimeUtc = startTimeUtc;
    }

    public Date getEndTimeUtc() {
        // IoT Hub will return a date of 12/30/9999 if job hasn't completed yet.
        // Return end time only if job is complete.
        if (this.jobStatus == JobStatus.completed) {
            return this.endTimeUtc;
        }
        return null;
    }

    public void setEndTimeUtc(Date endTimeUtc) {
        this.endTimeUtc = endTimeUtc;
    }

    public Long getMaxExecutionTimeInSeconds() {
        return this.maxExecutionTimeInSeconds;
    }

    public void setMaxExecutionTimeInSeconds(Long maxExecutionTimeInSeconds) {
        this.maxExecutionTimeInSeconds = maxExecutionTimeInSeconds;
    }

    public JobType getJobType() {
        return this.jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public JobStatus getJobStatus() {
        return this.jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public MethodParameterServiceModel getMethodParameter() {
        return methodParameter;
    }

    public void setMethodParameter(MethodParameterServiceModel methodParameter) {
        this.methodParameter = methodParameter;
    }

    public TwinServiceModel getUpdateTwin() {
        return updateTwin;
    }

    public void setUpdateTwin(TwinServiceModel updateTwin) {
        this.updateTwin = updateTwin;
    }

    public String getFailureReason() {
        return this.failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public JobStatistics getResultStatistics() {
        return this.resultStatistics;
    }

    public void setResultStatistics(JobStatistics resultStatistics) {
        this.resultStatistics = resultStatistics;
    }

    public List<DeviceJobServiceModel> getDevices() {
        return this.devices;
    }
}
