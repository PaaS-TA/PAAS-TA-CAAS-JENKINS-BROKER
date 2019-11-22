package org.openpaas.paasta.caas_jenkins.service.impl;


import org.openpaas.paasta.caas_jenkins.exception.CaaSJenkinsServiceException;
import org.openpaas.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.openpaas.servicebroker.model.DeleteServiceInstanceBindingRequest;
import org.openpaas.servicebroker.model.ServiceInstanceBinding;
import org.openpaas.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;

@Service
public class CaaSServiceServiceBinding implements ServiceInstanceBindingService {

    @Override
    public ServiceInstanceBinding createServiceInstanceBinding(CreateServiceInstanceBindingRequest request) throws CaaSJenkinsServiceException{
        throw new CaaSJenkinsServiceException("Not Supported");
    }

    @Override
    public ServiceInstanceBinding deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest request) throws CaaSJenkinsServiceException {
        throw new CaaSJenkinsServiceException("Not Supported");
    }
}
