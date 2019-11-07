package org.openpaas.paasta.caas_jenkins.service.impl;

import org.openpaas.servicebroker.exception.ServiceBrokerException;
import org.openpaas.servicebroker.model.Catalog;
import org.openpaas.servicebroker.model.ServiceDefinition;
import org.openpaas.servicebroker.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CaaSJenkinsCatalogService implements CatalogService {

    private Catalog catalog;
    private Map<String, ServiceDefinition> serviceDefs = new HashMap<String, ServiceDefinition>();

    @Autowired
    public CaaSJenkinsCatalogService(Catalog catalog) {
        this.catalog = catalog;
        initializeMap();
    }

    private void initializeMap() {
        for (ServiceDefinition def : catalog.getServiceDefinitions()) {
            serviceDefs.put(def.getId(), def);
        }
    }

    @Override
    public Catalog getCatalog() throws ServiceBrokerException {
        return catalog;
    }

    @Override
    public ServiceDefinition getServiceDefinition(String serviceId) throws ServiceBrokerException {
        return serviceDefs.get(serviceId);
    }
}
