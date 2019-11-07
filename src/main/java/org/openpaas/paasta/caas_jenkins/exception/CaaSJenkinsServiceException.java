package org.openpaas.paasta.caas_jenkins.exception;


import org.openpaas.servicebroker.exception.ServiceBrokerException;

public class CaaSJenkinsServiceException extends ServiceBrokerException {
    public CaaSJenkinsServiceException(String message) {
        super(message);
    }
}
