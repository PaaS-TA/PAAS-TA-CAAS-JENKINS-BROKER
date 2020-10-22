package model;


import io.kubernetes.client.models.*;
import org.openpaas.paasta.container.platform.jenkins.model.k8s.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeploymentInstanceModel {

    public static KubeDeploymentConditionV1 getKubeDeploymentConditionV1(){
        KubeDeploymentConditionV1 kubeV1 = new KubeDeploymentConditionV1();
        kubeV1 = kubeV1.lastTransitionTime("lastTransitionTime");
        kubeV1.setLastTransitionTime("lastTransitionTime");
        kubeV1 = kubeV1.lastUpdateTime("lastUpdateTime");
        kubeV1.setLastUpdateTime("lastUpdateTime");
        kubeV1 = kubeV1.message("message");
        kubeV1.setMessage("message");
        kubeV1 = kubeV1.reason("reason");
        kubeV1.setReason("reason");
        kubeV1 = kubeV1.status("status");
        kubeV1.setStatus("status");
        kubeV1 = kubeV1.type("type");
        kubeV1.setType("type");
        return kubeV1;
    }

    public static KubeDeploymentSpecV1 getKubeDeploymentSpecV1(){
        KubeDeploymentSpecV1 kubeV1 = new KubeDeploymentSpecV1();
        kubeV1 = kubeV1.minReadySeconds(1);
        kubeV1.setMinReadySeconds(1);
        kubeV1 = kubeV1.paused(false);
        kubeV1.setPaused(false);
        kubeV1 = kubeV1.replicas(1);
        kubeV1.setReplicas(1);
        kubeV1 = kubeV1.revisionHistoryLimit(1);
        kubeV1.setRevisionHistoryLimit(1);
        kubeV1 = kubeV1.selector(new V1LabelSelector());
        kubeV1.setSelector(new V1LabelSelector());
        kubeV1 = kubeV1.strategy(new AppsV1beta1DeploymentStrategy());
        kubeV1.setStrategy(new AppsV1beta1DeploymentStrategy());
        kubeV1 = kubeV1.template(new KubeTemplateV1());
        kubeV1.setTemplate(new KubeTemplateV1());
        return kubeV1;
    }

    public static KubeDeploymentStatusV1 getKubeDeploymentStatusV1(){
        KubeDeploymentStatusV1 kubeV1 = new KubeDeploymentStatusV1();
        kubeV1 = kubeV1.availableReplicas(1);
        kubeV1.setAvailableReplicas(1);
        kubeV1 = kubeV1.collisionCount(1);
        kubeV1.setCollisionCount(1);
        kubeV1 = kubeV1.conditions(new ArrayList<KubeDeploymentConditionV1>());
        kubeV1.setConditions(new ArrayList<>());
        kubeV1 = kubeV1.observedGeneration(1L);
        kubeV1.setObservedGeneration(1L);
        kubeV1 = kubeV1.readyReplicas(1);
        kubeV1.setReadyReplicas(1);
        kubeV1 = kubeV1.replicas(1);
        kubeV1.setReplicas(1);
        kubeV1 = kubeV1.unavailableReplicas(1);
        kubeV1.setUnavailableReplicas(1);
        kubeV1 = kubeV1.updatedReplicas(1);
        kubeV1.setUpdatedReplicas(1);
        return kubeV1;
    }

    public static KubeDeploymentV1 getKubeDeploymentV1(){
        KubeDeploymentV1 v1 = new KubeDeploymentV1();
        v1 = v1.apiVersion("apiVersion");
        v1.setApiVersion("apiVersion");
        v1 = v1.kind("kind");
        v1.setKind("kind");
        v1 = v1.metadata(getKubeMetadataV1());
        v1.setMetadata(getKubeMetadataV1());
        v1 = v1.spec(getKubeDeploymentSpecV1());
        v1.setSpec(getKubeDeploymentSpecV1());
        v1 = v1.status(getKubeDeploymentStatusV1());
        v1.setStatus(getKubeDeploymentStatusV1());
        return v1;
    }

    public static KubeMetadataV1 getKubeMetadataV1(){
        KubeMetadataV1 v1 = new KubeMetadataV1();
        v1 = v1.annotations(new HashMap<>());
        v1.setAnnotations(new HashMap<>());
        v1 = v1.clusterName("clusterName");
        v1.setClusterName("clusterName");
        v1 = v1.creationTimestamp("creationTimestamp");
        v1.setCreationTimestamp("creationTimestamp");
        v1 = v1.deletionGracePeriodSeconds(1L);
        v1.setDeletionGracePeriodSeconds(1L);
        v1 = v1.deletionTimestamp("deletionTimestamp");
        v1.deletionTimestamp("deletionTimestamp");
        v1 = v1.finalizers(new ArrayList<String>());
        v1.setFinalizers(new ArrayList<String>());
        v1 = v1.generateName("generateName");
        v1.setGenerateName("generateName");
        v1 = v1.generation(1L);
        v1.setGeneration(1L);
        v1 = v1.initializers(new V1Initializers());
        v1.setInitializers(new V1Initializers());
        v1 = v1.labels(new HashMap<>());
        v1.setLabels(new HashMap<>());
        v1 = v1.name("name");
        v1.setName("name");
        v1 = v1.namespace("namespace");
        v1.setNamespace("namespace");
        v1 = v1.ownerReferences(new ArrayList<>());
        v1.setOwnerReferences(new ArrayList<>());
        v1 = v1.resourceVersion("resourceVersion");
        v1.setResourceVersion("resourceVersion");
        v1 = v1.selfLink("selfLink");
        v1.setSelfLink("selfLink");
        v1 = v1.uid("uid");
        v1.setUid("uid");
        return v1;
    }

    public static KubeServiceV1 getKubeServiceV1(){
        KubeServiceV1 v1 = new KubeServiceV1();
        v1 = v1.apiVersion("apiVersion");
        v1.setApiVersion("apiVersion");
        v1 = v1.kind("kind");
        v1.setKind("kind");
        v1 = v1.metadata(getKubeMetadataV1());
        v1.setMetadata(getKubeMetadataV1());
        List<V1ServicePort> ports = new ArrayList<>();
        V1ServicePort port = new V1ServicePort();
        port.setPort(1);
        port.setNodePort(1);
        port.setName("name");
        ports.add(port);
        V1ServiceSpec v1ServiceSpec = new V1ServiceSpec();
        v1ServiceSpec.setPorts(ports);
        v1 = v1.spec(v1ServiceSpec);
        v1.setSpec(v1ServiceSpec);
        v1 = v1.status(new V1ServiceStatus());
        v1.setStatus(new V1ServiceStatus());
        return v1;
    }
    public static KubeTemplateV1 getKubeTemplateV1(){
        KubeTemplateV1 v1 = new KubeTemplateV1();
        v1 = v1.metadata(new KubeMetadataV1());
        v1.setMetadata(new KubeMetadataV1());
        v1 = v1.spec(new V1PodSpec());
        return v1;
    }
}
