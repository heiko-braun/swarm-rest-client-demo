package com.example.demo.rest;


import java.net.MalformedURLException;
import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Path("/hello")
@ApplicationScoped
public class HelloWorldEndpoint {

	@Inject
	HttpBin httpBinBean;

	@GET
	@Produces("text/plain")
	public Response doGetThroughFactory() {

		RestClientBuilder builder = RestClientBuilder.newBuilder();
		try {
			builder.baseUrl(new URL("http://httpbin.org"));
			String result = builder.build(HttpBin.class).doGet();
			return Response.ok(result).build();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

	}

	@GET
	@Path("/bean")
	@Produces("text/plain")
	public Response doGetThroughBean() {

		String result = httpBinBean.doGet();
		return Response.ok(result).build();

	}

	@ApplicationScoped
	@Path("/get")
	@RegisterRestClient
	public interface HttpBin {

		@GET
		@Produces("text/plain")
		String doGet();
	}

}
