package org.openpaas.paasta.ondemand.test;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import model.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.*;
import org.openpaas.paasta.caas_jenkins.common.CommonService;
import org.openpaas.paasta.caas_jenkins.common.RestTemplateService;
import org.openpaas.paasta.caas_jenkins.config.GsonConfig;
import org.openpaas.paasta.caas_jenkins.exception.CaaSJenkinsServiceException;
import org.openpaas.paasta.caas_jenkins.model.JpaJenkinsInstance;
import org.openpaas.paasta.caas_jenkins.model.JpaServiceInstance;
import org.openpaas.paasta.caas_jenkins.model.caas_custom.KubeDeploymentV1;
import org.openpaas.paasta.caas_jenkins.model.caas_custom.KubeServiceV1;
import org.openpaas.paasta.caas_jenkins.repo.JpaJenkinsInstanceRepository;
import org.openpaas.paasta.caas_jenkins.repo.JpaServiceInstanceRepository;
import org.openpaas.paasta.caas_jenkins.service.impl.CaaSJenkinsInstanceService;
import org.openpaas.servicebroker.exception.ServiceBrokerException;
import org.openpaas.servicebroker.model.CreateServiceInstanceRequest;
import org.openpaas.servicebroker.model.DeleteServiceInstanceRequest;
import org.openpaas.servicebroker.model.ServiceInstance;
import org.openpaas.servicebroker.model.UpdateServiceInstanceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CaaSJenkinsInstanceServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @InjectMocks
    CaaSJenkinsInstanceService caaSJenkinsInstanceService;

    @Mock
    JpaServiceInstanceRepository jpaServiceInstanceRepository;

    @Mock
    JpaJenkinsInstanceRepository jpaJenkinsInstanceRepository;

    @Mock
    private MockRestServiceServer mockServer;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    RestTemplateService restTemplateService;

    @Mock
    private Executor serviceBExecutorService;

    @Mock
    CommonService common;

    // My class in which I want to inject the mocks
    @InjectMocks
    private CompletableFuture service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockServer = MockRestServiceServer.createServer(restTemplate);
        service = new CompletableFuture();
        ReflectionTestUtils.setField(caaSJenkinsInstanceService, "namespace", "namespace");
        ReflectionTestUtils.setField(caaSJenkinsInstanceService, "caas_api_uri", "caas_api_uri");
        ReflectionTestUtils.setField(caaSJenkinsInstanceService, "master_api", "master_api");

        print();
    }

    private void print() {
        logger.info(caaSJenkinsInstanceService.namespace);
        logger.info(caaSJenkinsInstanceService.caas_api_uri);
        logger.info(caaSJenkinsInstanceService.master_api);
    }

    @Test
    public void updateServiceInstanceTest() throws Exception {
        UpdateServiceInstanceRequest request = ServiceInstanceRequestModel.getUpdateServiceInstanceRequest();
        assertThatThrownBy(() -> caaSJenkinsInstanceService.updateServiceInstance(request))
                .isInstanceOf(ServiceBrokerException.class).hasMessageContaining("Not Supported");
    }

    @Test
    public void createServiceInstanceTest() throws Exception {
        CreateServiceInstanceRequest request = JpaServiceInstanceModel.getCreateServiceInstanceRequest();
        logger.info(request.getServiceDefinition().getName());
        JpaServiceInstance serviceInstance = ServiceInstanceModel.getJpaServiceInstance();
        JpaJenkinsInstance jpaJenkinsInstance = new JpaJenkinsInstance();
        KubeServiceV1  v1 = DeploymentInstanceModel.getKubeServiceV1();
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.convertValue(v1, Map.class);
        when(jpaJenkinsInstanceRepository.existsByOrganizationGuid(request.getOrganizationGuid())).thenReturn(false);
        when(common.deployment(request.getOrganizationGuid())).thenReturn("deployment");
        when(common.service(request.getOrganizationGuid())).thenReturn("service");
        when(restTemplateService.send("/namespaces/"+"namespace"+"/services/"+"jenkins-"+request.getOrganizationGuid(), HttpMethod.GET, null, Map.class)).thenReturn(map);
        Gson gson = new Gson();
        when(common.getGson()).thenReturn(gson);
        ServiceInstance result = caaSJenkinsInstanceService.createServiceInstance(request);
    }

    @Test
    public void createServiceInstanceTest2() throws Exception {
        CreateServiceInstanceRequest request = JpaServiceInstanceModel.getCreateServiceInstanceRequest();
        when(jpaJenkinsInstanceRepository.existsByOrganizationGuid(request.getOrganizationGuid())).thenReturn(true);
        assertThatThrownBy(() -> caaSJenkinsInstanceService.createServiceInstance(request))
                .isInstanceOf(CaaSJenkinsServiceException.class).hasMessageContaining("Currently, only 1 service instances can be created in this organization.");
    }

    @Test
    public void deleteServiceInstanceTest1() throws Exception {
        DeleteServiceInstanceRequest request = JpaServiceInstanceModel.getDeleteServiceInstanceRequest();
        when(jpaJenkinsInstanceRepository.existsByServiceInstanceId(request.getServiceInstanceId())).thenReturn(true);
        JpaJenkinsInstance jpaJenkinsInstance = JpaJenkinsInstanceModel.getJpaJenkinsInstance("org_id","service_id","namespace");
        when(jpaJenkinsInstanceRepository.findByServiceInstanceId(request.getServiceInstanceId())).thenReturn(jpaJenkinsInstance);
        caaSJenkinsInstanceService.deleteServiceInstance(request);
    }

    @Test
    public void deleteServiceInstanceTest2() throws Exception {
        DeleteServiceInstanceRequest request = JpaServiceInstanceModel.getDeleteServiceInstanceRequest();
        when(jpaJenkinsInstanceRepository.existsByServiceInstanceId(request.getServiceInstanceId())).thenReturn(false);
        caaSJenkinsInstanceService.deleteServiceInstance(request);
    }

    @Test
    public void getServiceInstanceTest1() throws Exception {
        JpaJenkinsInstance jpaJenkinsInstance = JpaJenkinsInstanceModel.getJpaJenkinsInstance("org_id","service_id","namespace");
        when(jpaJenkinsInstanceRepository.findByServiceInstanceId("Instanceid")).thenReturn(jpaJenkinsInstance);
        caaSJenkinsInstanceService.getServiceInstance("Instanceid");
    }
    /*
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
     */
    @Test
    public void getOperationServiceInstanceTest1() throws Exception {
        JpaJenkinsInstance jpaJenkinsInstance = JpaJenkinsInstanceModel.getJpaJenkinsInstance("org_id","service_id","namespace");
        when(jpaJenkinsInstanceRepository.findByServiceInstanceId("Instanceid")).thenReturn(jpaJenkinsInstance);
        KubeDeploymentV1 v1 = DeploymentInstanceModel.getKubeDeploymentV1();
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.convertValue(v1, Map.class);
        Gson gson = new Gson();
        when(common.getGson()).thenReturn(gson);
        when(restTemplateService.send("/namespaces/"+"namespace"+"/deployments/"+"jenkins-"+jpaJenkinsInstance.getOrganizationGuid(), HttpMethod.GET, null, Map.class)).thenReturn(map);
        caaSJenkinsInstanceService.getOperationServiceInstance("Instanceid");
    }

    @Test
    public void getOperationServiceInstanceTest2() throws Exception {
        JpaJenkinsInstance jpaJenkinsInstance = JpaJenkinsInstanceModel.getJpaJenkinsInstance("org_id","service_id","namespace");
        when(jpaJenkinsInstanceRepository.findByServiceInstanceId("Instanceid")).thenReturn(jpaJenkinsInstance);
        KubeDeploymentV1 v1 = DeploymentInstanceModel.getKubeDeploymentV1();
        ObjectMapper mapper = new ObjectMapper();
        v1.getStatus().setUnavailableReplicas(-1);
        Map map = mapper.convertValue(v1, Map.class);
        Gson gson = new Gson();
        when(common.getGson()).thenReturn(gson);
        when(restTemplateService.send("/namespaces/"+"namespace"+"/deployments/"+"jenkins-"+jpaJenkinsInstance.getOrganizationGuid(), HttpMethod.GET, null, Map.class)).thenReturn(map);
        caaSJenkinsInstanceService.getOperationServiceInstance("Instanceid");
    }
//
//    //org 할당된 Service Instance 초과될경우
//    @Test
//    public void createServiceInstanceTest_1() throws Exception {
//        CreateServiceInstanceRequest request = JpaServiceInstanceModel.getCreateServiceInstanceRequest();
//        onDemandInstanceService.org_limitation = -2;
//        assertThatThrownBy(() -> onDemandInstanceService.createServiceInstance(request))
//                .isInstanceOf(ServiceBrokerException.class).hasMessageContaining("Currently, only -2 service instances can be created in this organization.");
//    }
//
//    //space 할당된 Service Instance 초과될경우
//    @Test
//    public void createServiceInstanceTest_2() throws Exception {
//        CreateServiceInstanceRequest request = JpaServiceInstanceModel.getCreateServiceInstanceRequest();
//        onDemandInstanceService.space_limitation = -2;
//        assertThatThrownBy(() -> onDemandInstanceService.createServiceInstance(request))
//                .isInstanceOf(ServiceBrokerException.class).hasMessageContaining("Currently, only -2 service instances can be created in this space.");
//    }
//
//    //getVmInstance == null
//    @Test
//    public void createServiceInstanceTest_3() throws Exception {
//        CreateServiceInstanceRequest request = JpaServiceInstanceModel.getCreateServiceInstanceRequest();
//        when(onDemandDeploymentService.getVmInstance("deployment_name", "instance_name")).thenReturn(null);
//        assertThatThrownBy(() -> onDemandInstanceService.createServiceInstance(request))
//                .isInstanceOf(ServiceBrokerException.class).hasMessageContaining("deployment_name is Working");
//    }
//
////    findByVmInstanceId == null
//    @Test
//    public void createServiceInstanceTest_4() throws Exception {
//        CreateServiceInstanceRequest request = JpaServiceInstanceModel.getCreateServiceInstanceRequest();
//        List<DeploymentInstance> getVmInstance = new ArrayList<>();
//        getVmInstance.add(DeploymentInstanceModel.getDeploymentInstance());
//        when(onDemandDeploymentService.getVmInstance("deployment_name", "instance_name")).thenReturn(getVmInstance);
//        when(jpaServiceInstanceRepository.findByVmInstanceId(getVmInstance.get(0).getId())).thenReturn(null);
//        JpaServiceInstance result = onDemandInstanceService.createServiceInstance(request);
//        assertThat(result.getVmInstanceId(), is(getVmInstance.get(0).getId()));
//        assertThat(result.getDashboardUrl(), is(getVmInstance.get(0).getIps().substring(1,getVmInstance.get(0).getIps().length()-1)));
//    }
//
//    //getLock == true
//    @Test
//    public void createServiceInstanceTest_4_1() throws Exception {
//        CreateServiceInstanceRequest request = JpaServiceInstanceModel.getCreateServiceInstanceRequest();
//        List<DeploymentInstance> getVmInstance = new ArrayList<>();
//        getVmInstance.add(DeploymentInstanceModel.getDeploymentInstance());
//        JpaServiceInstance jpaServiceInstance = new JpaServiceInstance();
//        when(onDemandDeploymentService.getVmInstance("deployment_name", "instance_name")).thenReturn(getVmInstance);
//        when(jpaServiceInstanceRepository.findByVmInstanceId(getVmInstance.get(0).getId())).thenReturn(jpaServiceInstance);
//        when(onDemandDeploymentService.getLock("deployment_name")).thenReturn(true);
//        assertThatThrownBy(() -> onDemandInstanceService.createServiceInstance(request))
//                .isInstanceOf(ServiceBrokerException.class).hasMessageContaining("deployment_name is Working");
//    }
//
//    //getLock == true
//    @Test
//    public void createServiceInstanceTest_5() throws Exception {
//        CreateServiceInstanceRequest request = JpaServiceInstanceModel.getCreateServiceInstanceRequest();
//        when(onDemandDeploymentService.getLock("deployment_name")).thenReturn(true);
//        assertThatThrownBy(() -> onDemandInstanceService.createServiceInstance(request))
//                .isInstanceOf(ServiceBrokerException.class).hasMessageContaining("deployment_name is Working");
//    }
//
////    Detach VM Start Test
//    @Test
//    public void createServiceInstanceTest_6() throws Exception {
//        CreateServiceInstanceRequest request = JpaServiceInstanceModel.getCreateServiceInstanceRequest();
//        List<DeploymentInstance> getVmInstance = new ArrayList<>();
//        getVmInstance.add(DeploymentInstanceModel.getDeploymentDetachedInstance());
//        String taskId = anyString();
//        String ips = anyString();
//        when(onDemandDeploymentService.getVmInstance("deployment_name", "instance_name")).thenReturn(getVmInstance);
//        when(onDemandDeploymentService.getLock("deployment_name")).thenReturn(false);
//        when(onDemandDeploymentService.getTaskID("deployment_name")).thenReturn(taskId);
//        when(onDemandDeploymentService.getStartInstanceIPS(taskId,"instance_name",getVmInstance.get(0).getId())).thenReturn(ips);
//        JpaServiceInstance result = onDemandInstanceService.createServiceInstance(request);
//        assertThat(result.getVmInstanceId(), is(getVmInstance.get(0).getId()));
//        assertThat(result.getDashboardUrl(), is(ips));
//    }
//
//    //Detach VM Start Sleep Test
//    @Test
//    public void createServiceInstanceTest_6_1() throws Exception {
//        CreateServiceInstanceRequest request = JpaServiceInstanceModel.getCreateServiceInstanceRequest();
//        List<DeploymentInstance> getVmInstance = new ArrayList<>();
//        getVmInstance.add(DeploymentInstanceModel.getDeploymentDetachedInstance());
//        when(onDemandDeploymentService.getVmInstance("deployment_name", "instance_name")).thenReturn(getVmInstance);
//        when(onDemandDeploymentService.getLock("deployment_name")).thenReturn(false);
//        when(onDemandDeploymentService.getTaskID("deployment_name")).thenReturn(null);
//    }
//
//    //Detach VM Start Sleep Test
//    @Test
//    public void createServiceInstanceTest_6_2() throws Exception {
//        CreateServiceInstanceRequest request = JpaServiceInstanceModel.getCreateServiceInstanceRequest();
//        List<DeploymentInstance> getVmInstance = new ArrayList<>();
//        getVmInstance.add(DeploymentInstanceModel.getDeploymentDetachedInstance());
//        when(onDemandDeploymentService.getVmInstance("deployment_name", "instance_name")).thenReturn(getVmInstance);
//        when(onDemandDeploymentService.getLock("deployment_name")).thenReturn(false);
//        when(onDemandDeploymentService.getTaskID("deployment_name")).thenReturn("task_id");
//        when(onDemandDeploymentService.getStartInstanceIPS("task_id","iinstance_name","instance_id")).thenReturn(null);
//    }
//
//    //Detach VM Instance Create Test
//    @Test
//    public void createServiceInstanceTest_7() throws Exception {
//        CreateServiceInstanceRequest request = JpaServiceInstanceModel.getCreateServiceInstanceRequest();
//        List<DeploymentInstance> getVmInstance = new ArrayList<>();
//        getVmInstance.add(DeploymentInstanceModel.getDeploymentEmptyInstance());
//        String taskId = "test taskId";
//        String ips = "test Ips";
//        String instance_id = "test instance_id";
//        when(onDemandDeploymentService.getVmInstance("deployment_name", "instance_name")).thenReturn(getVmInstance);
//        when(onDemandDeploymentService.getLock("deployment_name")).thenReturn(false);
//        Mockito.doCallRealMethod().when(onDemandDeploymentService).createInstance("deployment_name","instance_name");
//        onDemandDeploymentService.createInstance("deployment_name","instance_name");
//        when(onDemandDeploymentService.getTaskID("deployment_name")).thenReturn(taskId);
//        when(onDemandDeploymentService.getUpdateInstanceIPS(taskId)).thenReturn(ips);
//        when(onDemandDeploymentService.getUpdateVMInstanceID(taskId,"instance_name")).thenReturn(instance_id);
//
//        JpaServiceInstance result = onDemandInstanceService.createServiceInstance(request);
//        assertThat(result.getVmInstanceId(), is(instance_id));
//        assertThat(result.getDashboardUrl(), is(ips));
//    }
//
//    //Detach VM Instance Create Sleep Test
//    @Test
//    public void createServiceInstanceTest_7_1() throws Exception {
//        CreateServiceInstanceRequest request = JpaServiceInstanceModel.getCreateServiceInstanceRequest();
//        List<DeploymentInstance> getVmInstance = new ArrayList<>();
//        getVmInstance.add(DeploymentInstanceModel.getDeploymentEmptyInstance());
//        when(onDemandDeploymentService.getVmInstance("deployment_name", "instance_name")).thenReturn(getVmInstance);
//        when(onDemandDeploymentService.getLock("deployment_name")).thenReturn(false);
//        Mockito.doCallRealMethod().when(onDemandDeploymentService).createInstance("deployment_name","instance_name");
//        when(onDemandDeploymentService.getTaskID("deployment_name")).thenReturn(null);
//        onDemandDeploymentService.createInstance("deployment_name","instance_name");
//    }
//
//    //Detach VM Instance Create Sleep Test
//    @Test
//    public void createServiceInstanceTest_7_2() throws Exception {
//        CreateServiceInstanceRequest request = JpaServiceInstanceModel.getCreateServiceInstanceRequest();
//        List<DeploymentInstance> getVmInstance = new ArrayList<>();
//        getVmInstance.add(DeploymentInstanceModel.getDeploymentEmptyInstance());
//        String taskId = "test taskId";
//        when(onDemandDeploymentService.getVmInstance("deployment_name", "instance_name")).thenReturn(getVmInstance);
//        when(onDemandDeploymentService.getLock("deployment_name")).thenReturn(false);
//        Mockito.doCallRealMethod().when(onDemandDeploymentService).createInstance("deployment_name","instance_name");
//        when(onDemandDeploymentService.getTaskID("deployment_name")).thenReturn(taskId);
//        when(onDemandDeploymentService.getUpdateInstanceIPS(taskId)).thenReturn(null);
//        onDemandDeploymentService.createInstance("deployment_name","instance_name");
//    }
//
//    //Detach VM Instance Create Test
//    @Test
//    public void deleteServiceInstanceTest_1() throws Exception {
//        DeleteServiceInstanceRequest request = JpaServiceInstanceModel.getDeleteServiceInstanceRequest();
//        JpaServiceInstance jpaServiceInstance = new JpaServiceInstance();
//        when(jpaServiceInstanceRepository.findByServiceInstanceId(request.getServiceInstanceId())).thenReturn(jpaServiceInstance);
//        jpaServiceInstance.setVmInstanceId(null);
//        ServiceInstance result = onDemandInstanceService.deleteServiceInstance(request);
//        assertThat(result.getServiceInstanceId(), is(jpaServiceInstance.getServiceInstanceId()));
//    }
//
//
//    //Detach VM Instance delete Test
//    @Test
//    public void deleteServiceInstanceTest_2() throws Exception {
//        DeleteServiceInstanceRequest request = JpaServiceInstanceModel.getDeleteServiceInstanceRequest();
//        JpaServiceInstance jpaServiceInstance = new JpaServiceInstance();
//        when(jpaServiceInstanceRepository.findByServiceInstanceId(request.getServiceInstanceId())).thenReturn(jpaServiceInstance);
//        when(jpaServiceInstanceRepository.existsAllByVmInstanceId(request.getServiceInstanceId())).thenReturn(true);
//        when(onDemandDeploymentService.getLock("deployment_name")).thenReturn(false);
//        jpaServiceInstance.setVmInstanceId(anyString());
//        ServiceInstance result = onDemandInstanceService.deleteServiceInstance(request);
//        assertThat(result.getServiceInstanceId(), is(jpaServiceInstance.getServiceInstanceId()));
//    }
//
//    //Instance delete Test
//    @Test
//    public void deleteServiceInstanceTest_2_1() throws Exception {
//        DeleteServiceInstanceRequest request = JpaServiceInstanceModel.getDeleteServiceInstanceRequest();
//        JpaServiceInstance jpaServiceInstance = new JpaServiceInstance();
//        when(jpaServiceInstanceRepository.findByServiceInstanceId(request.getServiceInstanceId())).thenReturn(jpaServiceInstance);
//        when(jpaServiceInstanceRepository.existsAllByVmInstanceId(request.getServiceInstanceId())).thenReturn(true);
//        when(onDemandDeploymentService.getLock("deployment_name")).thenReturn(false);
//        jpaServiceInstance.setVmInstanceId(anyString());
//        ServiceInstance result = onDemandInstanceService.deleteServiceInstance(request);
//    }
//
//    //Instance delete Test
//    @Test
//    public void deleteServiceInstanceTest_3() throws Exception {
//        DeleteServiceInstanceRequest request = JpaServiceInstanceModel.getDeleteServiceInstanceRequest();
//        JpaServiceInstance jpaServiceInstance = new JpaServiceInstance();
//        when(jpaServiceInstanceRepository.findByServiceInstanceId(request.getServiceInstanceId())).thenReturn(jpaServiceInstance);
//        when(jpaServiceInstanceRepository.existsAllByVmInstanceId(request.getServiceInstanceId())).thenReturn(true);
//        when(onDemandDeploymentService.getLock("deployment_name")).thenReturn(true);
//        jpaServiceInstance.setVmInstanceId(anyString());
//        ServiceInstance result = onDemandInstanceService.deleteServiceInstance(request);
//        assertThat(result.getServiceInstanceId(), is(jpaServiceInstance.getServiceInstanceId()));
//    }
//
//    //Instance delete Test Exception
//    @Test
//    public void deleteServiceInstanceTest_4() throws Exception {
//        DeleteServiceInstanceRequest request = JpaServiceInstanceModel.getDeleteServiceInstanceRequest();
//        JpaServiceInstance jpaServiceInstance = new JpaServiceInstance();
//        jpaServiceInstance.setVmInstanceId(anyString());
//        when(jpaServiceInstanceRepository.findByServiceInstanceId(request.getServiceInstanceId())).thenReturn(jpaServiceInstance);
//        when(jpaServiceInstanceRepository.existsAllByVmInstanceId(request.getServiceInstanceId())).thenReturn(true);
//        doThrow(InterruptedException.class).when(onDemandDeploymentService).getLock("deployment_name");
//        ServiceInstance result = onDemandInstanceService.deleteServiceInstance(request);
//        assertThat(result.getServiceInstanceId(), is(jpaServiceInstance.getServiceInstanceId()));
//    }
//
//
//    //Detach VM Instance Create Test
//    @Test
//    public void getOperationServiceInstanceTest_1() throws Exception {
//        JpaServiceInstance jpaServiceInstance = new JpaServiceInstance();
//        String InstacneId = "Instance_id";
//        when(jpaServiceInstanceRepository.findByServiceInstanceId(InstacneId)).thenReturn(jpaServiceInstance);
//        when(onDemandDeploymentService.runningTask("deployment_name",jpaServiceInstance)).thenReturn(false);
//        JpaServiceInstance result = onDemandInstanceService.getOperationServiceInstance(InstacneId);
//        jpaServiceInstance = null;
//        assertThat(result, is(jpaServiceInstance));
//    }
//
//    //Detach VM Instance Create Test
//    @Test
//    public void getOperationServiceInstanceTest_2() throws Exception {
//        JpaServiceInstance jpaServiceInstance = new JpaServiceInstance();
//        String InstacneId = "Instance_id";
//        jpaServiceInstance.setAppGuid("app_guid");
//        Map map = new HashMap<>();
//        map.put("test","test");
//        when(jpaServiceInstanceRepository.findByServiceInstanceId(InstacneId)).thenReturn(jpaServiceInstance);
//        when(onDemandDeploymentService.runningTask("deployment_name",jpaServiceInstance)).thenReturn(true);
//        ServiceBindingsV2 serviceBindingsV2 = mock(ServiceBindingsV2.class, RETURNS_SMART_NULLS);
//        ApplicationsV2 applicationsV2 = mock(ApplicationsV2.class, RETURNS_SMART_NULLS);
//        Mockito.doCallRealMethod().when(cloudFoundryService).ServiceInstanceAppBinding("app_id","serviceInstance_id",map, serviceBindingsV2, applicationsV2);
////        cloudFoundryService.ServiceInstanceAppBinding("app_id","serviceInstance_id",map);
//        JpaServiceInstance result = onDemandInstanceService.getOperationServiceInstance(InstacneId);
//        assertThat(result, is(jpaServiceInstance));
//    }
//
//    // getOperationServiceInstance Test Exception
//    @Test
//    public void getOperationServiceInstanceTest_2_4() throws Exception {
//        JpaServiceInstance jpaServiceInstance = new JpaServiceInstance();
//        String InstacneId = "Instance_id";
//        jpaServiceInstance.setAppGuid("app_guid");
//        Map map = new HashMap<>();
//        map.put("test","test");
//        when(jpaServiceInstanceRepository.findByServiceInstanceId(InstacneId)).thenReturn(jpaServiceInstance);
//        when(onDemandDeploymentService.runningTask("deployment_name",jpaServiceInstance)).thenReturn(true);
//        ServiceBindingsV2 serviceBindingsV2 = mock(ServiceBindingsV2.class, RETURNS_SMART_NULLS);
//        ApplicationsV2 applicationsV2 = mock(ApplicationsV2.class, RETURNS_SMART_NULLS);
//        doThrow(Exception.class).when(cloudFoundryService).ServiceInstanceAppBinding("app_id","serviceInstance_id",map, serviceBindingsV2, applicationsV2);
//        JpaServiceInstance result = onDemandInstanceService.getOperationServiceInstance(InstacneId);
//        assertThat(result, is(jpaServiceInstance));
//    }
//
//    @Test
//    public void DeploymentModelTest() throws Exception {
//        DeploymentInstance deploymentInstance = DeploymentInstanceModel.getDeploymentInstance();
//        DeploymentInstance emptyInstance = DeploymentInstanceModel.EmptyInstance();
//
//        emptyInstance.setId(deploymentInstance.getId());
//        emptyInstance.setActive(deploymentInstance.getActive());
//        emptyInstance.setAgentId(deploymentInstance.getAgentId());
//        emptyInstance.setDiskCid(deploymentInstance.getDiskCid());
//        emptyInstance.setIps(deploymentInstance.getIps());
//        emptyInstance.setJobName(deploymentInstance.getJobName());
//        emptyInstance.setVmCid(deploymentInstance.getVmCid());
//        emptyInstance.setJobState(deploymentInstance.getJobState());
//        emptyInstance.setState(deploymentInstance.getState());
//
//        assertThat(deploymentInstance.getJobName(), is(emptyInstance.getJobName()));
//        assertThat(deploymentInstance.getVmCid(), is(emptyInstance.getVmCid()));
//        assertThat(deploymentInstance.getJobState(), is(emptyInstance.getJobState()));
//        assertThat(deploymentInstance.getState(), is(emptyInstance.getState()));
//        assertThat(deploymentInstance.getIps(), is(emptyInstance.getIps()));
//        assertThat(deploymentInstance.getDiskCid(), is(emptyInstance.getDiskCid()));
//        assertThat(deploymentInstance.getAgentId(), is(emptyInstance.getAgentId()));
//        assertThat(deploymentInstance.getActive(), is(emptyInstance.getActive()));
//        assertThat(deploymentInstance.getId(), is(emptyInstance.getId()));
//        assertThat(deploymentInstance.toString(), is(emptyInstance.toString()));
//
//    }
//
//
//
//
}
