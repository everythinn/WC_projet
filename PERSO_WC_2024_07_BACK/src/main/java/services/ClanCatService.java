package services;

import java.util.List;

import dao.bddimpl.ClanCatDaoBdd;
import dao.bddimpl.DaoFactory;
import enums.GenderEnum;
import enums.RankEnum;
import exceptions.DaoException;
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
import models.ClanCat;

@Path("/clancat")
public class ClanCatService {
	
	/**
	 * @GET all clan cats in database
	 * @return
	 * @throws DaoException
	 */
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClanCats() throws DaoException {
		try {
			DaoFactory.getInstance();
			List<ClanCat> cats = null;
			cats = DaoFactory.getClanCatDao().readAllObject();
			if (cats == null || cats.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND).entity("Clan cats not found").build();
			}
			final GenericEntity<List<ClanCat>> json = new GenericEntity<List<ClanCat>>(cats) {};
			return Response.ok().entity(json).build();
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @GET specific Clan cat by its id
	 * @param idClanCat
	 * @return
	 * @throws DaoException
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClanCatById(@PathParam("id") int idClanCat) throws DaoException {
		ClanCat cat = null;
		try {
			DaoFactory.getInstance();
			cat = DaoFactory.getClanCatDao().readObject(idClanCat);
			if (cat == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Clan cat not found").build();
			} else {
				final GenericEntity<ClanCat> json = new GenericEntity<ClanCat>(cat) {};				
				return Response.status(Response.Status.OK).entity(json).build();
			}
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @POST creates new Clan cat
	 * @param prefix AND suffix AND age AND gender (0 (female) or 1 (male)) AND appearance AND Clan AND rank (l (leader), d (deputy), m (med cat), w (warrior), a (apprentice), k (kit), e (elder))
	 * @return
	 */
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addClanCat(final MultivaluedMap<String, String> formParams) {
		if (formParams.get("prefix") == null || formParams.get("suffix") == null || formParams.get("age") == null || formParams.get("gender") == null 
				|| formParams.get("appearance") == null || formParams.get("Clan") == null || formParams.get("rank") == null) {
			return Response.status(Response.Status.EXPECTATION_FAILED).entity("Parameters missing").build();
		}
		try {
			final ClanCat clanCat = new ClanCat();
			DaoFactory.getInstance();
			final RankEnum rank;
			switch(formParams.getFirst("rank")) {
				case "l":
					rank = RankEnum.LEADER;
					break;
				case "d":
					rank = RankEnum.DEPUTY;
					break;
				case "m":
					rank = RankEnum.MEDICINE_CAT;
					break;
				case "w":
					rank = RankEnum.WARRIOR;
					break;
				case "a":
					rank = RankEnum.APPRENTICE;
					break;
				case "k":
					rank = RankEnum.KIT;
					break;
				case "e":
					rank = RankEnum.ELDER;
					break;
				default:
					rank = RankEnum.WARRIOR;
					break;
			}
			final String prefix = formParams.getFirst("prefix");
			final String suffix = formParams.getFirst("suffix");
			final Integer age = Integer.parseInt(formParams.getFirst("age"));
			final GenderEnum gender;
			final Integer genderNb = Integer.parseInt(formParams.getFirst("gender"));
			if(genderNb == 0) {
				gender = GenderEnum.FEMALE;
			} else if (genderNb == 1){
				gender = GenderEnum.MALE;
			} else return Response.status(Response.Status.EXPECTATION_FAILED).entity("Gender incorrectly entered.").build();
			final String appearance = formParams.getFirst("appearance");
			final Integer clanId = Integer.parseInt(formParams.getFirst("clan"));
			final Clan clan = DaoFactory.getClanDao().readObject(clanId);
			clanCat.setPrefix(prefix);
			clanCat.setSuffix(rank, suffix);
			clanCat.setAge(age);
			clanCat.setGender(gender);
			clanCat.setAppearance(appearance);
			clanCat.setClan(clan);
			ClanCatDaoBdd clanCatDaoBdd = new ClanCatDaoBdd();
			clanCatDaoBdd.createObject(clanCat);
			return Response.status(Response.Status.CREATED).entity(clanCat).build();
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Clan cat creation failed").build();
		}
	}
	
	/**
	 * @PUT updates one or more attributes of a specific Clan cat
	 * @param idClanCat
	 * @param prefix AND/OR suffix AND/OR age AND/OR gender (0 (female) or 1 (male)) AND/OR appearance AND/OR Clan AND/OR rank (l (leader), d (deputy), m (med cat), w (warrior), a (apprentice), k (kit), e (elder))
	 * @return
	 */
	@PUT
	@Consumes("application/x-www-form-urlencoded\")\r\n")
	@Path("/update/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateClanCat(@PathParam("id") int idClanCat, MultivaluedMap<String, String> formParams) {
		if (formParams.get("prefix") == null && formParams.get("suffix") == null && formParams.get("age") == null && formParams.get("gender") == null 
				|| formParams.get("appearance") == null && formParams.get("Clan") == null && formParams.get("rank") == null) {
			return Response.status(Response.Status.EXPECTATION_FAILED).entity("All fields empty").build();
		}
		ClanCat cat = null;
		try {
			DaoFactory.getInstance();
			cat = DaoFactory.getClanCatDao().readObject(idClanCat);
			if (formParams.getFirst("rank") != null) {
				final RankEnum rank;
				switch(formParams.getFirst("rank")) {
				case "l":
					rank = RankEnum.LEADER;
					break;
				case "d":
					rank = RankEnum.DEPUTY;
					break;
				case "m":
					rank = RankEnum.MEDICINE_CAT;
					break;
				case "w":
					rank = RankEnum.WARRIOR;
					break;
				case "a":
					rank = RankEnum.APPRENTICE;
					break;
				case "k":
					rank = RankEnum.KIT;
					break;
				case "e":
					rank = RankEnum.ELDER;
					break;
				default:
					rank = RankEnum.WARRIOR;
					break;
				}
				cat.setRank(rank);
			}
			final RankEnum updatedRank = cat.getRank();
			if (formParams.getFirst("prefix") != null) {
				final String prefix = formParams.getFirst("prefix");
				cat.setPrefix(prefix);	
			}
			if (formParams.getFirst("suffix") != null) {
				final String suffix = formParams.getFirst("suffix");
				cat.setSuffix(updatedRank, suffix);	
			}
			if (formParams.getFirst("age") != null) {
				final Integer age = Integer.parseInt(formParams.getFirst("age"));
				cat.setAge(age);	
			}
			if (formParams.getFirst("gender") != null) {
				final Integer genderNb = Integer.parseInt(formParams.getFirst("gender"));
				if (genderNb == 0) {
					final GenderEnum gender = GenderEnum.FEMALE;
					cat.setGender(gender);
				} else if (genderNb == 1) {
					final GenderEnum gender = GenderEnum.MALE;
					cat.setGender(gender);
				}  else return Response.status(Response.Status.EXPECTATION_FAILED).entity("Gender entered is incorrect").build();
			}
			if (formParams.getFirst("appearance") != null) {
				final String appearance = formParams.getFirst("appearance");
				cat.setAppearance(appearance);	
			}
			if (formParams.getFirst("clan") != null) {
				final Integer clanId = Integer.parseInt(formParams.getFirst("clan"));
				final Clan clan = DaoFactory.getClanDao().readObject(clanId);
				cat.setClan(clan);
			}
			return Response.status(Response.Status.OK).entity(cat).build();
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Clan cat update failed.").build();
		}
	}
	
	/**
	 * @DELETE deletes a specific Clan cat
	 * @param idClancat
	 * @return
	 * @throws DaoException
	 */
	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteClanCat(@PathParam("id") int idClancat) throws DaoException {
		ClanCat cat = null;
		try {
			DaoFactory.getInstance();
			cat = DaoFactory.getClanCatDao().readObject(idClancat);
			if ( cat == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Clan cat not found").build();	
			} else {
				ClanCatDaoBdd clanCatDaoBdd = new ClanCatDaoBdd();
				clanCatDaoBdd.deleteObject(cat);
				return Response.status(Response.Status.OK).entity("Clan cat deletion successful.").build();
			}
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @GET all leaders
	 * @return
	 * @throws DaoException
	 */
	@GET
	@Path("/get/leaders")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllLeaders() throws DaoException {
		try {
			DaoFactory.getInstance();
			List<ClanCat> leaders = null;
			leaders = ((ClanCatDaoBdd) DaoFactory.getClanCatDao()).readAllLeaders();
			if (leaders == null || leaders.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND).entity("Leaders not found").build();
			}
			final GenericEntity<List<ClanCat>> json = new GenericEntity<List<ClanCat>>(leaders) {};
			return Response.ok().entity(json).build();
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @GET all deputies
	 * @return
	 * @throws DaoException
	 */
	@GET
	@Path("/get/deputies")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllDeputies() throws DaoException {
		try {
			DaoFactory.getInstance();
			List<ClanCat> deputies = null;
			deputies = ((ClanCatDaoBdd) DaoFactory.getClanCatDao()).readAllDeputies();
			if (deputies == null || deputies.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND).entity("Deputies not found").build();
			}
			final GenericEntity<List<ClanCat>> json = new GenericEntity<List<ClanCat>>(deputies) {};
			return Response.ok().entity(json).build();
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @GET all medicine cats
	 * @return
	 * @throws DaoException
	 */
	@GET
	@Path("/get/medcats")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMedicineCats() throws DaoException {
		try {
			DaoFactory.getInstance();
			List<ClanCat> medcats = null;
			medcats = ((ClanCatDaoBdd) DaoFactory.getClanCatDao()).readAllMedicineCats();
			if (medcats == null || medcats.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND).entity("Medicine cats not found").build();
			}
			final GenericEntity<List<ClanCat>> json = new GenericEntity<List<ClanCat>>(medcats) {};
			return Response.ok().entity(json).build();
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
}