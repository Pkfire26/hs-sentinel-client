package com.hico.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import com.hico.config.*;

@Configuration
@ComponentScan("com.hico")
public class SentinelConfig {

	@Bean (name = "restClient")
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptors
            = restTemplate.getInterceptors();
        if ((interceptors == null) || (interceptors.size() == 0)) {
            interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        }
        interceptors.add(
                new HeaderRequestInterceptor(HttpHeaders.ACCEPT, "application/json"));
        interceptors.add(
                new HeaderRequestInterceptor(HttpHeaders.CONTENT_TYPE, "application/json"));
        System.out.println("foo :" + restTemplate.getMessageConverters().toString());
        restTemplate.setInterceptors(interceptors);
        /*
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(new ObjectMapper());
		restTemplate.getMessageConverters().add(converter);
        */
		return restTemplate;
	}
}
