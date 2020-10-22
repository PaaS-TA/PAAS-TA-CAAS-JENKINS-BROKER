package org.openpaas.paasta.jenkins.test;

import model.JpaServiceInstanceModel;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.openpaas.paasta.container.platform.jenkins.exception.ContainerPlatformJenkinsServiceException;

import org.openpaas.paasta.container.platform.jenkins.service.impl.ContainerPlatformServiceServiceBinding;

import org.openpaas.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.openpaas.servicebroker.model.DeleteServiceInstanceBindingRequest;

import org.springframework.boot.test.context.SpringBootTest;



import static org.assertj.core.api.Assertions.assertThatThrownBy;



@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContainerPlatformJenkinsInstanceServiceBindingTest {

    @InjectMocks
    ContainerPlatformServiceServiceBinding containerPlatformServiceServiceBinding;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createServiceInstanceBinding_test1() throws Exception {
        CreateServiceInstanceBindingRequest request = JpaServiceInstanceModel.getCreateServiceInstanceBindingRequest();
        assertThatThrownBy(() ->  containerPlatformServiceServiceBinding.createServiceInstanceBinding(request))
                .isInstanceOf(ContainerPlatformJenkinsServiceException.class).hasMessageContaining("Not Supported");
       ;
    }

    @Test
    public void createServiceInstanceUnBinding_test1() throws Exception {
        DeleteServiceInstanceBindingRequest request = JpaServiceInstanceModel.getDeleteServiceInstanceBindingRequest();;
        assertThatThrownBy(() ->  containerPlatformServiceServiceBinding.deleteServiceInstanceBinding(request))
                .isInstanceOf(ContainerPlatformJenkinsServiceException.class).hasMessageContaining("Not Supported");
    }
}
