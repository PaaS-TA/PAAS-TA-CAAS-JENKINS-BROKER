package org.openpaas.paasta.container.platform.jenkins.model.k8s;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

public class KubeDeploymentConditionV1 {
    @SerializedName("lastTransitionTime")
    private String lastTransitionTime = null;
    @SerializedName("lastUpdateTime")
    private String lastUpdateTime = null;
    @SerializedName("message")
    private String message = null;
    @SerializedName("reason")
    private String reason = null;
    @SerializedName("status")
    private String status = null;
    @SerializedName("type")
    private String type = null;

    public KubeDeploymentConditionV1() {
    }

    public KubeDeploymentConditionV1 lastTransitionTime(String lastTransitionTime) {
        this.lastTransitionTime = lastTransitionTime;
        return this;
    }

    @ApiModelProperty("Last time the condition transitioned from one status to another.")
    public String getLastTransitionTime() {
        return this.lastTransitionTime;
    }

    public void setLastTransitionTime(String lastTransitionTime) {
        this.lastTransitionTime = lastTransitionTime;
    }

    public KubeDeploymentConditionV1 lastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    @ApiModelProperty("The last time this condition was updated.")
    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public KubeDeploymentConditionV1 message(String message) {
        this.message = message;
        return this;
    }

    @ApiModelProperty("A human readable message indicating details about the transition.")
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public KubeDeploymentConditionV1 reason(String reason) {
        this.reason = reason;
        return this;
    }

    @ApiModelProperty("The reason for the condition's last transition.")
    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public KubeDeploymentConditionV1 status(String status) {
        this.status = status;
        return this;
    }

    @ApiModelProperty(
            required = true,
            value = "Status of the condition, one of True, False, Unknown."
    )
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public KubeDeploymentConditionV1 type(String type) {
        this.type = type;
        return this;
    }

    @ApiModelProperty(
            required = true,
            value = "Type of deployment condition."
    )
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            KubeDeploymentConditionV1 appsV1beta1DeploymentCondition = (KubeDeploymentConditionV1)o;
            return Objects.equals(this.lastTransitionTime, appsV1beta1DeploymentCondition.lastTransitionTime) && Objects.equals(this.lastUpdateTime, appsV1beta1DeploymentCondition.lastUpdateTime) && Objects.equals(this.message, appsV1beta1DeploymentCondition.message) && Objects.equals(this.reason, appsV1beta1DeploymentCondition.reason) && Objects.equals(this.status, appsV1beta1DeploymentCondition.status) && Objects.equals(this.type, appsV1beta1DeploymentCondition.type);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.lastTransitionTime, this.lastUpdateTime, this.message, this.reason, this.status, this.type});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AppsV1beta1DeploymentCondition {\n");
        sb.append("    lastTransitionTime: ").append(this.toIndentedString(this.lastTransitionTime)).append("\n");
        sb.append("    lastUpdateTime: ").append(this.toIndentedString(this.lastUpdateTime)).append("\n");
        sb.append("    message: ").append(this.toIndentedString(this.message)).append("\n");
        sb.append("    reason: ").append(this.toIndentedString(this.reason)).append("\n");
        sb.append("    status: ").append(this.toIndentedString(this.status)).append("\n");
        sb.append("    type: ").append(this.toIndentedString(this.type)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }

}
