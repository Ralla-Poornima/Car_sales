/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import CarSales.Customer;
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
import CarSales.DiscountCode;
import converter.CustomerConverter;
import javax.ejb.Stateless;

/**
 *
 * @author haris
 */

@Stateless
public class CustomerResource {
    @javax.ejb.EJB
    private DiscountCodeResourceSub discountCodeResourceSub;
    @Context
    protected UriInfo uriInfo;
    protected EntityManager em;
    protected Integer id;
  
    /** Creates a new instance of CustomerResource */
    public CustomerResource() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * Get method for retrieving an instance of Customer identified by id in XML format.
     *
     * @param id identifier for the entity
     * @return an instance of CustomerConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public CustomerConverter get(@QueryParam("expandLevel")
                                 @DefaultValue("1")
    int expandLevel) {
        return new CustomerConverter(getEntity(), uriInfo.getAbsolutePath(), expandLevel);
    }

    /**
     * Put method for updating an instance of Customer identified by id using XML as the input format.
     *
     * @param id identifier for the entity
     * @param data an CustomerConverter entity that is deserialized from a XML stream
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void put(CustomerConverter data) {
        updateEntity(getEntity(), data.resolveEntity(em));
    }

    /**
     * Delete method for deleting an instance of Customer identified by id.
     *
     * @param id identifier for the entity
     */
    @DELETE
    public void delete() {
        deleteEntity(getEntity());
    }

    /**
     * Returns an instance of Customer identified by id.
     *
     * @param id identifier for the entity
     * @return an instance of Customer
     */
    protected Customer getEntity() {
        try {
            return (Customer) em.createQuery("SELECT e FROM Customer e where e.customerId = :customerId").setParameter("customerId", id).getSingleResult();
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
    private Customer updateEntity(Customer entity, Customer newEntity) {
        DiscountCode discountCode = entity.getDiscountCode();
        DiscountCode discountCodeNew = newEntity.getDiscountCode();
        entity = em.merge(newEntity);
        if (discountCode != null && !discountCode.equals(discountCodeNew)) {
            discountCode.getCustomerCollection().remove(entity);
        }
        if (discountCodeNew != null && !discountCodeNew.equals(discountCode)) {
            discountCodeNew.getCustomerCollection().add(entity);
        }
        return entity;
    }

    /**
     * Deletes the entity.
     *
     * @param entity the entity to deletle
     */
    private void deleteEntity(Customer entity) {
        DiscountCode discountCode = entity.getDiscountCode();
        if (discountCode != null) {
            discountCode.getCustomerCollection().remove(entity);
        }
        em.remove(entity);
    }

    /**
     * Returns a dynamic instance of DiscountCodeResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of DiscountCodeResource
     */
    @Path("discountCode/")
    public DiscountCodeResource getDiscountCodeResource() {
        discountCodeResourceSub.setParent(getEntity());
        return discountCodeResourceSub;
    }

    @Stateless
    public static class DiscountCodeResourceSub extends DiscountCodeResource {

        private Customer parent;

        public void setParent(Customer parent) {
            this.parent = parent;
        }

        @Override
        protected DiscountCode getEntity() {
            DiscountCode entity = parent.getDiscountCode();
            if (entity == null) {
                throw new WebApplicationException(new Throwable("Resource for " + uriInfo.getAbsolutePath() + " does not exist."), 404);
            }
            return entity;
        }
    }
}
