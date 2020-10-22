package org.openpaas.paasta.container.platform.jenkins.model.k8s;

import com.google.gson.annotations.SerializedName;
import io.kubernetes.client.models.V1Initializers;
import io.kubernetes.client.models.V1OwnerReference;
import io.swagger.annotations.ApiModelProperty;

import java.util.*;

public class KubeMetadataV1 {

    @SerializedName("annotations")
    private Map<String, String> annotations = null;
    @SerializedName("clusterName")
    private String clusterName = null;
    @SerializedName("creationTimestamp")
    private String creationTimestamp = null;
    @SerializedName("deletionGracePeriodSeconds")
    private Long deletionGracePeriodSeconds = null;
    @SerializedName("deletionTimestamp")
    private String deletionTimestamp = null;
    @SerializedName("finalizers")
    private List<String> finalizers = null;
    @SerializedName("generateName")
    private String generateName = null;
    @SerializedName("generation")
    private Long generation = null;
    @SerializedName("initializers")
    private V1Initializers initializers = null;
    @SerializedName("labels")
    private Map<String, String> labels = null;
    @SerializedName("name")
    private String name = null;
    @SerializedName("namespace")
    private String namespace = null;
    @SerializedName("ownerReferences")
    private List<V1OwnerReference> ownerReferences = null;
    @SerializedName("resourceVersion")
    private String resourceVersion = null;
    @SerializedName("selfLink")
    private String selfLink = null;
    @SerializedName("uid")
    private String uid = null;

    public KubeMetadataV1() {
    }

    public KubeMetadataV1 annotations(Map<String, String> annotations) {
        this.annotations = annotations;
        return this;
    }

    public KubeMetadataV1 putAnnotationsItem(String key, String annotationsItem) {
        if (this.annotations == null) {
            this.annotations = new HashMap();
        }

        this.annotations.put(key, annotationsItem);
        return this;
    }

    @ApiModelProperty("Annotations is an unstructured key value map stored with a resource that may be set by external tools to store and retrieve arbitrary metadata. They are not queryable and should be preserved when modifying objects. More info: http://kubernetes.io/docs/user-guide/annotations")
    public Map<String, String> getAnnotations() {
        return this.annotations;
    }

    public void setAnnotations(Map<String, String> annotations) {
        this.annotations = annotations;
    }

    public KubeMetadataV1 clusterName(String clusterName) {
        this.clusterName = clusterName;
        return this;
    }

    @ApiModelProperty("The name of the cluster which the object belongs to. This is used to distinguish resources with same name and namespace in different clusters. This field is not set anywhere right now and apiserver is going to ignore it if set in create or update request.")
    public String getClusterName() {
        return this.clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public KubeMetadataV1 creationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
        return this;
    }

    @ApiModelProperty("CreationTimestamp is a timestamp representing the server time when this object was created. It is not guaranteed to be set in happens-before order across separate operations. Clients may not set this value. It is represented in RFC3339 form and is in UTC.  Populated by the system. Read-only. Null for lists. More info: https://git.k8s.io/community/contributors/devel/api-conventions.md#metadata")
    public String getCreationTimestamp() {
        return this.creationTimestamp;
    }

    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public KubeMetadataV1 deletionGracePeriodSeconds(Long deletionGracePeriodSeconds) {
        this.deletionGracePeriodSeconds = deletionGracePeriodSeconds;
        return this;
    }

    @ApiModelProperty("Number of seconds allowed for this object to gracefully terminate before it will be removed from the system. Only set when deletionTimestamp is also set. May only be shortened. Read-only.")
    public Long getDeletionGracePeriodSeconds() {
        return this.deletionGracePeriodSeconds;
    }

    public void setDeletionGracePeriodSeconds(Long deletionGracePeriodSeconds) {
        this.deletionGracePeriodSeconds = deletionGracePeriodSeconds;
    }

    public KubeMetadataV1 deletionTimestamp(String deletionTimestamp) {
        this.deletionTimestamp = deletionTimestamp;
        return this;
    }

    @ApiModelProperty("DeletionTimestamp is RFC 3339 date and time at which this resource will be deleted. This field is set by the server when a graceful deletion is requested by the user, and is not directly settable by a client. The resource is expected to be deleted (no longer visible from resource lists, and not reachable by name) after the time in this field, once the finalizers list is empty. As long as the finalizers list contains items, deletion is blocked. Once the deletionTimestamp is set, this value may not be unset or be set further into the future, although it may be shortened or the resource may be deleted prior to this time. For example, a user may request that a pod is deleted in 30 seconds. The Kubelet will react by sending a graceful termination signal to the containers in the pod. After that 30 seconds, the Kubelet will send a hard termination signal (SIGKILL) to the container and after cleanup, remove the pod from the API. In the presence of network partitions, this object may still exist after this timestamp, until an administrator or automated process can determine the resource is fully terminated. If not set, graceful deletion of the object has not been requested.  Populated by the system when a graceful deletion is requested. Read-only. More info: https://git.k8s.io/community/contributors/devel/api-conventions.md#metadata")
    public String getDeletionTimestamp() {
        return this.deletionTimestamp;
    }

    public void setDeletionTimestamp(String deletionTimestamp) {
        this.deletionTimestamp = deletionTimestamp;
    }

    public KubeMetadataV1 finalizers(List<String> finalizers) {
        this.finalizers = finalizers;
        return this;
    }

    public KubeMetadataV1 addFinalizersItem(String finalizersItem) {
        if (this.finalizers == null) {
            this.finalizers = new ArrayList();
        }

        this.finalizers.add(finalizersItem);
        return this;
    }

    @ApiModelProperty("Must be empty before the object is deleted from the registry. Each entry is an identifier for the responsible component that will remove the entry from the list. If the deletionTimestamp of the object is non-nil, entries in this list can only be removed.")
    public List<String> getFinalizers() {
        return this.finalizers;
    }

    public void setFinalizers(List<String> finalizers) {
        this.finalizers = finalizers;
    }

    public KubeMetadataV1 generateName(String generateName) {
        this.generateName = generateName;
        return this;
    }

    @ApiModelProperty("GenerateName is an optional prefix, used by the server, to generate a unique name ONLY IF the Name field has not been provided. If this field is used, the name returned to the client will be different than the name passed. This value will also be combined with a unique suffix. The provided value has the same validation rules as the Name field, and may be truncated by the length of the suffix required to make the value unique on the server.  If this field is specified and the generated name exists, the server will NOT return a 409 - instead, it will either return 201 Created or 500 with Reason ServerTimeout indicating a unique name could not be found in the time allotted, and the client should retry (optionally after the time indicated in the Retry-After header).  Applied only if Name is not specified. More info: https://git.k8s.io/community/contributors/devel/api-conventions.md#idempotency")
    public String getGenerateName() {
        return this.generateName;
    }

    public void setGenerateName(String generateName) {
        this.generateName = generateName;
    }

    public KubeMetadataV1 generation(Long generation) {
        this.generation = generation;
        return this;
    }

    @ApiModelProperty("A sequence number representing a specific generation of the desired state. Populated by the system. Read-only.")
    public Long getGeneration() {
        return this.generation;
    }

    public void setGeneration(Long generation) {
        this.generation = generation;
    }

    public KubeMetadataV1 initializers(V1Initializers initializers) {
        this.initializers = initializers;
        return this;
    }

    @ApiModelProperty("An initializer is a controller which enforces some system invariant at object creation time. This field is a list of initializers that have not yet acted on this object. If nil or empty, this object has been completely initialized. Otherwise, the object is considered uninitialized and is hidden (in list/watch and get calls) from clients that haven't explicitly asked to observe uninitialized objects.  When an object is created, the system will populate this list with the current set of initializers. Only privileged users may set or modify this list. Once it is empty, it may not be modified further by any user.")
    public V1Initializers getInitializers() {
        return this.initializers;
    }

    public void setInitializers(V1Initializers initializers) {
        this.initializers = initializers;
    }

    public KubeMetadataV1 labels(Map<String, String> labels) {
        this.labels = labels;
        return this;
    }

    public KubeMetadataV1 putLabelsItem(String key, String labelsItem) {
        if (this.labels == null) {
            this.labels = new HashMap();
        }

        this.labels.put(key, labelsItem);
        return this;
    }

    @ApiModelProperty("Map of string keys and values that can be used to organize and categorize (scope and select) objects. May match selectors of replication controllers and services. More info: http://kubernetes.io/docs/user-guide/labels")
    public Map<String, String> getLabels() {
        return this.labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public KubeMetadataV1 name(String name) {
        this.name = name;
        return this;
    }

    @ApiModelProperty("Name must be unique within a namespace. Is required when creating resources, although some resources may allow a client to request the generation of an appropriate name automatically. Name is primarily intended for creation idempotence and configuration definition. Cannot be updated. More info: http://kubernetes.io/docs/user-guide/identifiers#names")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KubeMetadataV1 namespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    @ApiModelProperty("Namespace defines the space within each name must be unique. An empty namespace is equivalent to the \"default\" namespace, but \"default\" is the canonical representation. Not all objects are required to be scoped to a namespace - the value of this field for those objects will be empty.  Must be a DNS_LABEL. Cannot be updated. More info: http://kubernetes.io/docs/user-guide/namespaces")
    public String getNamespace() {
        return this.namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public KubeMetadataV1 ownerReferences(List<V1OwnerReference> ownerReferences) {
        this.ownerReferences = ownerReferences;
        return this;
    }

    public KubeMetadataV1 addOwnerReferencesItem(V1OwnerReference ownerReferencesItem) {
        if (this.ownerReferences == null) {
            this.ownerReferences = new ArrayList();
        }

        this.ownerReferences.add(ownerReferencesItem);
        return this;
    }

    @ApiModelProperty("List of objects depended by this object. If ALL objects in the list have been deleted, this object will be garbage collected. If this object is managed by a controller, then an entry in this list will point to this controller, with the controller field set to true. There cannot be more than one managing controller.")
    public List<V1OwnerReference> getOwnerReferences() {
        return this.ownerReferences;
    }

    public void setOwnerReferences(List<V1OwnerReference> ownerReferences) {
        this.ownerReferences = ownerReferences;
    }

    public KubeMetadataV1 resourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
        return this;
    }

    @ApiModelProperty("An opaque value that represents the internal version of this object that can be used by clients to determine when objects have changed. May be used for optimistic concurrency, change detection, and the watch operation on a resource or set of resources. Clients must treat these values as opaque and passed unmodified back to the server. They may only be valid for a particular resource or set of resources.  Populated by the system. Read-only. Value must be treated as opaque by clients and . More info: https://git.k8s.io/community/contributors/devel/api-conventions.md#concurrency-control-and-consistency")
    public String getResourceVersion() {
        return this.resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    public KubeMetadataV1 selfLink(String selfLink) {
        this.selfLink = selfLink;
        return this;
    }

    @ApiModelProperty("SelfLink is a URL representing this object. Populated by the system. Read-only.")
    public String getSelfLink() {
        return this.selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public KubeMetadataV1 uid(String uid) {
        this.uid = uid;
        return this;
    }

    @ApiModelProperty("UID is the unique in time and space value for this object. It is typically generated by the server on successful creation of a resource and is not allowed to change on PUT operations.  Populated by the system. Read-only. More info: http://kubernetes.io/docs/user-guide/identifiers#uids")
    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            KubeMetadataV1 v1ObjectMeta = (KubeMetadataV1)o;
            return Objects.equals(this.annotations, v1ObjectMeta.annotations) && Objects.equals(this.clusterName, v1ObjectMeta.clusterName) && Objects.equals(this.creationTimestamp, v1ObjectMeta.creationTimestamp) && Objects.equals(this.deletionGracePeriodSeconds, v1ObjectMeta.deletionGracePeriodSeconds) && Objects.equals(this.deletionTimestamp, v1ObjectMeta.deletionTimestamp) && Objects.equals(this.finalizers, v1ObjectMeta.finalizers) && Objects.equals(this.generateName, v1ObjectMeta.generateName) && Objects.equals(this.generation, v1ObjectMeta.generation) && Objects.equals(this.initializers, v1ObjectMeta.initializers) && Objects.equals(this.labels, v1ObjectMeta.labels) && Objects.equals(this.name, v1ObjectMeta.name) && Objects.equals(this.namespace, v1ObjectMeta.namespace) && Objects.equals(this.ownerReferences, v1ObjectMeta.ownerReferences) && Objects.equals(this.resourceVersion, v1ObjectMeta.resourceVersion) && Objects.equals(this.selfLink, v1ObjectMeta.selfLink) && Objects.equals(this.uid, v1ObjectMeta.uid);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.annotations, this.clusterName, this.creationTimestamp, this.deletionGracePeriodSeconds, this.deletionTimestamp, this.finalizers, this.generateName, this.generation, this.initializers, this.labels, this.name, this.namespace, this.ownerReferences, this.resourceVersion, this.selfLink, this.uid});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class V1ObjectMeta {\n");
        sb.append("    annotations: ").append(this.toIndentedString(this.annotations)).append("\n");
        sb.append("    clusterName: ").append(this.toIndentedString(this.clusterName)).append("\n");
        sb.append("    creationTimestamp: ").append(this.toIndentedString(this.creationTimestamp)).append("\n");
        sb.append("    deletionGracePeriodSeconds: ").append(this.toIndentedString(this.deletionGracePeriodSeconds)).append("\n");
        sb.append("    deletionTimestamp: ").append(this.toIndentedString(this.deletionTimestamp)).append("\n");
        sb.append("    finalizers: ").append(this.toIndentedString(this.finalizers)).append("\n");
        sb.append("    generateName: ").append(this.toIndentedString(this.generateName)).append("\n");
        sb.append("    generation: ").append(this.toIndentedString(this.generation)).append("\n");
        sb.append("    initializers: ").append(this.toIndentedString(this.initializers)).append("\n");
        sb.append("    labels: ").append(this.toIndentedString(this.labels)).append("\n");
        sb.append("    name: ").append(this.toIndentedString(this.name)).append("\n");
        sb.append("    namespace: ").append(this.toIndentedString(this.namespace)).append("\n");
        sb.append("    ownerReferences: ").append(this.toIndentedString(this.ownerReferences)).append("\n");
        sb.append("    resourceVersion: ").append(this.toIndentedString(this.resourceVersion)).append("\n");
        sb.append("    selfLink: ").append(this.toIndentedString(this.selfLink)).append("\n");
        sb.append("    uid: ").append(this.toIndentedString(this.uid)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
