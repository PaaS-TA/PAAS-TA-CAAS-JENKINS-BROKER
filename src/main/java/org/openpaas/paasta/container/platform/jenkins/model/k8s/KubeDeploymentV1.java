package org.openpaas.paasta.container.platform.jenkins.model.k8s;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

public class KubeDeploymentV1 {

    @SerializedName("apiVersion")
    private String apiVersion = null;
    @SerializedName("kind")
    private String kind = null;
    @SerializedName("metadata")
    private KubeMetadataV1 metadata = null;
    @SerializedName("spec")
    private KubeDeploymentSpecV1 spec = null;
    @SerializedName("status")
    private KubeDeploymentStatusV1 status = null;

    public KubeDeploymentV1() {
    }

    public KubeDeploymentV1 apiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    @ApiModelProperty("APIVersion defines the versioned schema of this representation of an object. Servers should convert recognized schemas to the latest internal value, and may reject unrecognized values. More info: https://git.k8s.io/community/contributors/devel/api-conventions.md#resources")
    public String getApiVersion() {
        return this.apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public KubeDeploymentV1 kind(String kind) {
        this.kind = kind;
        return this;
    }

    @ApiModelProperty("Kind is a string value representing the REST resource this object represents. Servers may infer this from the endpoint the client submits requests to. Cannot be updated. In CamelCase. More info: https://git.k8s.io/community/contributors/devel/api-conventions.md#types-kinds")
    public String getKind() {
        return this.kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public KubeDeploymentV1 metadata(KubeMetadataV1 metadata) {
        this.metadata = metadata;
        return this;
    }

    @ApiModelProperty("Standard object metadata.")
    public KubeMetadataV1 getMetadata() {
        return this.metadata;
    }

    public void setMetadata(KubeMetadataV1 metadata) {
        this.metadata = metadata;
    }

    public KubeDeploymentV1 spec(KubeDeploymentSpecV1 spec) {
        this.spec = spec;
        return this;
    }

    @ApiModelProperty("Specification of the desired behavior of the Deployment.")
    public KubeDeploymentSpecV1 getSpec() {
        return this.spec;
    }

    public void setSpec(KubeDeploymentSpecV1 spec) {
        this.spec = spec;
    }

    public KubeDeploymentV1 status(KubeDeploymentStatusV1 status) {
        this.status = status;
        return this;
    }

    @ApiModelProperty("Most recently observed status of the Deployment.")
    public KubeDeploymentStatusV1 getStatus() {
        return this.status;
    }

    public void setStatus(KubeDeploymentStatusV1 status) {
        this.status = status;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            KubeDeploymentV1 KubeDeploymentV1 = (KubeDeploymentV1)o;
            return Objects.equals(this.apiVersion, KubeDeploymentV1.apiVersion) && Objects.equals(this.kind, KubeDeploymentV1.kind) && Objects.equals(this.metadata, KubeDeploymentV1.metadata) && Objects.equals(this.spec, KubeDeploymentV1.spec) && Objects.equals(this.status, KubeDeploymentV1.status);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.apiVersion, this.kind, this.metadata, this.spec, this.status});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class KubeDeploymentV1 {\n");
        sb.append("    apiVersion: ").append(this.toIndentedString(this.apiVersion)).append("\n");
        sb.append("    kind: ").append(this.toIndentedString(this.kind)).append("\n");
        sb.append("    metadata: ").append(this.toIndentedString(this.metadata)).append("\n");
        sb.append("    spec: ").append(this.toIndentedString(this.spec)).append("\n");
        sb.append("    status: ").append(this.toIndentedString(this.status)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }

}
