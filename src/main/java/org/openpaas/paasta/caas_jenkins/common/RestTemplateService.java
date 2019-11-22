package org.openpaas.paasta.caas_jenkins.common;


import org.openpaas.paasta.caas_jenkins.repo.JpaAdminTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class RestTemplateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateService.class);
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String CONTENT_TYPE = "Content-Type";
    private final RestTemplate restTemplate;
    private final String commonApiBase64Authorization;
    private final String caas_api;
    private final CommonService commonService;
    private final JpaAdminTokenRepository jpaAdminTokenRepository;



    /**
     * Instantiates a new Rest template service.
     * @param restTemplate                   the rest template
     * @param commonApiAuthorizationId       the common api authorization id
     * @param commonApiAuthorizationPassword the common api authorization password
//    *@param adminTokenService
     * @param jpaAdminTokenRepository
     */
    @Autowired
    public RestTemplateService(RestTemplate restTemplate,
                               @Value("${caas.authorization.id}") String commonApiAuthorizationId,
                               @Value("${caas.authorization.password}") String commonApiAuthorizationPassword,
                               @Value("${caas.api}") String caas_api,
                               @Value("${jenkins.namespace}") String namespace,
                               @Value("${jenkins.username}") String jenkins_username,
                               @Value("${jenkins.password}") String jenkins_password,
                               @Value("${jenkins.docker_repository_url}") String docker_repository_url,
                               CommonService commonService, JpaAdminTokenRepository jpaAdminTokenRepository) throws IOException {
        this.restTemplate = restTemplate;
        this.caas_api = caas_api;
        this.jpaAdminTokenRepository = jpaAdminTokenRepository;
        this.commonApiBase64Authorization = "Basic "
                + Base64Utils.encodeToString(
                (commonApiAuthorizationId + ":" + commonApiAuthorizationPassword).getBytes(StandardCharsets.UTF_8));
        this.commonService = commonService;
        try {
            while(true){
            if(this.jpaAdminTokenRepository.existsByTokenName("caas_admin")) {
                String create_namespace = commonService.namespace();
                String auth_value = commonService.secret_data_value(docker_repository_url, jenkins_username, jenkins_password);
                String secret = commonService.secret(auth_value);
                send("/namespaces", HttpMethod.POST, create_namespace, Map.class);
                Map map = send("/namespaces/"+namespace+"/secrets", HttpMethod.POST, secret, Map.class);
                LOGGER.info("END CREATE NAMESPACE ::: "+ namespace +", SECRET" );
                break;
            }
                LOGGER.info("NOT CREATE NAMESPACE....... After 5s Retry" );
                Thread.sleep(5000);
            }
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Send t.
     *
     * @param <T>          the type parameter
     * @param reqUrl       the req url
     * @param httpMethod   the http method
     * @param bodyObject   the body object
     * @param responseType the response type
     * @return the t
     */
    public <T> T send(String reqUrl, HttpMethod httpMethod, Object bodyObject, Class<T> responseType) {

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.add(AUTHORIZATION_HEADER_KEY, commonApiBase64Authorization);
        reqHeaders.add(CONTENT_TYPE, "application/json");

        HttpEntity<Object> reqEntity;
        if (bodyObject == null) {
            reqEntity = new HttpEntity<>(reqHeaders);
        } else {
            reqEntity = new HttpEntity<>(bodyObject, reqHeaders);
        }

        LOGGER.info("<T> T SEND :: REQUEST: {} BASE-URL: {}, CONTENT-TYPE: {}", httpMethod, reqUrl, reqHeaders.get(CONTENT_TYPE));
        ResponseEntity<T> resEntity = restTemplate.exchange(caas_api+reqUrl, httpMethod, reqEntity, responseType);

        if (resEntity.getBody() != null) {
            LOGGER.info("RESPONSE-TYPE: {}", resEntity.getBody().getClass());
        } else {
            LOGGER.error("RESPONSE-TYPE: RESPONSE BODY IS NULL");
        }

        return resEntity.getBody();
    }
}
