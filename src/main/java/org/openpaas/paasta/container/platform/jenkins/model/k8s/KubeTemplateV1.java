package org.openpaas.paasta.container.platform.jenkins.model.k8s;

import com.google.gson.annotations.SerializedName;
import io.kubernetes.client.models.V1PodSpec;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

public class KubeTemplateV1 {
    @SerializedName("metadata")
    private KubeMetadataV1 metadata = null;
    @SerializedName("spec")
    private V1PodSpec spec = null;

    public KubeTemplateV1() {
    }

    public KubeTemplateV1 metadata(KubeMetadataV1 metadata) {
        this.metadata = metadata;
        return this;
    }

    @ApiModelProperty("Standard object's metadata. More info: https://git.k8s.io/community/contributors/devel/api-conventions.md#metadata")
    public KubeMetadataV1 getMetadata() {
        return this.metadata;
    }

    public void setMetadata(KubeMetadataV1 metadata) {
        this.metadata = metadata;
    }

    public KubeTemplateV1 spec(V1PodSpec spec) {
        this.spec = spec;
        return this;
    }

    @ApiModelProperty("Specification of the desired behavior of the pod. More info: https://git.k8s.io/community/contributors/devel/api-conventions.md#spec-and-status")
    public V1PodSpec getSpec() {
        return this.spec;
    }

    public void setSpec(V1PodSpec spec) {
        this.spec = spec;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            KubeTemplateV1 v1PodTemplateSpec = (KubeTemplateV1)o;
            return Objects.equals(this.metadata, v1PodTemplateSpec.metadata) && Objects.equals(this.spec, v1PodTemplateSpec.spec);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.metadata, this.spec});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class V1PodTemplateSpec {\n");
        sb.append("    metadata: ").append(this.toIndentedString(this.metadata)).append("\n");
        sb.append("    spec: ").append(this.toIndentedString(this.spec)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
