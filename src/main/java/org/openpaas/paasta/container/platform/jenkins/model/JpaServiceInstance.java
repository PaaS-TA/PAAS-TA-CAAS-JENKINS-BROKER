package org.openpaas.paasta.container.platform.jenkins.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.openpaas.servicebroker.model.CreateServiceInstanceRequest;
import org.openpaas.servicebroker.model.ServiceInstance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "service_instance")
public class JpaServiceInstance extends ServiceInstance {

    @JsonSerialize
    @JsonProperty("service_instance_id")
    @Id
    @NotNull
    @Column(name = "service_instance_id")
    private String serviceInstanceId;

    @JsonSerialize
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private String userId;

    @JsonSerialize
    @JsonProperty("caas_account_token_name")
    @Column(name = "caas_account_token_name")
    private String caasAccountTokenName;

    @JsonSerialize
    @JsonProperty("caas_account_name")
    @Column(name = "caas_account_name")
    private String caasAccountName;

    @JsonSerialize
    @JsonProperty("caas_namespace")
    @Column(name = "caas_namespace")
    private String caasNamespace;

    @JsonSerialize
    @Column(name = "dashboard_url")
    @JsonProperty("dashboard_url")
    private String dashboardUrl;

    @JsonSerialize
    @JsonProperty("organization_guid")
    @Column(name = "organization_guid")
    private String organizationGuid;

    @JsonSerialize
    @Column(name = "plan_id")
    @JsonProperty("plan_id")
    private String planId;

    @JsonSerialize
    @JsonProperty("service_definition_id")
    @Column(name = "service_definition_id")
    private String serviceDefinitionId;

    @JsonSerialize
    @JsonProperty("space_guid")
    @Column(name = "space_guid")
    private String spaceGuid;

    public JpaServiceInstance() {
         super();
    }

    public JpaServiceInstance(CreateServiceInstanceRequest request) {
         super(request);
    }




    @Override
    public String getServiceInstanceId() {
        return serviceInstanceId;
    }

    public void setServiceInstanceId(String serviceInstanceId) {
        this.serviceInstanceId = serviceInstanceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCaasAccountTokenName() {
        return caasAccountTokenName;
    }

    public void setCaasAccountTokenName(String caasAccountTokenName) {
        this.caasAccountTokenName = caasAccountTokenName;
    }

    public String getCaasAccountName() {
        return caasAccountName;
    }

    public void setCaasAccountName(String caasAccountName) {
        this.caasAccountName = caasAccountName;
    }

    public String getCaasNamespace() {
        return caasNamespace;
    }

    public void setCaasNamespace(String caasNamespace) {
        this.caasNamespace = caasNamespace;
    }

    @Override
    public String getDashboardUrl() {
        return dashboardUrl;
    }

    public void setDashboardUrl(String dashboardUrl) {
        this.dashboardUrl = dashboardUrl;
    }

    @Override
    public String getOrganizationGuid() {
        return organizationGuid;
    }

    public void setOrganizationGuid(String organizationGuid) {
        this.organizationGuid = organizationGuid;
    }

    @Override
    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    @Override
    public String getServiceDefinitionId() {
        return serviceDefinitionId;
    }

    public void setServiceDefinitionId(String serviceDefinitionId) {
        this.serviceDefinitionId = serviceDefinitionId;
    }

    @Override
    public String getSpaceGuid() {
        return spaceGuid;
    }

    public void setSpaceGuid(String spaceGuid) {
        this.spaceGuid = spaceGuid;
    }
}