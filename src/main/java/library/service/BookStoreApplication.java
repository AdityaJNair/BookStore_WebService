package library.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/services")
public class BookStoreApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();

	public BookStoreApplication() {
		singletons.add(new BookResource());
		singletons.add(new AuthorResource());
		singletons.add(new UserResource());
		singletons.add(new WebExceptionMapper());
		singletons.add(PersistenceManager.instance());
		
		databaseInit();
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
	
	static void databaseInit(){
		
		
		
	}
}
