package model;

import org.openpaas.servicebroker.model.*;

import java.util.HashMap;
import java.util.Map;

public class ServiceInstanceRequestModel {

    public static Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("test", "test");
        return parameters;
    }

    public static Map<String, Object> getBindParameters() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("app_guid", "app_guid");
        return parameters;
    }

    public static PreviousValues getPreviousValues() {

        CreateServiceInstanceRequest createServiceInstanceRequest = getCreateServiceInstanceRequest();

        return new PreviousValues(createServiceInstanceRequest.getPlanId(),
                createServiceInstanceRequest.getServiceDefinitionId(),
                createServiceInstanceRequest.getOrganizationGuid(),
                createServiceInstanceRequest.getSpaceGuid());

    }

    public static CreateServiceInstanceRequest getCreateServiceInstanceRequest() {
        ServiceDefinition service = ServiceDefinitionModel.getService();

        return new CreateServiceInstanceRequest(
                service.getId(),
                service.getPlans().get(0).getId(),
                "org_guid",
                "space_guid",
                getParameters()
        );
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
