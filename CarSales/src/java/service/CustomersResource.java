/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import CarSales.Customer;
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
import CarSales.DiscountCode;
import converter.CustomersConverter;
import converter.CustomerConverter;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateless;

/**
 *
 * @author haris
 */

@Path("/customers/")
@Stateless
public class CustomersResource {
    @javax.ejb.EJB
    private CustomerResource customerResource;
    @Context
    protected UriInfo uriInfo;
    @PersistenceContext(unitName = "CarSalesPU")
    protected EntityManager em;
  
    /** Creates a new instance of CustomersResource */
    public CustomersResource() {
    }

    /**
     * Get method for retrieving a collection of Customer instance in XML format.
     *
     * @return an instance of CustomersConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public CustomersConverter get(@QueryParam("start")
                                  @DefaultValue("0")
    int start, @QueryParam("max")
               @DefaultValue("10")
    int max, @QueryParam("expandLevel")
             @DefaultValue("1")
    int expandLevel, @QueryParam("query")
                     @DefaultValue("SELECT e FROM Customer e")
    String query) {
        return new CustomersConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
    }

    /**
     * Post method for creating an instance of Customer using XML as the input format.
     *
     * @param data an CustomerConverter entity that is deserialized from an XML stream
     * @return an instance of CustomerConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(CustomerConverter data) {
        Customer entity = data.resolveEntity(em);
        createEntity(data.resolveEntity(em));
        return Response.created(uriInfo.getAbsolutePath().resolve(entity.getCustomerId() + "/")).build();
    }

    /**
     * Returns a dynamic instance of CustomerResource used for entity navigation.
     *
     * @return an instance of CustomerResource
     */
    @Path("{customerId}/")
    public CustomerResource getCustomerResource(@PathParam("customerId")
    Integer id) {
        customerResource.setId(id);
        customerResource.setEm(em);
        return customerResource;
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Customer instances
     */
    protected Collection<Customer> getEntities(int start, int max, String query) {
        return em.createQuery(query).setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Customer entity) {
        em.persist(entity);
        DiscountCode discountCode = entity.getDiscountCode();
        if (discountCode != null) {
            discountCode.getCustomerCollection().add(entity);
        }
    }
}
