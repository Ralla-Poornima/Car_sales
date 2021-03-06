/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package converter;

import CarSales.DiscountCode;
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

@XmlRootElement(name = "discountCodes")
public class DiscountCodesConverter {
    private Collection<DiscountCode> entities;
    private Collection<DiscountCodeConverter> items;
    private URI uri;
    private int expandLevel;
  
    /** Creates a new instance of DiscountCodesConverter */
    public DiscountCodesConverter() {
    }

    /**
     * Creates a new instance of DiscountCodesConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded
     */
    public DiscountCodesConverter(Collection<DiscountCode> entities, URI uri, int expandLevel) {
        this.entities = entities;
        this.uri = uri;
        this.expandLevel = expandLevel;
        getDiscountCode();
    }

    /**
     * Returns a collection of DiscountCodeConverter.
     *
     * @return a collection of DiscountCodeConverter
     */
    @XmlElement
    public Collection<DiscountCodeConverter> getDiscountCode() {
        if (items == null) {
            items = new ArrayList<DiscountCodeConverter>();
        }
        if (entities != null) {
            items.clear();
            for (DiscountCode entity : entities) {
                items.add(new DiscountCodeConverter(entity, uri, expandLevel, true));
            }
        }
        return items;
    }

    /**
     * Sets a collection of DiscountCodeConverter.
     *
     * @param a collection of DiscountCodeConverter to set
     */
    public void setDiscountCode(Collection<DiscountCodeConverter> items) {
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
     * Returns a collection DiscountCode entities.
     *
     * @return a collection of DiscountCode entities
     */
    @XmlTransient
    public Collection<DiscountCode> getEntities() {
        entities = new ArrayList<DiscountCode>();
        if (items != null) {
            for (DiscountCodeConverter item : items) {
                entities.add(item.getEntity());
            }
        }
        return entities;
    }
}
