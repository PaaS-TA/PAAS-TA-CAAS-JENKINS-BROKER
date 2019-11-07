package org.openpaas.paasta.caas_jenkins.repo;

import org.openpaas.paasta.caas_jenkins.model.JpaJenkinsInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaJenkinsInstanceRepository extends JpaRepository<JpaJenkinsInstance, String> {

    boolean existsByOrganizationGuid(String organization_guid);

    boolean existsByServiceInstanceId(String serviceInstanceId);

    JpaJenkinsInstance findByServiceInstanceId(String serviceInstanceId);

}
