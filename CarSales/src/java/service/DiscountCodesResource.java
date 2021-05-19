/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import CarSales.DiscountCode;
import java.util.Collection;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.persistence.EntityManager;
import CarSales.Customer;
import converter.DiscountCodesConverter;
import converter.DiscountCodeConverter;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateless;

/**
 *
 * @author haris
 */

@Path("/discountCodes/")
@Stateless
public class DiscountCodesResource {
    @javax.ejb.EJB
    private DiscountCodeResource discountCodeResource;
    @Context
    protected UriInfo uriInfo;
    @PersistenceContext(unitName = "CarSalesPU")
    protected EntityManager em;
  
    /** Creates a new instance of DiscountCodesResource */
    public DiscountCodesResource() {
    }

    /**
     * Get method for retrieving a collection of DiscountCode instance in XML format.
     *
     * @return an instance of DiscountCodesConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public DiscountCodesConverter get(@QueryParam("start")
                                      @DefaultValue("0")
    int start, @QueryParam("max")
               @DefaultValue("10")
    int max, @QueryParam("expandLevel")
             @DefaultValue("1")
    int expandLevel, @QueryParam("query")
                     @DefaultValue("SELECT e FROM DiscountCode e")
    String query) {
        return new DiscountCodesConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
    }

    /**
     * Post method for creating an instance of DiscountCode using XML as the input format.
     *
     * @param data an DiscountCodeConverter entity that is deserialized from an XML stream
     * @return an instance of DiscountCodeConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(DiscountCodeConverter data) {
        DiscountCode entity = data.resolveEntity(em);
        createEntity(data.resolveEntity(em));
        return Response.created(uriInfo.getAbsolutePath().resolve(entity.getDiscountCode() + "/")).build();
    }

    /**
     * Returns a dynamic instance of DiscountCodeResource used for entity navigation.
     *
     * @return an instance of DiscountCodeResource
     */
    @Path("{discountCode}/")
    public DiscountCodeResource getDiscountCodeResource(@PathParam("discountCode")
    String id) {
        discountCodeResource.setId(id);
        discountCodeResource.setEm(em);
        return discountCodeResource;
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of DiscountCode instances
     */
    protected Collection<DiscountCode> getEntities(int start, int max, String query) {
        return em.createQuery(query).setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(DiscountCode entity) {
        em.persist(entity);
        for (Customer value : entity.getCustomerCollection()) {
            DiscountCode oldEntity = value.getDiscountCode();
            value.setDiscountCode(entity);
            if (oldEntity != null) {
                oldEntity.getCustomerCollection().remove(value);
            }
        }
    }
}
