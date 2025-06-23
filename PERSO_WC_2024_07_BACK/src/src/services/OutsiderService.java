package src.services;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import src.dao.OutsiderDaoBdd;
import src.dao.impl.bdd.DaoFactory;
import src.enums.GenderEnum;
import src.enums.StatusEnum;
import src.exceptions.DaoException;
import src.models.Outsider;

@Path("/outsider")
public class OutsiderService {
	
	/**
	 * @GET all outsiders in database
	 * @return
	 * @throws DaoException
	 */
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOutsiders() throws DaoException {
		try {
			DaoFactory.getInstance();
			List<Outsider> outsiders = null;
			outsiders = DaoFactory.getOutsiderDao().readAllObject();
			if (outsiders == null || outsiders.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND).entity("Outsiders not found").build();
			}
			final GenericEntity<List<Outsider>> json = new GenericEntity<List<Outsider>>(outsiders) {};
			return Response.ok().entity(json).build();
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @GET specific outsider by its id
	 * @param idOutsider
	 * @return
	 * @throws DaoException
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOutsiderById(@PathParam("id") int idOutsider) throws DaoException {
		Outsider out = null;
		try {
			DaoFactory.getInstance();
			out = DaoFactory.getOutsiderDao().readObject(idOutsider);
			if (out == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Outsider not found").build();
			} else {
				final GenericEntity<Outsider> json = new GenericEntity<Outsider>(out) {};				
				return Response.status(Response.Status.OK).entity(json).build();
			}
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @POST creates new Outsider
	 * @param name AND age AND gender (0 (female) or 1 (male)) AND appearance AND status (k (kittypet), l (loner) or r (rogue))
	 * @return
	 */
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addOutsider(final MultivaluedMap<String, String> formParams) {
		if (formParams.get("name") == null || formParams.get("age") == null || formParams.get("gender") == null || formParams.get("appearance") == null || formParams.get("status") == null) {
			return Response.status(Response.Status.EXPECTATION_FAILED).entity("Parameters missing").build();
		}
		try {
			final Outsider outsider = new Outsider();
			final String name = formParams.getFirst("name");
			final Integer age = Integer.parseInt(formParams.getFirst("age"));
			final Integer genderNb = Integer.parseInt(formParams.getFirst("gender"));
			final GenderEnum gender;
			if (genderNb == 0) {
				gender = GenderEnum.FEMALE;
			} else if (genderNb == 1) {
				gender = GenderEnum.MALE;
			} else return Response.status(Response.Status.EXPECTATION_FAILED).entity("Gender entered is incorrect").build();
			final String appearance = formParams.getFirst("appearance");
			final StatusEnum status;
			switch(formParams.getFirst("status")) {
				case "k":
					status = StatusEnum.KITTYPET;
					break;
				case "r":
					status = StatusEnum.ROGUE;
					break;
				case "l":
					status = StatusEnum.LONER;
					break;
				default :
					return Response.status(Response.Status.EXPECTATION_FAILED).entity("Status entered is incorrect").build();
			}
			outsider.setName(name);
			outsider.setAge(age);
			outsider.setGender(gender);
			outsider.setAppearance(appearance);
			outsider.setStatus(status);
			OutsiderDaoBdd outDaoBdd = new OutsiderDaoBdd();
			outDaoBdd.createObject(outsider);
			return Response.status(Response.Status.CREATED).entity(outsider).build();
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Outsider creation failed").build();
		}
	}
	
	/**
	 * @PUT updates one or more attributes of a specific outsider
	 * @param idOutsider
	 * @param name AND/OR age AND/OR gender (0 (female) or 1 (male)) AND/OR appearance AND/OR status (k (kittypet), l (loner) or r (rogue))
	 * @return
	 */
	@PUT
	@Consumes("application/x-www-form-urlencoded\")\r\n")
	@Path("/update/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateOutsider(@PathParam("id") int idOutsider, MultivaluedMap<String, String> formParams) {
		if (formParams.get("name") == null && formParams.get("age") == null && formParams.get("gender") == null && formParams.get("appearance") == null && formParams.get("status") == null) {
			return Response.status(Response.Status.EXPECTATION_FAILED).entity("All fields empty").build();
		}
		Outsider out = null;
		try {
			DaoFactory.getInstance();
			out = DaoFactory.getOutsiderDao().readObject(idOutsider);
			if (formParams.getFirst("name") != null) {
				final String name = formParams.getFirst("name");
				out.setName(name);	
			}
			if (formParams.getFirst("age") != null) {
				final Integer age = Integer.parseInt(formParams.getFirst("age"));
				out.setAge(age);	
			}
			if (formParams.getFirst("gender") != null) {
				final Integer genderNb = Integer.parseInt(formParams.getFirst("gender"));
				if (genderNb == 0) {
					final GenderEnum gender = GenderEnum.FEMALE;
					out.setGender(gender);
				} else if (genderNb == 1) {
					final GenderEnum gender = GenderEnum.MALE;
					out.setGender(gender);
				}  else return Response.status(Response.Status.EXPECTATION_FAILED).entity("Gender entered is incorrect").build();
			}
			if (formParams.getFirst("appearance") != null) {
				final String appearance = formParams.getFirst("appearance");
				out.setAppearance(appearance);	
			}
			final StatusEnum status;
			switch(formParams.getFirst("status")) {
				case "k":
					status = StatusEnum.KITTYPET;
					out.setStatus(status);
					break;
				case "r":
					status = StatusEnum.ROGUE;
					out.setStatus(status);
					break;
				case "l":
					status = StatusEnum.LONER;
					out.setStatus(status);
					break;
				default:
					return Response.status(Response.Status.EXPECTATION_FAILED).entity("Status entered is incorrect").build();
			}
			return Response.status(Response.Status.OK).entity(out).build();
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Outsider update failed.").build();
		}
	}
	
	/**
	 * @DELETE deletes a specific outsider
	 * @param idOutsider
	 * @return
	 * @throws DaoException
	 */
	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteOutsider(@PathParam("id") int idOutsider) throws DaoException {
		Outsider out = null;
		try {
			DaoFactory.getInstance();
			out = DaoFactory.getOutsiderDao().readObject(idOutsider);
			if ( out == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Outsider not found").build();	
			} else {
				OutsiderDaoBdd outDaoBdd = new OutsiderDaoBdd();
				outDaoBdd.deleteObject(out);
				return Response.status(Response.Status.OK).entity("Outsider deletion successful.").build();
			}
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
}
