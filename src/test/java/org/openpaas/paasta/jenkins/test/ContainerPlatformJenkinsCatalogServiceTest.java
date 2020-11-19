package org.openpaas.paasta.jenkins.test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.openpaas.paasta.container.platform.jenkins.common.CommonService;
import org.openpaas.paasta.container.platform.jenkins.config.BrokerConfig;
import org.openpaas.paasta.container.platform.jenkins.config.CatalogConfig;
import org.openpaas.paasta.container.platform.jenkins.config.GsonConfig;
import org.openpaas.paasta.container.platform.jenkins.config.RestTemplateConfig;
import org.openpaas.paasta.container.platform.jenkins.service.impl.ContainerPlatformJenkinsCatalogService;
import org.openpaas.paasta.container.platform.jenkins.service.impl.ContainerPlatformJenkinsInstanceService;
import org.openpaas.servicebroker.controller.CatalogController;
import org.openpaas.servicebroker.model.Catalog;
import org.openpaas.servicebroker.service.CatalogService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContainerPlatformJenkinsCatalogServiceTest {

    @InjectMocks
    ContainerPlatformJenkinsInstanceService containerPlatformJenkinsInstanceService;


    @InjectMocks
    CatalogController controller;

    @Mock
    CatalogService catalogService;

    @Mock
    CommonService commonService;

    @Spy
    CatalogConfig catalogConfig;

    @Spy
    BrokerConfig brokerConfig;

    @Spy
    GsonConfig GsonConfig;

    @Spy
    RestTemplateConfig restTemplateConfig;

    private MockMvc mockMvc;

    private Catalog catalog;

    private String basicAuth;

    private RestTemplate resttemplate;


    @Before
    public void setup() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        MockitoAnnotations.initMocks(this);
        basicAuth = "Basic " + (Base64.getEncoder().encodeToString(("admin" + ":" + "cloudfoundry").getBytes()));
        resttemplate = restTemplateConfig.restTemplate();
        // CatalogController 를 MockMvC 객체로 만듬.
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getCatalog() throws Exception {
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_ID", "54e2de61-de84-4b9c-afc3-88d08aadfcb6");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_NAME", "redis");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_DESC", "\"A paasta source control service for application development.provision parameters : parameters {owner : owner}\"");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_BINDABLE_STRING", "false");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLANUPDATABLE_STRING", "true");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_BULLET_NAME", "100");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_BULLET_DESC", "100");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLAN1_ID", "2a26b717-b8b5-489c-8ef1-02bcdc445720");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLAN1_NAME", "jenkins");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLAN1_DESC", "jenkins plan test");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLAN1_TYPE", "A");
        catalog = catalogConfig.catalog();
        when(catalogService.getCatalog()).thenReturn(catalog);

        MvcResult result = this.mockMvc.perform(get(CatalogController.BASE_PATH)
                .header("X-Broker-Api-Version", "2")
                .header("Authorization", basicAuth)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void getCatalog_B() throws Exception {
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_ID", "54e2de61-de84-4b9c-afc3-88d08aadfcb6");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_NAME", "redis");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_DESC", "\"A paasta source control service for application development.provision parameters : parameters {owner : owner}\"");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_BINDABLE_STRING", "true");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLANUPDATABLE_STRING", "false");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_BULLET_NAME", "100");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_BULLET_DESC", "100");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLAN1_ID", "2a26b717-b8b5-489c-8ef1-02bcdc445720");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLAN1_NAME", "jenkins");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLAN1_DESC", "jenkins plan test");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLAN1_TYPE", "B");
        catalog = catalogConfig.catalog();
        when(catalogService.getCatalog()).thenReturn(catalog);
        MvcResult result = this.mockMvc.perform(get(CatalogController.BASE_PATH)
                .header("X-Broker-Api-Version", "2")
                .header("Authorization", basicAuth)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }
//
    @Test
    public void getCatalog_Default() throws Exception {

        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_ID", "54e2de61-de84-4b9c-afc3-88d08aadfcb6");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_NAME", "jenkins");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_DESC", "\"A paasta source control service for application development.provision parameters : parameters {owner : owner}\"");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_BINDABLE_STRING", "true");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLANUPDATABLE_STRING", "false");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_BULLET_NAME", "100");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_BULLET_DESC", "100");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLAN1_ID", "2a26b717-b8b5-489c-8ef1-02bcdc445720");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLAN1_NAME", "jenkins");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLAN1_DESC", "jenkins plan test");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLAN1_TYPE", "C");
        catalog = catalogConfig.catalog();
        when(catalogService.getCatalog()).thenReturn(catalog);

        MvcResult result = this.mockMvc.perform(get(CatalogController.BASE_PATH)
                .header("X-Broker-Api-Version", "2")
                .header("Authorization", basicAuth)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }
//
    @Test
    public void ContainerPlatformJenkinsCatalogService() throws Exception {

        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_ID", "54e2de61-de84-4b9c-afc3-88d08aadfcb6");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_NAME", "jenkins");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_DESC", "\"A paasta source control service for application development.provision parameters : parameters {owner : owner}\"");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_BINDABLE_STRING", "true");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLANUPDATABLE_STRING", "false");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_BULLET_NAME", "100");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_BULLET_DESC", "100");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLAN1_ID", "2a26b717-b8b5-489c-8ef1-02bcdc445720");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLAN1_NAME", "jenkins");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLAN1_DESC", "jenkins");
        ReflectionTestUtils.setField(catalogConfig, "SERVICEDEFINITION_PLAN1_TYPE", "C");
        catalog = catalogConfig.catalog();
        //when(new OnDemandCatalogService(catalog)).thenReturn(onDemandCatalogService);
        ContainerPlatformJenkinsCatalogService containerPlatformJenkinsCatalogService = new ContainerPlatformJenkinsCatalogService(catalog);

        assertThat(containerPlatformJenkinsCatalogService.getCatalog(), is(catalog));
        assertThat(containerPlatformJenkinsCatalogService.getServiceDefinition("54e2de61-de84-4b9c-afc3-88d08aadfcb6").getId(), is(catalog.getServiceDefinitions().get(0).getId()));

    }

}
