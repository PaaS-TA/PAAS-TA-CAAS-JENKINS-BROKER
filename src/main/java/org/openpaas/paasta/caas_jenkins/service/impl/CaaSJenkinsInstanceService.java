package org.openpaas.paasta.caas_jenkins.service.impl;

import org.openpaas.paasta.caas_jenkins.common.CommonService;
import org.openpaas.paasta.caas_jenkins.common.RestTemplateService;
import org.openpaas.paasta.caas_jenkins.exception.CaaSJenkinsServiceException;
import org.openpaas.paasta.caas_jenkins.model.JpaJenkinsInstance;
import org.openpaas.paasta.caas_jenkins.model.JpaServiceInstance;
import org.openpaas.paasta.caas_jenkins.model.caas_custom.KubeDeploymentV1;
import org.openpaas.paasta.caas_jenkins.repo.JpaJenkinsInstanceRepository;
import org.openpaas.paasta.caas_jenkins.repo.JpaServiceInstanceRepository;
import org.openpaas.servicebroker.exception.ServiceBrokerException;
import org.openpaas.servicebroker.exception.ServiceInstanceDoesNotExistException;
import org.openpaas.servicebroker.exception.ServiceInstanceUpdateNotSupportedException;
import org.openpaas.servicebroker.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openpaas.servicebroker.service.ServiceInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import java.util.Map;


@Service
public class CaaSJenkinsInstanceService implements ServiceInstanceService {

    private static final Logger logger = LoggerFactory.getLogger(CaaSJenkinsInstanceService.class);
    private final RestTemplateService restTemplateService;
    private final CommonService commonService;

    @Autowired
    JpaServiceInstanceRepository jpaServiceInstanceRepository;

    @Autowired
    JpaJenkinsInstanceRepository jpaJenkinsInstanceRepository;

    @Autowired public CaaSJenkinsInstanceService(RestTemplateService restTemplateService, CommonService commonService){
        this.restTemplateService = restTemplateService;
        this.commonService = commonService;
    }

    @Override
    public JpaServiceInstance createServiceInstance(CreateServiceInstanceRequest request) throws CaaSJenkinsServiceException {
        logger.info("Service_Name : " + request.getServiceDefinition().getName());
        try {
            if (jpaJenkinsInstanceRepository.existsByOrganizationGuid(request.getOrganizationGuid())) {
                throw new CaaSJenkinsServiceException("Currently, only 1 service instances can be created in this organization.");
            } JpaServiceInstance jpaServiceInstance1  = jpaServiceInstanceRepository.findByOrganizationGuid(request.getOrganizationGuid());
            if(jpaServiceInstance1 == null) {
                throw new CaaSJenkinsServiceException("Apply for caas service first.");
            } String deployment = commonService.deployment(request.getOrganizationGuid());
            String services = commonService.service(request.getOrganizationGuid());
            restTemplateService.send("/namespaces/"+jpaServiceInstance1.getCaasNamespace()+"/deployments", HttpMethod.POST, deployment, Map.class);
            restTemplateService.send("/namespaces/"+jpaServiceInstance1.getCaasNamespace()+"/services", HttpMethod.POST, services, Map.class);
            JpaJenkinsInstance jpaJenkinsInstance = new JpaJenkinsInstance(request.getOrganizationGuid(), request.getServiceInstanceId(), jpaServiceInstance1.getCaasNamespace());
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
            restTemplateService.send("/namespaces/"+jpaJenkinsInstance.getNamespace()+"/deployments/"+"jenkins-"+jpaJenkinsInstance.getOrganizationGuid(), HttpMethod.DELETE, null, Map.class);
            restTemplateService.send("/namespaces/"+jpaJenkinsInstance.getNamespace()+"/services/"+"jenkins-"+jpaJenkinsInstance.getOrganizationGuid(), HttpMethod.DELETE, null, Map.class);
            jpaJenkinsInstanceRepository.delete(request.getServiceInstanceId());
        }

        return jpaServiceInstanceRepository.findByServiceInstanceId(request.getServiceInstanceId());
    }

    @Override
    public JpaServiceInstance getServiceInstance(String Instanceid) {
        return jpaServiceInstanceRepository.findByServiceInstanceId(Instanceid);
    }

    @Override
    public JpaServiceInstance getOperationServiceInstance(String Instanceid) {
        JpaJenkinsInstance jpaJenkinsInstance = jpaJenkinsInstanceRepository.findByServiceInstanceId(Instanceid);
        Map result = restTemplateService.send("/namespaces/"+jpaJenkinsInstance.getNamespace()+"/deployments/"+"jenkins-"+jpaJenkinsInstance.getOrganizationGuid(), HttpMethod.GET, null, Map.class);
        String deployment_json = commonService.getGson().toJson(result);
        KubeDeploymentV1 kubeDeploymentV1 = commonService.getGson().fromJson(deployment_json, KubeDeploymentV1.class);
        if(kubeDeploymentV1.getStatus().getUnavailableReplicas().intValue() > 0){
            return null;
        }
        JpaServiceInstance instance = jpaServiceInstanceRepository.findByServiceInstanceId(Instanceid);
        return instance;
    }

}
