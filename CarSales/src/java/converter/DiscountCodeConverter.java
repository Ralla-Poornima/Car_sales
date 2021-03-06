/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package converter;

import CarSales.DiscountCode;
import java.math.BigDecimal;
import java.net.URI;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.ws.rs.core.UriBuilder;
import javax.persistence.EntityManager;
import CarSales.Customer;
import java.util.Collection;

/**
 *
 * @author haris
 */

@XmlRootElement(name = "discountCode")
public class DiscountCodeConverter {
    private DiscountCode entity;
    private URI uri;
    private int expandLevel;
  
    /** Creates a new instance of DiscountCodeConverter */
    public DiscountCodeConverter() {
        entity = new DiscountCode();
    }

    /**
     * Creates a new instance of DiscountCodeConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded@param isUriExtendable indicates whether the uri can be extended
     */
    public DiscountCodeConverter(DiscountCode entity, URI uri, int expandLevel, boolean isUriExtendable) {
        this.entity = entity;
        this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getDiscountCode() + "/").build() : uri;
        this.expandLevel = expandLevel;
        getCustomerCollection();
    }

    /**
     * Creates a new instance of DiscountCodeConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded
     */
    public DiscountCodeConverter(DiscountCode entity, URI uri, int expandLevel) {
        this(entity, uri, expandLevel, false);
    }

    /**
     * Getter for discountCode.
     *
     * @return value for discountCode
     */
    @XmlElement
    public Character getDiscountCode() {
        return (expandLevel > 0) ? entity.getDiscountCode() : null;
    }

    /**
     * Setter for discountCode.
     *
     * @param value the value to set
     */
    public void setDiscountCode(Character value) {
        entity.setDiscountCode(value);
    }

    /**
     * Getter for rate.
     *
     * @return value for rate
     */
    @XmlElement
    public BigDecimal getRate() {
        return (expandLevel > 0) ? entity.getRate() : null;
    }

    /**
     * Setter for rate.
     *
     * @param value the value to set
     */
    public void setRate(BigDecimal value) {
        entity.setRate(value);
    }

    /**
     * Getter for customerCollection.
     *
     * @return value for customerCollection
     */
    @XmlElement
    public CustomersConverter getCustomerCollection() {
        if (expandLevel > 0) {
            if (entity.getCustomerCollection() != null) {
                return new CustomersConverter(entity.getCustomerCollection(), uri.resolve("customerCollection/"), expandLevel - 1);
            }
        }
        return null;
    }

    /**
     * Setter for customerCollection.
     *
     * @param value the value to set
     */
    public void setCustomerCollection(CustomersConverter value) {
        entity.setCustomerCollection((value != null) ? value.getEntities() : null);
    }

    /**
     * Returns the URI associated with this converter.
     *
     * @return the uri
     */
    @XmlAttribute
    public URI getUri() {
        return uri;
    }

    /**
     * Sets the URI for this reference converter.
     *
     */
    public void setUri(URI uri) {
        this.uri = uri;
    }

    /**
     * Returns the DiscountCode entity.
     *
     * @return an entity
     */
    @XmlTransient
    public DiscountCode getEntity() {
        if (entity.getDiscountCode() == null) {
            DiscountCodeConverter converter = UriResolver.getInstance().resolve(DiscountCodeConverter.class, uri);
            if (converter != null) {
                entity = converter.getEntity();
            }
        }
        return entity;
    }

    /**
     * Returns the resolved DiscountCode entity.
     *
     * @return an resolved entity
     */
    public DiscountCode resolveEntity(EntityManager em) {
        Collection<Customer> customerCollection = entity.getCustomerCollection();
        Collection<Customer> newcustomerCollection = new java.util.ArrayList<Customer>();
        if (customerCollection != null) {
            for (Customer item : customerCollection) {
                newcustomerCollection.add(em.getReference(Customer.class, item.getCustomerId()));
            }
        }
        entity.setCustomerCollection(newcustomerCollection);
        return entity;
    }
}
