package acme.components.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import acme.framework.controllers.AbstractController;

@Configuration
@ComponentScan(basePackages = {"acme.framework"}, excludeFilters={
	  @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=AbstractController.class)})
public class CustomControllerConfiguration {

}
