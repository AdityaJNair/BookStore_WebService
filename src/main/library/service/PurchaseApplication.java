package main.library.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class PurchaseApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();

	public PurchaseApplication() {
		PurchaseResource resource = new PurchaseResource();
		singletons.add(resource);
		// classes.add();
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}
}
