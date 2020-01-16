package org.openpaas.paasta.jenkins.test;

import model.JpaServiceInstanceModel;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.openpaas.paasta.caas_jenkins.exception.CaaSJenkinsServiceException;

import org.openpaas.paasta.caas_jenkins.service.impl.CaaSServiceServiceBinding;

import org.openpaas.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.openpaas.servicebroker.model.DeleteServiceInstanceBindingRequest;

import org.springframework.boot.test.context.SpringBootTest;



import static org.assertj.core.api.Assertions.assertThatThrownBy;



@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CaaSJenkinsInstanceServiceBindingTest {

    @InjectMocks
    CaaSServiceServiceBinding caaSServiceServiceBinding;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createServiceInstanceBinding_test1() throws Exception {
        CreateServiceInstanceBindingRequest request = JpaServiceInstanceModel.getCreateServiceInstanceBindingRequest();
        assertThatThrownBy(() ->  caaSServiceServiceBinding.createServiceInstanceBinding(request))
                .isInstanceOf(CaaSJenkinsServiceException.class).hasMessageContaining("Not Supported");
       ;
    }

    @Test
    public void createServiceInstanceUnBinding_test1() throws Exception {
        DeleteServiceInstanceBindingRequest request = JpaServiceInstanceModel.getDeleteServiceInstanceBindingRequest();;
        assertThatThrownBy(() ->  caaSServiceServiceBinding.deleteServiceInstanceBinding(request))
                .isInstanceOf(CaaSJenkinsServiceException.class).hasMessageContaining("Not Supported");
    }
}
