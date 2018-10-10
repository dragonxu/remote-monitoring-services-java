// Copyright (c) Microsoft. All rights reserved.

package com.microsoft.azure.iotsolutions.uiconfig.services.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

public class Package {
    private String id;
    private String name;
    private PackageType type;
    private String content;
    private String dateCreated;

    public Package() {
    }

    public Package(String id, String name, PackageType type, String content) {
        this(id, name, type, content,StringUtils.EMPTY);
    }

    public Package(String id, String name, PackageType type, String content, String dateCreated) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.content = content;
        this.dateCreated = dateCreated;
    }

    @JsonProperty("Id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("Type")
    public PackageType getType() {
        return type;
    }

    public void setType(PackageType type) {
        this.type = type;
    }

    @JsonProperty("Content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonProperty("DateCreated")
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}