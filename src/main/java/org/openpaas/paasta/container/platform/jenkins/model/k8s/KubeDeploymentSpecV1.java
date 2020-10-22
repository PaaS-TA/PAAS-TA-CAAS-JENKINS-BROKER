package org.openpaas.paasta.container.platform.jenkins.model.k8s;

import com.google.gson.annotations.SerializedName;
import io.kubernetes.client.models.AppsV1beta1DeploymentStrategy;
import io.kubernetes.client.models.AppsV1beta1RollbackConfig;
import io.kubernetes.client.models.V1LabelSelector;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

public class KubeDeploymentSpecV1 {

    @SerializedName("minReadySeconds")
    private Integer minReadySeconds = null;
    @SerializedName("paused")
    private Boolean paused = null;
    @SerializedName("progressDeadlineSeconds")
    private Integer progressDeadlineSeconds = null;
    @SerializedName("replicas")
    private Integer replicas = null;
    @SerializedName("revisionHistoryLimit")
    private Integer revisionHistoryLimit = null;
    @SerializedName("rollbackTo")
    private AppsV1beta1RollbackConfig rollbackTo = null;
    @SerializedName("selector")
    private V1LabelSelector selector = null;
    @SerializedName("strategy")
    private AppsV1beta1DeploymentStrategy strategy = null;
    @SerializedName("template")
    private KubeTemplateV1 template = null;

    public KubeDeploymentSpecV1() {
    }

    public KubeDeploymentSpecV1 minReadySeconds(Integer minReadySeconds) {
        this.minReadySeconds = minReadySeconds;
        return this;
    }

    @ApiModelProperty("Minimum number of seconds for which a newly created pod should be ready without any of its container crashing, for it to be considered available. Defaults to 0 (pod will be considered available as soon as it is ready)")
    public Integer getMinReadySeconds() {
        return this.minReadySeconds;
    }

    public void setMinReadySeconds(Integer minReadySeconds) {
        this.minReadySeconds = minReadySeconds;
    }

    public KubeDeploymentSpecV1 paused(Boolean paused) {
        this.paused = paused;
        return this;
    }

    @ApiModelProperty("Indicates that the deployment is paused.")
    public Boolean isPaused() {
        return this.paused;
    }

    public void setPaused(Boolean paused) {
        this.paused = paused;
    }

    public KubeDeploymentSpecV1 progressDeadlineSeconds(Integer progressDeadlineSeconds) {
        this.progressDeadlineSeconds = progressDeadlineSeconds;
        return this;
    }

    @ApiModelProperty("The maximum time in seconds for a deployment to make progress before it is considered to be failed. The deployment controller will continue to process failed deployments and a condition with a ProgressDeadlineExceeded reason will be surfaced in the deployment status. Note that progress will not be estimated during the time a deployment is paused. Defaults to 600s.")
    public Integer getProgressDeadlineSeconds() {
        return this.progressDeadlineSeconds;
    }

    public void setProgressDeadlineSeconds(Integer progressDeadlineSeconds) {
        this.progressDeadlineSeconds = progressDeadlineSeconds;
    }

    public KubeDeploymentSpecV1 replicas(Integer replicas) {
        this.replicas = replicas;
        return this;
    }

    @ApiModelProperty("Number of desired pods. This is a pointer to distinguish between explicit zero and not specified. Defaults to 1.")
    public Integer getReplicas() {
        return this.replicas;
    }

    public void setReplicas(Integer replicas) {
        this.replicas = replicas;
    }

    public KubeDeploymentSpecV1 revisionHistoryLimit(Integer revisionHistoryLimit) {
        this.revisionHistoryLimit = revisionHistoryLimit;
        return this;
    }

    @ApiModelProperty("The number of old ReplicaSets to retain to allow rollback. This is a pointer to distinguish between explicit zero and not specified. Defaults to 2.")
    public Integer getRevisionHistoryLimit() {
        return this.revisionHistoryLimit;
    }

    public void setRevisionHistoryLimit(Integer revisionHistoryLimit) {
        this.revisionHistoryLimit = revisionHistoryLimit;
    }

    public KubeDeploymentSpecV1 rollbackTo(AppsV1beta1RollbackConfig rollbackTo) {
        this.rollbackTo = rollbackTo;
        return this;
    }

    @ApiModelProperty("DEPRECATED. The config this deployment is rolling back to. Will be cleared after rollback is done.")
    public AppsV1beta1RollbackConfig getRollbackTo() {
        return this.rollbackTo;
    }

    public void setRollbackTo(AppsV1beta1RollbackConfig rollbackTo) {
        this.rollbackTo = rollbackTo;
    }

    public KubeDeploymentSpecV1 selector(V1LabelSelector selector) {
        this.selector = selector;
        return this;
    }

    @ApiModelProperty("Label selector for pods. Existing ReplicaSets whose pods are selected by this will be the ones affected by this deployment.")
    public V1LabelSelector getSelector() {
        return this.selector;
    }

    public void setSelector(V1LabelSelector selector) {
        this.selector = selector;
    }

    public KubeDeploymentSpecV1 strategy(AppsV1beta1DeploymentStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    @ApiModelProperty("The deployment strategy to use to replace existing pods with new ones.")
    public AppsV1beta1DeploymentStrategy getStrategy() {
        return this.strategy;
    }

    public void setStrategy(AppsV1beta1DeploymentStrategy strategy) {
        this.strategy = strategy;
    }

    public KubeDeploymentSpecV1 template(KubeTemplateV1 template) {
        this.template = template;
        return this;
    }

    @ApiModelProperty(
            required = true,
            value = "Template describes the pods that will be created."
    )
    public KubeTemplateV1 getTemplate() {
        return this.template;
    }

    public void setTemplate(KubeTemplateV1 template) {
        this.template = template;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            KubeDeploymentSpecV1 KubeDeploymentSpecV1 = (KubeDeploymentSpecV1)o;
            return Objects.equals(this.minReadySeconds, KubeDeploymentSpecV1.minReadySeconds) && Objects.equals(this.paused, KubeDeploymentSpecV1.paused) && Objects.equals(this.progressDeadlineSeconds, KubeDeploymentSpecV1.progressDeadlineSeconds) && Objects.equals(this.replicas, KubeDeploymentSpecV1.replicas) && Objects.equals(this.revisionHistoryLimit, KubeDeploymentSpecV1.revisionHistoryLimit) && Objects.equals(this.rollbackTo, KubeDeploymentSpecV1.rollbackTo) && Objects.equals(this.selector, KubeDeploymentSpecV1.selector) && Objects.equals(this.strategy, KubeDeploymentSpecV1.strategy) && Objects.equals(this.template, KubeDeploymentSpecV1.template);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.minReadySeconds, this.paused, this.progressDeadlineSeconds, this.replicas, this.revisionHistoryLimit, this.rollbackTo, this.selector, this.strategy, this.template});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class KubeDeploymentSpec {\n");
        sb.append("    minReadySeconds: ").append(this.toIndentedString(this.minReadySeconds)).append("\n");
        sb.append("    paused: ").append(this.toIndentedString(this.paused)).append("\n");
        sb.append("    progressDeadlineSeconds: ").append(this.toIndentedString(this.progressDeadlineSeconds)).append("\n");
        sb.append("    replicas: ").append(this.toIndentedString(this.replicas)).append("\n");
        sb.append("    revisionHistoryLimit: ").append(this.toIndentedString(this.revisionHistoryLimit)).append("\n");
        sb.append("    rollbackTo: ").append(this.toIndentedString(this.rollbackTo)).append("\n");
        sb.append("    selector: ").append(this.toIndentedString(this.selector)).append("\n");
        sb.append("    strategy: ").append(this.toIndentedString(this.strategy)).append("\n");
        sb.append("    template: ").append(this.toIndentedString(this.template)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
