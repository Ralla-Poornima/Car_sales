/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import CarSales.DiscountCode;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.WebApplicationException;
import javax.persistence.NoResultException;
import javax.persistence.EntityManager;
import CarSales.Customer;
import java.util.Collection;
import converter.DiscountCodeConverter;
import javax.ejb.Stateless;

/**
 *
 * @author haris
 */

@Stateless
public class DiscountCodeResource {
    @javax.ejb.EJB
    private CustomerCollectionResourceSub customerCollectionResourceSub;
    @Context
    protected UriInfo uriInfo;
    protected EntityManager em;
    protected String id;
  
    /** Creates a new instance of DiscountCodeResource */
    public DiscountCodeResource() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * Get method for retrieving an instance of DiscountCode identified by id in XML format.
     *
     * @param id identifier for the entity
     * @return an instance of DiscountCodeConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public DiscountCodeConverter get(@QueryParam("expandLevel")
                                     @DefaultValue("1")
    int expandLevel) {
        return new DiscountCodeConverter(getEntity(), uriInfo.getAbsolutePath(), expandLevel);
    }

    /**
     * Put method for updating an instance of DiscountCode identified by id using XML as the input format.
     *
     * @param id identifier for the entity
     * @param data an DiscountCodeConverter entity that is deserialized from a XML stream
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void put(DiscountCodeConverter data) {
        updateEntity(getEntity(), data.resolveEntity(em));
    }

    /**
     * Delete method for deleting an instance of DiscountCode identified by id.
     *
     * @param id identifier for the entity
     */
    @DELETE
    public void delete() {
        deleteEntity(getEntity());
    }

    /**
     * Returns an instance of DiscountCode identified by id.
     *
     * @param id identifier for the entity
     * @return an instance of DiscountCode
     */
    protected DiscountCode getEntity() {
        try {
            return (DiscountCode) em.createQuery("SELECT e FROM DiscountCode e where e.discountCode = :discountCode").setParameter("discountCode", id.charAt(0)).getSingleResult();
        } catch (NoResultException ex) {
            throw new WebApplicationException(new Throwable("Resource for " + uriInfo.getAbsolutePath() + " does not exist."), 404);
        }
    }

    /**
     * Updates entity using data from newEntity.
     *
     * @param entity the entity to update
     * @param newEntity the entity containing the new data
     * @return the updated entity
     */
    private DiscountCode updateEntity(DiscountCode entity, DiscountCode newEntity) {
        Collection<Customer> customerCollection = entity.getCustomerCollection();
        Collection<Customer> customerCollectionNew = newEntity.getCustomerCollection();
        entity = em.merge(newEntity);
        for (Customer value : customerCollection) {
            if (!customerCollectionNew.contains(value)) {
                throw new WebApplicationException(new Throwable("Cannot remove items from customerCollection"));
            }
        }
        for (Customer value : customerCollectionNew) {
            if (!customerCollection.contains(value)) {
                DiscountCode oldEntity = value.getDiscountCode();
                value.setDiscountCode(entity);
                if (oldEntity != null && !oldEntity.equals(entity)) {
                    oldEntity.getCustomerCollection().remove(value);
                }
            }
        }
        return entity;
    }

    /**
     * Deletes the entity.
     *
     * @param entity the entity to deletle
     */
    private void deleteEntity(DiscountCode entity) {
        if (!entity.getCustomerCollection().isEmpty()) {
            throw new WebApplicationException(new Throwable("Cannot delete entity because customerCollection is not empty."));
        }
        em.remove(entity);
    }

    /**
     * Returns a dynamic instance of CustomersResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of CustomersResource
     */
    @Path("customerCollection/")
    public CustomersResource getCustomerCollectionResource() {
        customerCollectionResourceSub.setParent(getEntity());
        return customerCollectionResourceSub;
    }

    @Stateless
    public static class CustomerCollectionResourceSub extends CustomersResource {

        private DiscountCode parent;

        public void setParent(DiscountCode parent) {
            this.parent = parent;
        }

        @Override
        protected Collection<Customer> getEntities(int start, int max, String query) {
            Collection<Customer> result = new java.util.ArrayList<Customer>();
            int index = 0;
            for (Customer e : parent.getCustomerCollection()) {
                if (index >= start && (index - start) < max) {
                    result.add(e);
                }
                index++;
            }
            return result;
        }
    }
}
