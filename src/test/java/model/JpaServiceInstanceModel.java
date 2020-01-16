package model;

import org.openpaas.paasta.caas_jenkins.model.JpaServiceInstance;
import org.openpaas.servicebroker.model.CreateServiceInstanceRequest;
import org.openpaas.servicebroker.model.ServiceDefinition;

import java.util.HashMap;
import java.util.Map;

public class JpaServiceInstanceModel {

    public static JpaServiceInstance getJpaServiceInstance(){
        return new JpaServiceInstance();
    }

    public static CreateServiceInstanceRequest getCreateServiceInstanceRequest() {
        ServiceDefinition service = ServiceDefinitionModel.getService();
        return new CreateServiceInstanceRequest(
                service.getId(),
                service.getPlans().get(0).getId(),
                "org_guid",
                "space_guid",
                getParameters()
        ).withServiceDefinition(service);
    }
    public static Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("test", "test");
        return parameters;
    }
}
