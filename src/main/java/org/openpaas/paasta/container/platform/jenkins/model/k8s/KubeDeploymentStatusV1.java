package org.openpaas.paasta.container.platform.jenkins.model.k8s;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KubeDeploymentStatusV1 {
    @SerializedName("availableReplicas")
    private Integer availableReplicas = null;
    @SerializedName("collisionCount")
    private Integer collisionCount = null;
    @SerializedName("conditions")
    private List<KubeDeploymentConditionV1> conditions = null;
    @SerializedName("observedGeneration")
    private Long observedGeneration = null;
    @SerializedName("readyReplicas")
    private Integer readyReplicas = null;
    @SerializedName("replicas")
    private Integer replicas = null;
    @SerializedName("unavailableReplicas")
    private Integer unavailableReplicas = null;
    @SerializedName("updatedReplicas")
    private Integer updatedReplicas = null;

    public KubeDeploymentStatusV1() {
    }

    public KubeDeploymentStatusV1 availableReplicas(Integer availableReplicas) {
        this.availableReplicas = availableReplicas;
        return this;
    }

    @ApiModelProperty("Total number of available pods (ready for at least minReadySeconds) targeted by this deployment.")
    public Integer getAvailableReplicas() {
        return this.availableReplicas;
    }

    public void setAvailableReplicas(Integer availableReplicas) {
        this.availableReplicas = availableReplicas;
    }

    public KubeDeploymentStatusV1 collisionCount(Integer collisionCount) {
        this.collisionCount = collisionCount;
        return this;
    }

    @ApiModelProperty("Count of hash collisions for the Deployment. The Deployment controller uses this field as a collision avoidance mechanism when it needs to create the name for the newest ReplicaSet.")
    public Integer getCollisionCount() {
        return this.collisionCount;
    }

    public void setCollisionCount(Integer collisionCount) {
        this.collisionCount = collisionCount;
    }

    public KubeDeploymentStatusV1 conditions(List<KubeDeploymentConditionV1> conditions) {
        this.conditions = conditions;
        return this;
    }

    public KubeDeploymentStatusV1 addConditionsItem(KubeDeploymentConditionV1 conditionsItem) {
        if (this.conditions == null) {
            this.conditions = new ArrayList();
        }

        this.conditions.add(conditionsItem);
        return this;
    }

    @ApiModelProperty("Represents the latest available observations of a deployment's current state.")
    public List<KubeDeploymentConditionV1> getConditions() {
        return this.conditions;
    }

    public void setConditions(List<KubeDeploymentConditionV1> conditions) {
        this.conditions = conditions;
    }

    public KubeDeploymentStatusV1 observedGeneration(Long observedGeneration) {
        this.observedGeneration = observedGeneration;
        return this;
    }

    @ApiModelProperty("The generation observed by the deployment controller.")
    public Long getObservedGeneration() {
        return this.observedGeneration;
    }

    public void setObservedGeneration(Long observedGeneration) {
        this.observedGeneration = observedGeneration;
    }

    public KubeDeploymentStatusV1 readyReplicas(Integer readyReplicas) {
        this.readyReplicas = readyReplicas;
        return this;
    }

    @ApiModelProperty("Total number of ready pods targeted by this deployment.")
    public Integer getReadyReplicas() {
        return this.readyReplicas;
    }

    public void setReadyReplicas(Integer readyReplicas) {
        this.readyReplicas = readyReplicas;
    }

    public KubeDeploymentStatusV1 replicas(Integer replicas) {
        this.replicas = replicas;
        return this;
    }

    @ApiModelProperty("Total number of non-terminated pods targeted by this deployment (their labels match the selector).")
    public Integer getReplicas() {
        return this.replicas;
    }

    public void setReplicas(Integer replicas) {
        this.replicas = replicas;
    }

    public KubeDeploymentStatusV1 unavailableReplicas(Integer unavailableReplicas) {
        this.unavailableReplicas = unavailableReplicas;
        return this;
    }

    @ApiModelProperty("Total number of unavailable pods targeted by this deployment. This is the total number of pods that are still required for the deployment to have 100% available capacity. They may either be pods that are running but not yet available or pods that still have not been created.")
    public Integer getUnavailableReplicas() {
        return this.unavailableReplicas;
    }

    public void setUnavailableReplicas(Integer unavailableReplicas) {
        this.unavailableReplicas = unavailableReplicas;
    }

    public KubeDeploymentStatusV1 updatedReplicas(Integer updatedReplicas) {
        this.updatedReplicas = updatedReplicas;
        return this;
    }

    @ApiModelProperty("Total number of non-terminated pods targeted by this deployment that have the desired template spec.")
    public Integer getUpdatedReplicas() {
        return this.updatedReplicas;
    }

    public void setUpdatedReplicas(Integer updatedReplicas) {
        this.updatedReplicas = updatedReplicas;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            KubeDeploymentStatusV1 KubeDeploymentStatusV1 = (KubeDeploymentStatusV1)o;
            return Objects.equals(this.availableReplicas, KubeDeploymentStatusV1.availableReplicas) && Objects.equals(this.collisionCount, KubeDeploymentStatusV1.collisionCount) && Objects.equals(this.conditions, KubeDeploymentStatusV1.conditions) && Objects.equals(this.observedGeneration, KubeDeploymentStatusV1.observedGeneration) && Objects.equals(this.readyReplicas, KubeDeploymentStatusV1.readyReplicas) && Objects.equals(this.replicas, KubeDeploymentStatusV1.replicas) && Objects.equals(this.unavailableReplicas, KubeDeploymentStatusV1.unavailableReplicas) && Objects.equals(this.updatedReplicas, KubeDeploymentStatusV1.updatedReplicas);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.availableReplicas, this.collisionCount, this.conditions, this.observedGeneration, this.readyReplicas, this.replicas, this.unavailableReplicas, this.updatedReplicas});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class KubeDeploymentStatusV1 {\n");
        sb.append("    availableReplicas: ").append(this.toIndentedString(this.availableReplicas)).append("\n");
        sb.append("    collisionCount: ").append(this.toIndentedString(this.collisionCount)).append("\n");
        sb.append("    conditions: ").append(this.toIndentedString(this.conditions)).append("\n");
        sb.append("    observedGeneration: ").append(this.toIndentedString(this.observedGeneration)).append("\n");
        sb.append("    readyReplicas: ").append(this.toIndentedString(this.readyReplicas)).append("\n");
        sb.append("    replicas: ").append(this.toIndentedString(this.replicas)).append("\n");
        sb.append("    unavailableReplicas: ").append(this.toIndentedString(this.unavailableReplicas)).append("\n");
        sb.append("    updatedReplicas: ").append(this.toIndentedString(this.updatedReplicas)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
