package org.openpaas.paasta.container.platform.jenkins.service.impl;

import org.openpaas.paasta.container.platform.jenkins.common.CommonService;
import org.openpaas.paasta.container.platform.jenkins.exception.ContainerPlatformJenkinsServiceException;
import org.openpaas.paasta.container.platform.jenkins.model.JpaJenkinsInstance;
import org.openpaas.paasta.container.platform.jenkins.model.JpaServiceInstance;
import org.openpaas.paasta.container.platform.jenkins.model.k8s.KubeDeploymentV1;
import org.openpaas.paasta.container.platform.jenkins.model.k8s.KubeServiceV1;
import org.openpaas.paasta.container.platform.jenkins.repo.JpaJenkinsInstanceRepository;
import org.openpaas.paasta.container.platform.jenkins.repo.JpaServiceInstanceRepository;
import org.openpaas.servicebroker.exception.ServiceBrokerException;
import org.openpaas.servicebroker.exception.ServiceInstanceDoesNotExistException;
import org.openpaas.servicebroker.exception.ServiceInstanceUpdateNotSupportedException;
import org.openpaas.servicebroker.model.CreateServiceInstanceRequest;
import org.openpaas.servicebroker.model.DeleteServiceInstanceRequest;
import org.openpaas.servicebroker.model.ServiceInstance;
import org.openpaas.servicebroker.model.UpdateServiceInstanceRequest;
import org.openpaas.servicebroker.service.ServiceInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import java.util.Map;

@Service
public class ContainerPlatformJenkinsInstanceService implements ServiceInstanceService {

    private static final Logger logger = LoggerFactory.getLogger(ContainerPlatformJenkinsInstanceService.class);
    private final CommonService commonService;

    @Value("${jenkins.namespace}")
    public String namespace;

    @Value("${caas.k8s_api_server_ip}")
    public String k8s_api_server_ip;

    @Value("${caas.k8s_api_server_port}")
    public String k8s_api_server_port;

    @Value("${caas.k8s_auth_bearer}")
    public String auth_bearer;

    @Value("${caas.dashboard_ip}")
    public String dashboard_ip;

    private final JpaJenkinsInstanceRepository jpaJenkinsInstanceRepository;

    @Autowired public ContainerPlatformJenkinsInstanceService(CommonService commonService, JpaJenkinsInstanceRepository jpaJenkinsInstanceRepository){
        this.commonService = commonService;
        this.jpaJenkinsInstanceRepository = jpaJenkinsInstanceRepository;

        //try {
        //    String create_namespace = commonService.namespace();
        //    String auth_value =
        //        commonService.secret_data_value(docker_repository_url, jenkins_username, jenkins_password);
        //    String secret = commonService.secret(auth_value);
        //    //send("/namespaces", HttpMethod.POST, create_namespace, Map.class);
        //    //Map map = send("/namespaces/"+namespace+"/secrets", HttpMethod.POST, secret, Map.class);
        //    //LOGGER.info("END CREATE NAMESPACE ::: "+ namespace +", SECRET" );
        //    break;
        //} catch (InterruptedException e1) {
        //    e1.printStackTrace();
        //}
    }

    @Override
    public JpaServiceInstance createServiceInstance(CreateServiceInstanceRequest request) throws ContainerPlatformJenkinsServiceException {
        logger.info("Service_Name : " + request.getServiceDefinition().getName());
        try {
            if (jpaJenkinsInstanceRepository.existsByOrganizationGuid(request.getOrganizationGuid())) {
                throw new ContainerPlatformJenkinsServiceException("Currently, only 1 service instances can be created in this organization.");
            }

            this.sendPost(commonService.namespace(), "/api/v1/namespaces");

            JpaServiceInstance jpaServiceInstance1  = new JpaServiceInstance(request);

            String deployment = commonService.deployment(request.getOrganizationGuid());
            String services = commonService.service(request.getOrganizationGuid());

            this.sendPost(deployment, "/apis/apps/v1/namespaces/" + namespace + "/deployments");
            this.sendPost(services, "/api/v1/namespaces/" + namespace + "/services");

            JpaJenkinsInstance jpaJenkinsInstance = new JpaJenkinsInstance(request.getOrganizationGuid(), request.getServiceInstanceId(), namespace);
            Thread.sleep(5000);
          
            String result = this.sendGet("/api/v1/namespaces/" + namespace + "/services/jenkins-"+jpaJenkinsInstance.getOrganizationGuid());

            KubeServiceV1 v1Service = commonService.getGson().fromJson(result, KubeServiceV1.class);
            jpaServiceInstance1.setDashboardUrl(dashboard_ip + ":"+v1Service.getSpec().getPorts().get(0).getNodePort().toString());
            jpaJenkinsInstanceRepository.save(jpaJenkinsInstance);
            jpaServiceInstance1.withAsync(true);
            return jpaServiceInstance1;
        } catch (Exception e) {
            throw new ContainerPlatformJenkinsServiceException(e.getMessage());
        }
    }

    @Override
    public ServiceInstance updateServiceInstance(UpdateServiceInstanceRequest request) throws ServiceInstanceUpdateNotSupportedException, ServiceBrokerException, ServiceInstanceDoesNotExistException {
        throw new ServiceBrokerException("Not Supported");
    }

    @Override
    public ServiceInstance deleteServiceInstance(DeleteServiceInstanceRequest request) {
        if (jpaJenkinsInstanceRepository.existsByServiceInstanceId(request.getServiceInstanceId())) {
            JpaJenkinsInstance jpaJenkinsInstance = jpaJenkinsInstanceRepository.findByServiceInstanceId(request.getServiceInstanceId());
            //restTemplateService.send("/namespaces/"+namespace+"/deployments/"+"jenkins-"+jpaJenkinsInstance.getOrganizationGuid(), HttpMethod.DELETE, null, Map.class);
            //restTemplateService.send("/namespaces/"+namespace+"/services/"+"jenkins-"+jpaJenkinsInstance.getOrganizationGuid(), HttpMethod.DELETE, null, Map.class);
            try {
                sendDelete("/apis/apps/v1/namespaces/" + namespace + "/deployments/jenkins-" + jpaJenkinsInstance.getOrganizationGuid());
                sendDelete("/api/v1/namespaces/" + namespace + "/services/jenkins-" + jpaJenkinsInstance.getOrganizationGuid());
            } catch (Exception e) {
                e.printStackTrace();
            }
            jpaJenkinsInstanceRepository.delete(request.getServiceInstanceId());
        }
        JpaServiceInstance jpaServiceInstance = new JpaServiceInstance();
        jpaServiceInstance.setOrganizationGuid(jpaServiceInstance.getOrganizationGuid());
        jpaServiceInstance.setServiceInstanceId(jpaServiceInstance.getServiceInstanceId());
        return jpaServiceInstance;
    }

    @Override
    public JpaServiceInstance getServiceInstance(String Instanceid) {
        JpaJenkinsInstance jpaJenkinsInstance = jpaJenkinsInstanceRepository.findByServiceInstanceId(Instanceid);
        JpaServiceInstance jpaServiceInstance = new JpaServiceInstance();
        jpaServiceInstance.setOrganizationGuid(jpaJenkinsInstance.getOrganizationGuid());
        jpaServiceInstance.setServiceInstanceId(jpaJenkinsInstance.getServiceInstanceId());
        return jpaServiceInstance;
    }

    @Override
    public JpaServiceInstance getOperationServiceInstance(String Instanceid) {
        JpaJenkinsInstance jpaJenkinsInstance = jpaJenkinsInstanceRepository.findByServiceInstanceId(Instanceid);
        //Map result = restTemplateService.send("/namespaces/"+namespace+"/deployments/"+"jenkins-"+jpaJenkinsInstance.getOrganizationGuid(), HttpMethod.GET, null, Map.class);
        try {
            String result = this.sendGet("/apis/apps/v1/namespaces/"
                                           + namespace 
                                           + "/deployments/jenkins-"
                                           + jpaJenkinsInstance.getOrganizationGuid());
            KubeDeploymentV1 kubeDeploymentV1 =
              commonService.getGson().fromJson(result, KubeDeploymentV1.class);

            if(kubeDeploymentV1.getStatus().getUnavailableReplicas().intValue() > 0){
                return null;
            }
        } catch (Exception e) {
        }
        JpaServiceInstance instance = new JpaServiceInstance();
        instance.setServiceInstanceId(jpaJenkinsInstance.getServiceInstanceId());
        instance.setOrganizationGuid(jpaJenkinsInstance.getOrganizationGuid());
        return instance;
    }

    private String sendPost(String yml, String url) throws ClientProtocolException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

        HttpPost httpPost = new HttpPost(k8s_api_server_ip + ":" + k8s_api_server_port + url);

        httpPost.addHeader("Authorization", "Bearer " + auth_bearer);
        httpPost.addHeader("Content-Type", "application/yaml;charset=UTF-8");
        httpPost.addHeader("Accept", "application/json,application/yaml,text/html");

        StringEntity params = new StringEntity(yml);

        httpPost.setEntity(params);

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        logger.info("POST " + url + " --- \n" + result);

        httpClient.close();

        return result;
    }

    private String sendGet(String url) throws ClientProtocolException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

        HttpGet httpGet = new HttpGet(k8s_api_server_ip + ":" + k8s_api_server_port + url);

        httpGet.addHeader("Authorization", "Bearer " + auth_bearer);
        httpGet.addHeader("Content-Type", "application/yaml;charset=UTF-8");
        httpGet.addHeader("Accept", "application/json,application/yaml,text/html");

        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        logger.info("GET " + url + " --- \n" + result);

        httpClient.close();

        return result;
    }

    private String sendDelete(String url) throws ClientProtocolException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

        HttpDelete httpDelete = new HttpDelete(k8s_api_server_ip + ":" + k8s_api_server_port + url);

        httpDelete.addHeader("Authorization", "Bearer " + auth_bearer);
        httpDelete.addHeader("Content-Type", "application/yaml;charset=UTF-8");
        httpDelete.addHeader("Accept", "application/json,application/yaml,text/html");

        CloseableHttpResponse httpResponse = httpClient.execute(httpDelete);
        String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        logger.info("DELETE " + url + " --- \n" + result);

        httpClient.close();

        return result;
    }
}
