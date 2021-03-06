/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package converter;

import CarSales.Customer;
import java.net.URI;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.ws.rs.core.UriBuilder;
import javax.persistence.EntityManager;
import CarSales.DiscountCode;

/**
 *
 * @author haris
 */

@XmlRootElement(name = "customer")
public class CustomerConverter {
    private Customer entity;
    private URI uri;
    private int expandLevel;
  
    /** Creates a new instance of CustomerConverter */
    public CustomerConverter() {
        entity = new Customer();
    }

    /**
     * Creates a new instance of CustomerConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded@param isUriExtendable indicates whether the uri can be extended
     */
    public CustomerConverter(Customer entity, URI uri, int expandLevel, boolean isUriExtendable) {
        this.entity = entity;
        this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getCustomerId() + "/").build() : uri;
        this.expandLevel = expandLevel;
        getDiscountCode();
    }

    /**
     * Creates a new instance of CustomerConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded
     */
    public CustomerConverter(Customer entity, URI uri, int expandLevel) {
        this(entity, uri, expandLevel, false);
    }

    /**
     * Getter for customerId.
     *
     * @return value for customerId
     */
    @XmlElement
    public Integer getCustomerId() {
        return (expandLevel > 0) ? entity.getCustomerId() : null;
    }

    /**
     * Setter for customerId.
     *
     * @param value the value to set
     */
    public void setCustomerId(Integer value) {
        entity.setCustomerId(value);
    }

    /**
     * Getter for zip.
     *
     * @return value for zip
     */
    @XmlElement
    public String getZip() {
        return (expandLevel > 0) ? entity.getZip() : null;
    }

    /**
     * Setter for zip.
     *
     * @param value the value to set
     */
    public void setZip(String value) {
        entity.setZip(value);
    }

    /**
     * Getter for name.
     *
     * @return value for name
     */
    @XmlElement
    public String getName() {
        return (expandLevel > 0) ? entity.getName() : null;
    }

    /**
     * Setter for name.
     *
     * @param value the value to set
     */
    public void setName(String value) {
        entity.setName(value);
    }

    /**
     * Getter for addressline1.
     *
     * @return value for addressline1
     */
    @XmlElement
    public String getAddressline1() {
        return (expandLevel > 0) ? entity.getAddressline1() : null;
    }

    /**
     * Setter for addressline1.
     *
     * @param value the value to set
     */
    public void setAddressline1(String value) {
        entity.setAddressline1(value);
    }

    /**
     * Getter for addressline2.
     *
     * @return value for addressline2
     */
    @XmlElement
    public String getAddressline2() {
        return (expandLevel > 0) ? entity.getAddressline2() : null;
    }

    /**
     * Setter for addressline2.
     *
     * @param value the value to set
     */
    public void setAddressline2(String value) {
        entity.setAddressline2(value);
    }

    /**
     * Getter for city.
     *
     * @return value for city
     */
    @XmlElement
    public String getCity() {
        return (expandLevel > 0) ? entity.getCity() : null;
    }

    /**
     * Setter for city.
     *
     * @param value the value to set
     */
    public void setCity(String value) {
        entity.setCity(value);
    }

    /**
     * Getter for state.
     *
     * @return value for state
     */
    @XmlElement
    public String getState() {
        return (expandLevel > 0) ? entity.getState() : null;
    }

    /**
     * Setter for state.
     *
     * @param value the value to set
     */
    public void setState(String value) {
        entity.setState(value);
    }

    /**
     * Getter for phone.
     *
     * @return value for phone
     */
    @XmlElement
    public String getPhone() {
        return (expandLevel > 0) ? entity.getPhone() : null;
    }

    /**
     * Setter for phone.
     *
     * @param value the value to set
     */
    public void setPhone(String value) {
        entity.setPhone(value);
    }

    /**
     * Getter for fax.
     *
     * @return value for fax
     */
    @XmlElement
    public String getFax() {
        return (expandLevel > 0) ? entity.getFax() : null;
    }

    /**
     * Setter for fax.
     *
     * @param value the value to set
     */
    public void setFax(String value) {
        entity.setFax(value);
    }

    /**
     * Getter for email.
     *
     * @return value for email
     */
    @XmlElement
    public String getEmail() {
        return (expandLevel > 0) ? entity.getEmail() : null;
    }

    /**
     * Setter for email.
     *
     * @param value the value to set
     */
    public void setEmail(String value) {
        entity.setEmail(value);
    }

    /**
     * Getter for creditLimit.
     *
     * @return value for creditLimit
     */
    @XmlElement
    public Integer getCreditLimit() {
        return (expandLevel > 0) ? entity.getCreditLimit() : null;
    }

    /**
     * Setter for creditLimit.
     *
     * @param value the value to set
     */
    public void setCreditLimit(Integer value) {
        entity.setCreditLimit(value);
    }

    /**
     * Getter for discountCode.
     *
     * @return value for discountCode
     */
    @XmlElement
    public DiscountCodeConverter getDiscountCode() {
        if (expandLevel > 0) {
            if (entity.getDiscountCode() != null) {
                return new DiscountCodeConverter(entity.getDiscountCode(), uri.resolve("discountCode/"), expandLevel - 1, false);
            }
        }
        return null;
    }

    /**
     * Setter for discountCode.
     *
     * @param value the value to set
     */
    public void setDiscountCode(DiscountCodeConverter value) {
        entity.setDiscountCode((value != null) ? value.getEntity() : null);
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
     * Returns the Customer entity.
     *
     * @return an entity
     */
    @XmlTransient
    public Customer getEntity() {
        if (entity.getCustomerId() == null) {
            CustomerConverter converter = UriResolver.getInstance().resolve(CustomerConverter.class, uri);
            if (converter != null) {
                entity = converter.getEntity();
            }
        }
        return entity;
    }

    /**
     * Returns the resolved Customer entity.
     *
     * @return an resolved entity
     */
    public Customer resolveEntity(EntityManager em) {
        DiscountCode discountCode = entity.getDiscountCode();
        if (discountCode != null) {
            entity.setDiscountCode(em.getReference(DiscountCode.class, discountCode.getDiscountCode()));
        }
        return entity;
    }
}
