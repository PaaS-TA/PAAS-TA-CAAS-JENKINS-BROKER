package org.openpaas.paasta.container.platform.jenkins.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "jenkins_instance")
public class JpaJenkinsInstance {


    @JsonSerialize
    @JsonProperty("organization_guid")
    @Column(name = "organization_guid")
    private String organizationGuid;

    @JsonSerialize
    @JsonProperty("name_space")
    @Column(name = "name_space")
    private String namespace;

    @JsonSerialize
    @JsonProperty("service_instance_id")
    @Id
    @NotNull
    @Column(name = "service_instance_id")
    private String serviceInstanceId;

    public JpaJenkinsInstance(){
    }

    public JpaJenkinsInstance(String organizationGuid, String serviceInstanceId, String namespace){
        this.organizationGuid = organizationGuid;
        this.serviceInstanceId = serviceInstanceId;
        this.namespace = namespace;
    }

    public String getOrganizationGuid() {
        return organizationGuid;
    }

    public void setOrganizationGuid(String organizationGuid) {
        this.organizationGuid = organizationGuid;
    }


    public String getServiceInstanceId() {
        return serviceInstanceId;
    }

    public void setServiceInstanceId(String serviceInstanceId) {
        this.serviceInstanceId = serviceInstanceId;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
