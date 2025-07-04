package services;

import java.util.List;

import dao.bddimpl.ClanDaoBdd;
import dao.bddimpl.DaoFactory;
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

@Path("/clan")
public class ClanService {
	
	/**
	 * @GET all clans in database
	 * @return
	 * @throws DaoException 
	 */
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClans() throws DaoException {
		try {
			DaoFactory.getInstance();
			List<Clan> clans = null;
			clans = DaoFactory.getClanDao().readAllObject();
			if (clans == null || clans.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND).entity("Clans not found").build();
			}
			final GenericEntity<List<Clan>> json = new GenericEntity<List<Clan>>(clans) {};
			return Response.ok().entity(json).build();
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @GET specific Clan by its id
	 * @param idClan
	 * @return
	 * @throws DaoException
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClanById(@PathParam("id") int idClan) throws DaoException {
		Clan clan = null;
		try {
			DaoFactory.getInstance();
			clan = DaoFactory.getClanDao().readObject(idClan);
			if (clan == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Clan not found").build();
			} else {
				final GenericEntity<Clan> json = new GenericEntity<Clan>(clan) {};				
				return Response.status(Response.Status.OK).entity(json).build();
			}
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @POST creates new Clan
	 * @param name AND description AND territory type AND preys
	 * @return
	 */
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addClan(final MultivaluedMap<String, String> formParams) {
		if (formParams.get("name") == null || formParams.get("description") == null || formParams.get("territory_type") == null || formParams.get("preys") == null) {
			return Response.status(Response.Status.EXPECTATION_FAILED).entity("Parameters missing").build();
		}
		try {
			final Clan clan = new Clan();
			final String name = formParams.getFirst("name");
			final String description = formParams.getFirst("description");
			final String territory_type = formParams.getFirst("territory_type");
			final String preys = formParams.getFirst("preys");
			clan.setName(name);
			clan.setDescription(description);
			clan.setTerrType(territory_type);
			clan.setPreys(preys);
			ClanDaoBdd clanDaoBdd = new ClanDaoBdd();
			clanDaoBdd.createObject(clan);
			return Response.status(Response.Status.CREATED).entity(clan).build();
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Clan creation failed").build();
		}
	}
	
	/**
	 * @PUT updates one or more attributes of a specific clan
	 * @param idClan
	 * @param name AND/OR description AND/OR territory type AND/OR preys
	 * @return
	 */
	@PUT
	@Consumes("application/x-www-form-urlencoded\")\r\n")
	@Path("/update/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateClan(@PathParam("id") int idClan, MultivaluedMap<String, String> formParams) {
		if (formParams.get("name") == null && formParams.get("description") == null && formParams.get("territory_type") == null && formParams.get("preys") == null) {
			return Response.status(Response.Status.EXPECTATION_FAILED).entity("All fields empty").build();
		}
		Clan clan = null;
		try {
			DaoFactory.getInstance();
			clan = DaoFactory.getClanDao().readObject(idClan);
			if (formParams.getFirst("name") != null) {
				final String name = formParams.getFirst("name");
				clan.setName(name);	
			}
			if (formParams.getFirst("description") != null) {
				final String description = formParams.getFirst("description");
				clan.setDescription(description);	
			}
			if (formParams.getFirst("territory_name") != null) {
				final String territory_name = formParams.getFirst("territory_name");
				clan.setTerrType(territory_name);
			}
			if (formParams.getFirst("preys") != null) {
				final String preys = formParams.getFirst("preys");
				clan.setPreys(preys);	
			}
			return Response.status(Response.Status.OK).entity(clan).build();
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Clan update failed.").build();
		}
	}
	
	/**
	 * @DELETE deletes a specific clan
	 * @param idClan
	 * @return
	 * @throws DaoException
	 */
	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteClan(@PathParam("id") int idClan) throws DaoException {
		Clan clan = null;
		try {
			DaoFactory.getInstance();
			clan = DaoFactory.getClanDao().readObject(idClan);
			if (clan == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Clan not found").build();	
			} else {
				ClanDaoBdd clanDaoBdd = new ClanDaoBdd();
				clanDaoBdd.deleteObject(clan);
				return Response.status(Response.Status.OK).entity("Clan deletion successful.").build();
			}
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @GET leader for a specific Clan
	 * @param idClan
	 * @return
	 * @throws DaoException
	 */
	@GET
	@Path("/{id}/leader")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClanLeader(@PathParam("id") int idClan) throws DaoException {
		try {
			Clan clan = null;
			ClanCat leader = null;
			DaoFactory.getInstance();
			clan = DaoFactory.getClanDao().readObject(idClan);
			if (clan == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Clan not found").build();
			}
			List<ClanCat> members = clan.getMembers();
			for (int i = 0; i<=members.size(); i++){
				if (members.get(i).clanRank== RankEnum.LEADER){
					leader = members.get(i);
				}
			} 
			final GenericEntity<ClanCat> json = new GenericEntity<ClanCat>(leader) {};
			return Response.ok().entity(json).build();
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @GET deputy for a specific Clan
	 * @param idClan
	 * @return
	 * @throws DaoException
	 */
	@GET
	@Path("/{id}/deputy")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClanDeputy(@PathParam("id") int idClan) throws DaoException {
		try {
			Clan clan = null;
			ClanCat deputy = null;
			DaoFactory.getInstance();
			clan = DaoFactory.getClanDao().readObject(idClan);
			if (clan == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Clan not found").build();
			}
			List<ClanCat> members = clan.getMembers();
			for (int i = 0; i<=members.size(); i++){
				if (members.get(i).clanRank== RankEnum.DEPUTY){
					deputy = members.get(i);
				}
			} 
			final GenericEntity<ClanCat> json = new GenericEntity<ClanCat>(deputy) {};
			return Response.ok().entity(json).build();
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @GET medicine cat(s) for a specific Clan
	 * @param idClan
	 * @return
	 * @throws DaoException
	 */
	@GET
	@Path("/{id}/medcat")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClanMedicineCat(@PathParam("id") int idClan) throws DaoException {
		try {
			Clan clan = null;
			List<ClanCat> medCats = null;
			DaoFactory.getInstance();
			clan = DaoFactory.getClanDao().readObject(idClan);
			if (clan == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Clan not found").build();
			}
			List<ClanCat> members = clan.getMembers();
			for (int i = 0; i<=members.size(); i++){
				if (members.get(i).clanRank== RankEnum.MEDICINE_CAT){
					medCats.add(members.get(i));
				}
			}
			if (medCats.size() == 1) {
				ClanCat medCat = medCats.get(1);
				final GenericEntity<ClanCat> json = new GenericEntity<ClanCat>(medCat) {};
				return Response.ok().entity(json).build();
			} else {
				final GenericEntity<List<ClanCat>> json = new GenericEntity<List<ClanCat>>(medCats) {};
				return Response.ok().entity(json).build();
			}
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @GET warriors for a specific Clan
	 * @param idClan
	 * @return
	 * @throws DaoException
	 */
	@GET
	@Path("/{id}/warriors")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClanWarriors(@PathParam("id") int idClan) throws DaoException {
		try {
			Clan clan = null;
			List<ClanCat> warriors = null;
			DaoFactory.getInstance();
			clan = DaoFactory.getClanDao().readObject(idClan);
			if (clan == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Clan not found").build();
			}
			List<ClanCat> members = clan.getMembers();
			for (int i = 0; i<=members.size(); i++){
				if (members.get(i).clanRank== RankEnum.WARRIOR){
					warriors.add(members.get(i));
				}
			} 
			final GenericEntity<List<ClanCat>> json = new GenericEntity<List<ClanCat>>(warriors) {};
			return Response.ok().entity(json).build();
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @GET apprentices for a specific Clan
	 * @param idClan
	 * @return
	 * @throws DaoException
	 */
	@GET
	@Path("/{id}/apprentices")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClanApprentices(@PathParam("id") int idClan) throws DaoException {
		try {
			Clan clan = null;
			List<ClanCat> apprentices = null;
			DaoFactory.getInstance();
			clan = DaoFactory.getClanDao().readObject(idClan);
			if (clan == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Clan not found").build();
			}
			List<ClanCat> members = clan.getMembers();
			for (int i = 0; i<=members.size(); i++){
				if (members.get(i).clanRank== RankEnum.APPRENTICE){
					apprentices.add(members.get(i));
				}
			} 
			final GenericEntity<List<ClanCat>> json = new GenericEntity<List<ClanCat>>(apprentices) {};
			return Response.ok().entity(json).build();
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	/**
	 * @GET kits for a specific Clan
	 * @param idClan
	 * @return
	 * @throws DaoException
	 */
	@GET
	@Path("/{id}/kits")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClanKits(@PathParam("id") int idClan) throws DaoException {
		try {
			Clan clan = null;
			List<ClanCat> kits = null;
			DaoFactory.getInstance();
			clan = DaoFactory.getClanDao().readObject(idClan);
			if (clan == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Clan not found").build();
			}
			List<ClanCat> members = clan.getMembers();
			for (int i = 0; i<=members.size(); i++){
				if (members.get(i).clanRank== RankEnum.APPRENTICE){
					kits.add(members.get(i));
				}
			} 
			final GenericEntity<List<ClanCat>> json = new GenericEntity<List<ClanCat>>(kits) {};
			return Response.ok().entity(json).build();
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @GET elders for a specific Clan
	 * @param idClan
	 * @return
	 * @throws DaoException
	 */
	@GET
	@Path("/{id}/elders")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClanElders(@PathParam("id") int idClan) throws DaoException {
		try {
			Clan clan = null;
			List<ClanCat> elders = null;
			DaoFactory.getInstance();
			clan = DaoFactory.getClanDao().readObject(idClan);
			if (clan == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Clan not found").build();
			}
			List<ClanCat> members = clan.getMembers();
			for (int i = 0; i<=members.size(); i++){
				if (members.get(i).clanRank== RankEnum.APPRENTICE){
					elders.add(members.get(i));
				}
			} 
			final GenericEntity<List<ClanCat>> json = new GenericEntity<List<ClanCat>>(elders) {};
			return Response.ok().entity(json).build();
		} catch (final Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
}
