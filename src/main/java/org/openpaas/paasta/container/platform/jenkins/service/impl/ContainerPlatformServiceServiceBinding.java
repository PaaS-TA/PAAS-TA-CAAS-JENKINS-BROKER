package org.openpaas.paasta.container.platform.jenkins.service.impl;


import org.openpaas.paasta.container.platform.jenkins.exception.ContainerPlatformJenkinsServiceException;
import org.openpaas.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.openpaas.servicebroker.model.DeleteServiceInstanceBindingRequest;
import org.openpaas.servicebroker.model.ServiceInstanceBinding;
import org.openpaas.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;

@Service
public class ContainerPlatformServiceServiceBinding implements ServiceInstanceBindingService {

    @Override
    public ServiceInstanceBinding createServiceInstanceBinding(CreateServiceInstanceBindingRequest request) throws ContainerPlatformJenkinsServiceException{
        throw new ContainerPlatformJenkinsServiceException("Not Supported");
    }

    @Override
    public ServiceInstanceBinding deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest request) throws ContainerPlatformJenkinsServiceException {
        throw new ContainerPlatformJenkinsServiceException("Not Supported");
    }
}
