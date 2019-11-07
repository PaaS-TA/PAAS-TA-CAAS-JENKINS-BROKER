package org.openpaas.paasta.caas_jenkins.repo;

import org.openpaas.paasta.caas_jenkins.model.JpaServiceInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaServiceInstanceRepository extends JpaRepository<JpaServiceInstance, String> {

    List<JpaServiceInstance> findAllByOrganizationGuid(String organizationId);

    JpaServiceInstance findByServiceInstanceId(String serviceInstanceId);

    JpaServiceInstance findByOrganizationGuid(String organizationId);

}
