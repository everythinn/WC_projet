package services;

import java.util.List;

import dao.bddimpl.ClanCatDaoBdd;
import dao.bddimpl.DaoFactory;
import dao.interfaces.IClanCatDao;
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
import models.Clan;
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
			final List<ClanCat> ret = DaoFactory.getInstance().getClanCatDao().getAllClanCats();
			final GenericEntity<List<ClanCat>> json = new GenericEntity<>(ret) {};
			return Response.ok().entity(json).build();
		} catch (final Exception e) {
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
		ClanCat s = null;
		try {
			s = DaoFactory.getInstance().getClanCatDao().getClanCatById(idClanCat);
			if(s == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Aucun chat de Clan avec l'id [".concat(Integer.toString(idClanCat))
						.concat("] n'a été trouvé.")).build();
			}
			final GenericEntity<ClanCat> json = new GenericEntity<>(s) {};
			return Response.ok().entity(json).build();
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	
	/**
	 * @POST creates new Clan cat
	 * @param prefix AND suffix AND age AND gender (0 (female) or 1 (male)) AND appearance AND Clan AND rank (l (leader), d (deputy), m (med cat), w (warrior), a (apprentice), k (kit), e (elder))
	 * @return
	 * @throws Exception 
	 */
	@POST
	@Path("/add")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addClanCat(final MultivaluedMap<String, String> formParams) throws Exception {
		if (formParams.get("prefix") == null || formParams.get("suffix") == null || formParams.get("age") == null || formParams.get("gender") == null 
				|| formParams.get("appearance") == null || formParams.get("Clan") == null || formParams.get("rank") == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("L'un des paramètres obligatoires n'est pas fourni.").build();
		}
		final String prefix = formParams.getFirst("prefix");
		final String suffix = formParams.getFirst("suffix");
		final String ageTemp = formParams.getFirst("age");
		final String genderTemp = formParams.getFirst("gender");
		final String rankTemp = formParams.getFirst("rank");
		final String appearance = formParams.getFirst("appearance");
		final String clanIdTemp = formParams.getFirst("Clan");
		if (prefix == null || prefix.isBlank() || suffix == null || suffix.isBlank() || ageTemp == null || ageTemp.isEmpty() || genderTemp == null || 
				genderTemp.isBlank() || rankTemp == null || rankTemp.isBlank() || appearance == null || appearance.isBlank() || clanIdTemp == null || 
				clanIdTemp.isBlank()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("L'un des paramètres obligatoires n'est pas fourni.").build();
		}
		int age;
		int genderNb;
		GenderEnum gender;
		int clanId;
		Clan clan;
		RankEnum clanRank;
		try {
			age = Integer.parseInt(ageTemp);
		} catch (final NumberFormatException e){
			return Response.status(Response.Status.BAD_REQUEST).entity("L'age fourni n'est pas valide.").build();
		}
		try {
			genderNb = Integer.parseInt(genderTemp);
			if(genderNb == 0) {
				gender = GenderEnum.FEMALE;
			} else if (genderNb == 1){
				gender = GenderEnum.MALE;
			} else return Response.status(Response.Status.EXPECTATION_FAILED).entity("Gender incorrectly entered.").build();
		} catch (final NumberFormatException e){
			return Response.status(Response.Status.BAD_REQUEST).entity("L'id de Clan fourni n'est pas valide.").build();
		}
		try {
			clanId = Integer.parseInt(ageTemp);
			try {
				clan = DaoFactory.getInstance().getClanDao().getClanById(clanId);
			} catch (Exception e) {
				return Response.status(Response.Status.EXPECTATION_FAILED).entity("Clan id incorrectly entered.").build();
			}
		} catch (final NumberFormatException e){
			return Response.status(Response.Status.BAD_REQUEST).entity("L'id de Clan fourni n'est pas valide.").build();
		}
		switch(formParams.getFirst("rank")) {
			case "l":
				clanRank = RankEnum.LEADER;
				break;
			case "d":
				clanRank = RankEnum.DEPUTY;
				break;
			case "m":
				clanRank = RankEnum.MEDICINE_CAT;
				break;
			case "w":
				clanRank = RankEnum.WARRIOR;
				break;
			case "a":
				clanRank = RankEnum.APPRENTICE;
				break;
			case "k":
				clanRank = RankEnum.KIT;
				break;
			case "e":
				clanRank = RankEnum.ELDER;
				break;
			default:
				clanRank = RankEnum.WARRIOR;
				break;
		}
		ClanCat cc = new ClanCat(age, gender, appearance, prefix, clan, clanRank, suffix);
		try {
			cc = DaoFactory.getInstance().getClanCatDao().createClanCat(cc);
			final GenericEntity<ClanCat> json = new GenericEntity<>(cc) {};
			return Response.ok().entity(json).build();
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @PUT updates one or more attributes of a specific Clan cat
	 * @param idClanCat
	 * @param prefix AND/OR suffix AND/OR age AND/OR gender (0 (female) or 1 (male)) AND/OR appearance AND/OR Clan AND/OR rank (l (leader), 
	 * d (deputy), m (med cat), w (warrior), a (apprentice), k (kit), e (elder))
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
		final String idTemp = formParams.getFirst("id");
		final String prefix = formParams.getFirst("prefix");
		final String suffix = formParams.getFirst("suffix");
		final String ageTemp = formParams.getFirst("age");
		final String genderTemp = formParams.getFirst("gender");
		final String rankTemp = formParams.getFirst("rank");
		final String appearance = formParams.getFirst("appearance");
		final String clanIdTemp = formParams.getFirst("Clan");
		if (prefix == null || prefix.isBlank() || suffix == null || suffix.isBlank() || ageTemp == null || ageTemp.isEmpty() || genderTemp == null || 
				genderTemp.isBlank() || rankTemp == null || rankTemp.isBlank() || appearance == null || appearance.isBlank() || clanIdTemp == null || 
				clanIdTemp.isBlank()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("L'un des paramètres obligatoires n'est pas fourni.").build();
		}
		int id;
		int age;
		int genderNb;
		GenderEnum gender;
		int clanId;
		Clan clan;
		RankEnum clanRank;
		try {
			id = Integer.parseInt(idTemp);
		} catch (final NumberFormatException e){
			return Response.status(Response.Status.BAD_REQUEST).entity("L'id du chat de Clan fourni n'est pas valide.").build();
		}
		try {
			age = Integer.parseInt(ageTemp);
		} catch (final NumberFormatException e){
			return Response.status(Response.Status.BAD_REQUEST).entity("L'age fourni n'est pas valide.").build();
		}
		try {
			genderNb = Integer.parseInt(genderTemp);
			if(genderNb == 0) {
				gender = GenderEnum.FEMALE;
			} else if (genderNb == 1){
				gender = GenderEnum.MALE;
			} else return Response.status(Response.Status.EXPECTATION_FAILED).entity("Gender incorrectly entered.").build();
		} catch (final NumberFormatException e){
			return Response.status(Response.Status.BAD_REQUEST).entity("L'id de Clan fourni n'est pas valide.").build();
		}
		try {
			clanId = Integer.parseInt(ageTemp);
			try {
				clan = DaoFactory.getInstance().getClanDao().getClanById(clanId);
			} catch (Exception e) {
				return Response.status(Response.Status.EXPECTATION_FAILED).entity("Clan id incorrectly entered.").build();
			}
		} catch (final NumberFormatException e){
			return Response.status(Response.Status.BAD_REQUEST).entity("L'id de Clan fourni n'est pas valide.").build();
		}
		switch(formParams.getFirst("rank")) {
			case "l":
				clanRank = RankEnum.LEADER;
				break;
			case "d":
				clanRank = RankEnum.DEPUTY;
				break;
			case "m":
				clanRank = RankEnum.MEDICINE_CAT;
				break;
			case "w":
				clanRank = RankEnum.WARRIOR;
				break;
			case "a":
				clanRank = RankEnum.APPRENTICE;
				break;
			case "k":
				clanRank = RankEnum.KIT;
				break;
			case "e":
				clanRank = RankEnum.ELDER;
				break;
			default:
				clanRank = RankEnum.WARRIOR;
				break;
		}
		ClanCat cc = null;
		try {
			cc = DaoFactory.getInstance().getClanCatDao().getClanCatById(id);
			if (cc == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Aucun chat de Clan trouvé avec l'identifiant ".concat(idTemp)).build();
			}
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		try {
			cc.setPrefix(prefix);
			cc.setSuffix(suffix);
			cc.setAge(age);
			cc.setGender(gender);
			cc.setAppearance(appearance);
			cc.setClan(clan);
			cc.setClanRank(clanRank);
			DaoFactory.getInstance().getClanCatDao().updateClanCat(cc);
			final GenericEntity<ClanCat> json = new GenericEntity<>(cc) {};
			return Response.ok().entity(json).build();
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @DELETE deletes a specific Clan cat
	 * @param idClancat
	 * @return
	 * @throws Exception 
	 */
	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteClanCat(@PathParam("id") int idClancat) throws Exception {
		final IClanCatDao dao = DaoFactory.getInstance().getClanCatDao();
		try {
			final ClanCat cc = dao.getClanCatById(idClancat);
			if (cc == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Aucun chat de Clan avec l'id [".concat(Integer.toString(idClancat)).concat("] n'a été trouvé.")).build();
			}
			dao.deleteClanCat(idClancat);
			return Response.ok().entity("Supprimé").build();
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
			final List<ClanCat> leaders = DaoFactory.getInstance().getClanCatDao().getAllLeaders();
			final GenericEntity<List<ClanCat>> json = new GenericEntity<>(leaders) {};
			return Response.ok().entity(json).build();
		} catch (final Exception e) {
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
			final List<ClanCat> leaders = DaoFactory.getInstance().getClanCatDao().getAllDeputies();
			final GenericEntity<List<ClanCat>> json = new GenericEntity<>(leaders) {};
			return Response.ok().entity(json).build();
		} catch (final Exception e) {
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
			final List<ClanCat> leaders = DaoFactory.getInstance().getClanCatDao().getAllMedCats();
			final GenericEntity<List<ClanCat>> json = new GenericEntity<>(leaders) {};
			return Response.ok().entity(json).build();
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
}