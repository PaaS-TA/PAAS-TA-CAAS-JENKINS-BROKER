package org.openpaas.paasta.container.platform.jenkins.common;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.gson.Gson;
import io.kubernetes.client.models.AppsV1beta1Deployment;
import io.kubernetes.client.models.V1Namespace;
import io.kubernetes.client.models.V1Secret;
import io.kubernetes.client.models.V1Service;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);
    private final Gson gson;
    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());


    @Value("${jenkins.deployment_file_path}")
    public String jenkins_deployment_file_path;

    @Value("${jenkins.service_file_path}")
    public String jenkins_service_file_path;

    @Value("${jenkins.namespace_file_path}")
    public String jenkins_namespace_file_path;

    @Value("${jenkins.secret_file_path}")
    public String jenkins_secret_file_path;

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

    public String namespace() throws IOException {
        File service = new File(jenkins_namespace_file_path);
        V1Namespace v1Namespace =  mapper.readValue(service, V1Namespace.class);
        return Object_To_Json(v1Namespace);
    }

    public String secret(String value) throws IOException {
        Path path = Paths.get(jenkins_secret_file_path);
        Charset cs = StandardCharsets.UTF_8;
        List<String> list = Files.readAllLines(path,cs);
        File secret = new File(jenkins_secret_file_path);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(secret));
        int i = 0;
        for(String readLine : list){
            if(i == 6){
                bufferedWriter.write("  .dockerconfigjson: "+value);
            } else{
                bufferedWriter.write(readLine);
            }
            bufferedWriter.newLine();
            i++;
        }
        bufferedWriter.close();
        secret = new File(jenkins_secret_file_path);
        V1Secret v1Secret =  mapper.readValue(secret, V1Secret.class);
        return Object_To_Json(v1Secret);
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

    public String secret_data_value(String docker_repo_uri, String username, String password) throws IOException {
        LOGGER.info("docker_repo_uri::::::" + docker_repo_uri + "   username:::::" + username + "    password::::::" + password);
        String auth = Base64Utils.encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
        LOGGER.info("AUTH ::::: " + auth);
        Map auth_property = new HashMap<String,String>();
        auth_property.put("username", username);
        auth_property.put("password", password);
        auth_property.put("auth", auth);
        LOGGER.info("auth_property ::::: " + auth_property);
        Map auth_value = new HashMap<String,Map<?,?>>();
        auth_value.put(docker_repo_uri, auth_property);
        LOGGER.info("auth_value ::::: " + auth_value);
        Map auth_result = new HashMap<String,Map<?,?>>();
        auth_result.put("auths", auth_value);
        LOGGER.info("auth_result ::::: " + auth_result);
        JSONObject jsonObject = new JSONObject(auth_result);
        LOGGER.info(jsonObject.toString());
        LOGGER.info(Base64Utils.encodeToString(jsonObject.toString().getBytes(StandardCharsets.UTF_8)).getBytes().toString());;
        String auth_base64 = Base64Utils.encodeToString(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
        LOGGER.info(auth_base64);
        return auth_base64;
    }



}
