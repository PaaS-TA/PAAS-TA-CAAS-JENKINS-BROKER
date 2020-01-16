package org.openpaas.paasta.caas_jenkins.service.impl;

import org.openpaas.paasta.caas_jenkins.common.CommonService;
import org.openpaas.paasta.caas_jenkins.common.RestTemplateService;
import org.openpaas.paasta.caas_jenkins.exception.CaaSJenkinsServiceException;
import org.openpaas.paasta.caas_jenkins.model.JpaJenkinsInstance;
import org.openpaas.paasta.caas_jenkins.model.JpaServiceInstance;
import org.openpaas.paasta.caas_jenkins.model.caas_custom.KubeDeploymentV1;
import org.openpaas.paasta.caas_jenkins.model.caas_custom.KubeServiceV1;
import org.openpaas.paasta.caas_jenkins.repo.JpaJenkinsInstanceRepository;
import org.openpaas.paasta.caas_jenkins.repo.JpaServiceInstanceRepository;
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

import java.util.Map;


@Service
public class CaaSJenkinsInstanceService implements ServiceInstanceService {

    private static final Logger logger = LoggerFactory.getLogger(CaaSJenkinsInstanceService.class);
    private final RestTemplateService restTemplateService;
    private final CommonService commonService;

    @Value("${jenkins.namespace}")
    public String namespace;

    @Value("${caas.api}")
    public String caas_api_uri;

    @Value("${caas.master_api}")
    public String master_api;

    private final JpaJenkinsInstanceRepository jpaJenkinsInstanceRepository;

    @Autowired public CaaSJenkinsInstanceService(RestTemplateService restTemplateService, CommonService commonService, JpaJenkinsInstanceRepository jpaJenkinsInstanceRepository){
        this.restTemplateService = restTemplateService;
        this.commonService = commonService;
        this.jpaJenkinsInstanceRepository = jpaJenkinsInstanceRepository;
    }

    @Override
    public JpaServiceInstance createServiceInstance(CreateServiceInstanceRequest request) throws CaaSJenkinsServiceException {
        logger.info("Service_Name : " + request.getServiceDefinition().getName());
        try {
            if (jpaJenkinsInstanceRepository.existsByOrganizationGuid(request.getOrganizationGuid())) {
                throw new CaaSJenkinsServiceException("Currently, only 1 service instances can be created in this organization.");
            }

            JpaServiceInstance jpaServiceInstance1  = new JpaServiceInstance(request);
            String deployment = commonService.deployment(request.getOrganizationGuid());
            String services = commonService.service(request.getOrganizationGuid());
            restTemplateService.send("/namespaces/"+namespace+"/deployments", HttpMethod.POST, deployment, Map.class);
            restTemplateService.send("/namespaces/"+namespace+"/services", HttpMethod.POST, services, Map.class);
            JpaJenkinsInstance jpaJenkinsInstance = new JpaJenkinsInstance(request.getOrganizationGuid(), request.getServiceInstanceId(), namespace);
            Thread.sleep(5000);
            Map result = restTemplateService.send("/namespaces/"+namespace+"/services/"+"jenkins-"+jpaJenkinsInstance.getOrganizationGuid(), HttpMethod.GET, null, Map.class);
            String service_json = commonService.getGson().toJson(result);
            KubeServiceV1 v1Service = commonService.getGson().fromJson(service_json, KubeServiceV1.class);
            jpaServiceInstance1.setDashboardUrl(master_api+":"+v1Service.getSpec().getPorts().get(0).getNodePort().toString());
            jpaJenkinsInstanceRepository.save(jpaJenkinsInstance);
            jpaServiceInstance1.withAsync(true);
            return jpaServiceInstance1;
        } catch (Exception e) {
            throw new CaaSJenkinsServiceException(e.getMessage());
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
            restTemplateService.send("/namespaces/"+namespace+"/deployments/"+"jenkins-"+jpaJenkinsInstance.getOrganizationGuid(), HttpMethod.DELETE, null, Map.class);
            restTemplateService.send("/namespaces/"+namespace+"/services/"+"jenkins-"+jpaJenkinsInstance.getOrganizationGuid(), HttpMethod.DELETE, null, Map.class);
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
        Map result = restTemplateService.send("/namespaces/"+namespace+"/deployments/"+"jenkins-"+jpaJenkinsInstance.getOrganizationGuid(), HttpMethod.GET, null, Map.class);
        String deployment_json = commonService.getGson().toJson(result);
        KubeDeploymentV1 kubeDeploymentV1 = commonService.getGson().fromJson(deployment_json, KubeDeploymentV1.class);
        if(kubeDeploymentV1.getStatus().getUnavailableReplicas().intValue() > 0){
            return null;
        }
        JpaServiceInstance instance = new JpaServiceInstance();
        instance.setServiceInstanceId(jpaJenkinsInstance.getServiceInstanceId());
        instance.setOrganizationGuid(jpaJenkinsInstance.getOrganizationGuid());
        return instance;
    }

}
