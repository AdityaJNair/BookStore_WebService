package library.service;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import library.content.domain.Author;
import library.content.domain.Book;
import library.content.dto.AuthorDTO;
import library.content.dto.BookDTO;
import library.content.dto.DTOMapper;

/**
 * Resource class for Authors.
 * Unable to delete an author object, as meaning would imply deleting all books associated which is not correct.
 * For a bookstore this is not a valid design or idea.
 * So a design constraint was to not allow deletes. Deletes would be done by the database admin.
 * @author adijn
 *
 */
@Path("/author")
public class AuthorResource {

	private static final Logger _logger = LoggerFactory.getLogger(AuthorResource.class);
	
	/**
	 * Gets the Author based on a path param of their name (which is a unique constraint)
	 * @param name
	 * @return
	 */
	@GET
	@Path("name/{name}")
	@Produces({"application/xml","application/json"})
	public AuthorDTO getBook(@PathParam("name") String name){
		//create entity manaa
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		Author a = null;
		try	{
			_logger.info("querying for author where name is = name");
			a = m.createQuery("SELECT a FROM Author a WHERE a.authorName=:name", Author.class).setParameter("name", name).getSingleResult();
		} catch (PersistenceException e){
			m.close();
			_logger.error("Entity not found");
			throw new EntityNotFoundException();
		}
		if (a == null){
			m.close();
			_logger.error("Entity not found");
			throw new EntityNotFoundException();
		}
		_logger.info("Map author domain to dto");
		AuthorDTO adto = DTOMapper.toAuthorDTO(a);
		m.getTransaction().commit();
		m.close();
		return adto;
	}
	
	
	/**
	 * Get author dto object from id value
	 * @param id
	 * @return AuthorDTO object
	 */
	@GET
	@Path("{id}")
	@Produces({"application/xml","application/json"})
	public Response getBook(@PathParam("id") long id, @Context UriInfo uriInfo){
		URI uri = uriInfo.getAbsolutePath();
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		_logger.info("Find particular author with id");
		Author b = m.find(Author.class, id);
		if (b == null) {
			m.close();
			_logger.error("Entity not found");
			throw new EntityNotFoundException();
		}
		AuthorDTO b1 = DTOMapper.toAuthorDTO(b);
		m.getTransaction().commit();
		m.close();
		_logger.info("Adding links to the description/book");
		Link description = Link.fromUri(uri + "/description").rel("description").build();
		Link book = Link.fromUri(uri + "/book").rel("book").build();
		ResponseBuilder builder = Response.ok(b1);
		builder.links(description,book);
		return builder.build();
	}
	
	/**
	 * Get a set of authorDTOs from the database
	 * @return
	 */
	@GET
	@Produces({"application/xml","application/json"})
	public Response getAuthors(){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		_logger.info("create set of authorDto to give back to user");
		Set<AuthorDTO> authorDTOset = new HashSet<AuthorDTO>();
		TypedQuery<Author> AuthorQuery = m.createQuery("FROM Author", Author.class);
		List<Author> listAuthor = AuthorQuery.getResultList();
		if (listAuthor == null) {
			m.close();
			_logger.error("Entity not found");
			return Response.status(404).build();
		}
		for(Author u: listAuthor){
			authorDTOset.add(DTOMapper.toAuthorDTO(u));
		}
		_logger.info("Creating entity for wrapper for author dtos");
		GenericEntity<Set<AuthorDTO>> entity = new GenericEntity<Set<AuthorDTO>>(authorDTOset){};
		m.getTransaction().commit();
		m.close();
		return Response.ok(entity).build();
	}
	
	/**
	 * Post a authorDTOs to the database
	 * @param bookdto
	 * @return
	 */
	@POST
	@Consumes({"application/xml","application/json"})
	public Response addAuthor(AuthorDTO bookdto){
		Author author = DTOMapper.toAuthorDomain(bookdto);
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		try{
			_logger.info("persisting author");
			m.persist(author);
			m.getTransaction().commit();
		} catch (PersistenceException p){
			_logger.error("Conflict");
			return Response.status(409).build();
		} finally {
			m.close();
		}
		return Response.created(URI.create("/author/" + author.get_authorId())).status(201).build();
	}
		
	
	/**
	 * Update author description based on its id of the author
	 */
	@PUT
	@Path("{id}/description")
	@Consumes({"application/xml","application/json"})
	public Response updateAuthorDescription(@PathParam("id") long id,String r){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		_logger.info("querying for author where id = id");
		Author author = m.find(Author.class, id);
		if (author == null) {
			m.close();
			_logger.error("Entity not found");
			throw new EntityNotFoundException();
		}
		author.set_description(r);
		m.getTransaction().commit();
		m.close();
		_logger.info("Successful Put");
		return Response.status(204).build();
	}
	
	/**
	 * Get set of all books by author based on the id of the author
	 */
	@GET
	@Path("{id}/book")
	@Produces({"application/xml","application/json"})
	public Response getAuthorBooks(@PathParam("id") long id){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		_logger.info("querying for all books that were authored by the id author");
		List<Book> bookQuery = m.createQuery("SELECT b FROM Book b WHERE b.author.authorId=:authorid", Book.class).setParameter("authorid", id).getResultList();
		if(bookQuery == null){
			m.close();
			_logger.error("Entity not found");
			return Response.status(404).build();
		}
		Set<BookDTO> bSet = new HashSet<BookDTO>();
		for(Book b: bookQuery){
			bSet.add(DTOMapper.toBookDTO(b));
		}
		_logger.info("Creating generic entity wrapper for set of bookdtos");
		GenericEntity<Set<BookDTO>> entity = new GenericEntity<Set<BookDTO>>(bSet){};
		m.getTransaction().commit();
		m.close();
		return Response.ok(entity).build();
	}
}
