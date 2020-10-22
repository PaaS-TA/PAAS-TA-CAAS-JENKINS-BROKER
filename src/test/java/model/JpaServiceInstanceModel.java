package model;

import org.openpaas.paasta.container.platform.jenkins.model.JpaServiceInstance;
import org.openpaas.servicebroker.model.*;

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

    public static CreateServiceInstanceBindingRequest getCreateServiceInstanceBindingRequest() {

        ServiceDefinition service = ServiceDefinitionModel.getService();

        return new CreateServiceInstanceBindingRequest(
                service.getId(),
                service.getPlans().get(0).getId(),
                "app_guid");
    }

    public static DeleteServiceInstanceRequest getDeleteServiceInstanceRequest() {

        ServiceInstance service = ServiceInstanceModel.getServiceInstance();

        return new DeleteServiceInstanceRequest(
                service.getServiceInstanceId(),
                service.getServiceDefinitionId(),
                service.getPlanId());
    }

    public static UpdateServiceInstanceRequest getUpdateServiceInstanceRequest() {

        ServiceInstance service = ServiceInstanceModel.getServiceInstance();

        return new UpdateServiceInstanceRequest(
                service.getPlanId(),
                service.getServiceDefinitionId());
    }

    public static DeleteServiceInstanceBindingRequest getDeleteServiceInstanceBindingRequest() {

        ServiceInstance service = ServiceInstanceModel.getServiceInstance();

        return new DeleteServiceInstanceBindingRequest(
                "test_bindingId",
                service,
                service.getServiceDefinitionId(),
                service.getPlanId());
    }
}
