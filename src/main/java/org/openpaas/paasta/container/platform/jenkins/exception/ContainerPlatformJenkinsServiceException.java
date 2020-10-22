package org.openpaas.paasta.container.platform.jenkins.exception;


import org.openpaas.servicebroker.exception.ServiceBrokerException;

public class ContainerPlatformJenkinsServiceException extends ServiceBrokerException {
    public ContainerPlatformJenkinsServiceException(String message) {
        super(message);
    }
}
