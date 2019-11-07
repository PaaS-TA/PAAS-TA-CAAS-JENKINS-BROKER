package org.openpaas.paasta.caas_jenkins.common;

import afu.org.checkerframework.checker.oigj.qual.O;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.gson.Gson;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.AppsV1beta1Deployment;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.models.V1Service;
import io.kubernetes.client.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class CommonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);
    private final Gson gson;
    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());


    @Value("${jenkins.deployment_file_path}")
    public String jenkins_deployment_file_path;

    @Value("${jenkins.service_file_path}")
    public String jenkins_service_file_path;

    public Gson getGson() {
        return gson;
    }

    @Autowired
    public CommonService(Gson gson) {this.gson = gson;}

    public String deployment(String org_guid) throws IOException {
        File deployment = new File(jenkins_deployment_file_path);
        AppsV1beta1Deployment v1beta1Deployment = mapper.readValue(deployment, AppsV1beta1Deployment.class);
        v1beta1Deployment = deployment_name_change(v1beta1Deployment, org_guid);
        return Object_To_Json(v1beta1Deployment);
    }

    public String service(String org_guid) throws IOException {
        File service = new File(jenkins_service_file_path);
        V1Service v1Service = mapper.readValue(service, V1Service.class);
        v1Service = service_name_change(v1Service, org_guid);
        return Object_To_Json(v1Service);
    }

    private String Object_To_Json(Object object){
        return gson.toJson(object);
    }

    private AppsV1beta1Deployment deployment_name_change(AppsV1beta1Deployment deployment, String org_guid){
        deployment.getMetadata().setName("jenkins-" + org_guid);
        deployment.getSpec().getSelector().getMatchLabels().put("app", "jenkins-" + org_guid);
        deployment.getSpec().getTemplate().getMetadata().getLabels().put("app", "jenkins-" + org_guid);
        deployment.getSpec().getTemplate().getSpec().getContainers().get(0).name("jenkins-" + org_guid);
        deployment.getSpec().getTemplate().getSpec().setNodeName(null);
        return deployment;
    }

    private V1Service service_name_change(V1Service v1Service, String org_guid){
        v1Service.getMetadata().setName("jenkins-" + org_guid);
        v1Service.getSpec().getSelector().put("app", "jenkins-" + org_guid);
        v1Service.getSpec().getPorts().get(0).setName("jenkins-" + org_guid);
        return v1Service;
    }

}
