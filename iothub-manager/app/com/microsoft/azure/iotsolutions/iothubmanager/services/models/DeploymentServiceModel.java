// Copyright (c) Microsoft. All rights reserved.

package com.microsoft.azure.iotsolutions.iothubmanager.services.models;

import com.microsoft.azure.iotsolutions.iothubmanager.services.exceptions.InvalidInputException;
import com.microsoft.azure.sdk.iot.service.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class DeploymentServiceModel {

    private final static String DEPLOYMENT_NAME_LABEL = "Name";
    private final static String DEPLOYMENT_GROUP_ID_LABEL = "DeviceGroupId";
    private final static String DATE_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private String id;
    private String name;
    private String packageContent;
    private String deviceGroupId;
    private String deviceGroupQuery;
    private String createdDateTimeUtc;
    private int priority;
    private DeploymentType type;
    private DeploymentMetrics deploymentMetrics;

    public DeploymentServiceModel(final String name,
                                  final String deviceGroupId,
                                  final String deviceGroupQuery,
                                  final String packageContent,
                                  final int priority,
                                  final DeploymentType type) {
        this.deviceGroupId = deviceGroupId;
        this.deviceGroupQuery = deviceGroupQuery;
        this.packageContent = packageContent;
        this.name = name;
        this.priority = priority;
        this.type = type;
    }
    public DeploymentServiceModel(Configuration config) throws InvalidInputException {
        if (StringUtils.isEmpty(config.getId())) {
            throw new InvalidInputException("Invalid id provided");
        }

        if (!config.getLabels().containsKey(DEPLOYMENT_GROUP_ID_LABEL)) {
            throw new InvalidInputException("Configuration is missing necessary label " + DEPLOYMENT_GROUP_ID_LABEL);
        }

        if (!config.getLabels().containsKey(DEPLOYMENT_NAME_LABEL)) {
            throw new InvalidInputException("Configuration is missing necessary label " + DEPLOYMENT_NAME_LABEL);
        }

        this.id = config.getId();
        this.name = config.getLabels().get(DEPLOYMENT_NAME_LABEL);
        this.deviceGroupId = config.getLabels().get(DEPLOYMENT_GROUP_ID_LABEL);
        this.createdDateTimeUtc =  this.formatDateTimeToUTC(config.getCreatedTimeUtc());
        this.priority = config.getPriority();
        this.type = DeploymentType.edgeManifest;
        this.deploymentMetrics = new DeploymentMetrics(config.getSystemMetrics(), config.getMetrics());
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPackageContent() {
        return this.packageContent;
    }

    public String getDeviceGroupId() {
        return this.deviceGroupId;
    }

    public String getDeviceGroupQuery() { return this.deviceGroupQuery; }

    public String getCreatedDateTimeUtc() {
        return this.createdDateTimeUtc;
    }

    public int getPriority() {
        return this.priority;
    }

    public DeploymentType getType() {
        return this.type;
    }

    public DeploymentMetrics getDeploymentMetrics() {
        return this.deploymentMetrics;
    }

    private String formatDateTimeToUTC(String originalTime) {
        DateTime dateTime = DateTime.parse(originalTime);

        // TODO: Remove workaround for Java SDK bug not returning time in UTC.
        try {
            dateTime = dateTime.toDateTime(DateTimeZone.UTC);
        } catch(Exception ex) {
            // Swallow exception and use date as-is.
        }

        return dateTime.toString(DATE_FORMAT_STRING);
    }
}
