package model;

import org.openpaas.paasta.container.platform.jenkins.model.JpaJenkinsInstance;

public class JpaJenkinsInstanceModel {

    public static JpaJenkinsInstance getJpaJenkinsInstance(){
        return new JpaJenkinsInstance();
    }

    public static JpaJenkinsInstance getJpaJenkinsInstance(String organizationGuid , String serviceInstanceId, String namespace){
        return new JpaJenkinsInstance(organizationGuid, serviceInstanceId, namespace);
    }

}
