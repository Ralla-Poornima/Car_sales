/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package converter;

import CarSales.Customer;
import java.net.URI;
import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.ArrayList;

/**
 *
 * @author haris
 */

@XmlRootElement(name = "customers")
public class CustomersConverter {
    private Collection<Customer> entities;
    private Collection<CustomerConverter> items;
    private URI uri;
    private int expandLevel;
  
    /** Creates a new instance of CustomersConverter */
    public CustomersConverter() {
    }

    /**
     * Creates a new instance of CustomersConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded
     */
    public CustomersConverter(Collection<Customer> entities, URI uri, int expandLevel) {
        this.entities = entities;
        this.uri = uri;
        this.expandLevel = expandLevel;
        getCustomer();
    }

    /**
     * Returns a collection of CustomerConverter.
     *
     * @return a collection of CustomerConverter
     */
    @XmlElement
    public Collection<CustomerConverter> getCustomer() {
        if (items == null) {
            items = new ArrayList<CustomerConverter>();
        }
        if (entities != null) {
            items.clear();
            for (Customer entity : entities) {
                items.add(new CustomerConverter(entity, uri, expandLevel, true));
            }
        }
        return items;
    }

    /**
     * Sets a collection of CustomerConverter.
     *
     * @param a collection of CustomerConverter to set
     */
    public void setCustomer(Collection<CustomerConverter> items) {
        this.items = items;
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
     * Returns a collection Customer entities.
     *
     * @return a collection of Customer entities
     */
    @XmlTransient
    public Collection<Customer> getEntities() {
        entities = new ArrayList<Customer>();
        if (items != null) {
            for (CustomerConverter item : items) {
                entities.add(item.getEntity());
            }
        }
        return entities;
    }
}
