//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.06 at 02:19:42 AM WAT 
//


package com.ihvn.validator.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for VisitType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VisitType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="visit_id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="patient_id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="location_id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="visit_type_id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="date_started" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="date_stopped" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="creator" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="date_created" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="changed_by" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="date_changed" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="voided" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="voided_by" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="date_voided" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="patient_uuid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="datim_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="visit_uuid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VisitType", propOrder = {
    "visitId",
    "patientId",
    "locationId",
    "visitTypeId",
    "dateStarted",
    "dateStopped",
    "creator",
    "dateCreated",
    "changedBy",
    "dateChanged",
    "voided",
    "voidedBy",
    "dateVoided",
    "patientUuid",
    "datimId",
    "visitUuid"
})
public class VisitType {

    @XmlElement(name = "visit_id")
    protected int visitId;
    @XmlElement(name = "patient_id")
    protected int patientId;
    @XmlElement(name = "location_id")
    protected int locationId;
    @XmlElement(name = "visit_type_id")
    protected int visitTypeId;
    @XmlElement(name = "date_started", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateStarted;
    @XmlElement(name = "date_stopped", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateStopped;
    protected int creator;
    @XmlElement(name = "date_created", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateCreated;
    @XmlElement(name = "changed_by")
    protected int changedBy;
    @XmlElement(name = "date_changed", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateChanged;
    protected int voided;
    @XmlElement(name = "voided_by")
    protected int voidedBy;
    @XmlElement(name = "date_voided", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateVoided;
    @XmlElement(name = "patient_uuid", required = true)
    protected String patientUuid;
    @XmlElement(name = "datim_id", required = true)
    protected String datimId;
    @XmlElement(name = "visit_uuid", required = true)
    protected String visitUuid;

    /**
     * Gets the value of the visitId property.
     * 
     */
    public int getVisitId() {
        return visitId;
    }

    /**
     * Sets the value of the visitId property.
     * 
     */
    public void setVisitId(int value) {
        this.visitId = value;
    }

    /**
     * Gets the value of the patientId property.
     * 
     */
    public int getPatientId() {
        return patientId;
    }

    /**
     * Sets the value of the patientId property.
     * 
     */
    public void setPatientId(int value) {
        this.patientId = value;
    }

    /**
     * Gets the value of the locationId property.
     * 
     */
    public int getLocationId() {
        return locationId;
    }

    /**
     * Sets the value of the locationId property.
     * 
     */
    public void setLocationId(int value) {
        this.locationId = value;
    }

    /**
     * Gets the value of the visitTypeId property.
     * 
     */
    public int getVisitTypeId() {
        return visitTypeId;
    }

    /**
     * Sets the value of the visitTypeId property.
     * 
     */
    public void setVisitTypeId(int value) {
        this.visitTypeId = value;
    }

    /**
     * Gets the value of the dateStarted property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateStarted() {
        return dateStarted;
    }

    /**
     * Sets the value of the dateStarted property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateStarted(XMLGregorianCalendar value) {
        this.dateStarted = value;
    }

    /**
     * Gets the value of the dateStopped property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateStopped() {
        return dateStopped;
    }

    /**
     * Sets the value of the dateStopped property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateStopped(XMLGregorianCalendar value) {
        this.dateStopped = value;
    }

    /**
     * Gets the value of the creator property.
     * 
     */
    public int getCreator() {
        return creator;
    }

    /**
     * Sets the value of the creator property.
     * 
     */
    public void setCreator(int value) {
        this.creator = value;
    }

    /**
     * Gets the value of the dateCreated property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the value of the dateCreated property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateCreated(XMLGregorianCalendar value) {
        this.dateCreated = value;
    }

    /**
     * Gets the value of the changedBy property.
     * 
     */
    public int getChangedBy() {
        return changedBy;
    }

    /**
     * Sets the value of the changedBy property.
     * 
     */
    public void setChangedBy(int value) {
        this.changedBy = value;
    }

    /**
     * Gets the value of the dateChanged property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateChanged() {
        return dateChanged;
    }

    /**
     * Sets the value of the dateChanged property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateChanged(XMLGregorianCalendar value) {
        this.dateChanged = value;
    }

    /**
     * Gets the value of the voided property.
     * 
     */
    public int getVoided() {
        return voided;
    }

    /**
     * Sets the value of the voided property.
     * 
     */
    public void setVoided(int value) {
        this.voided = value;
    }

    /**
     * Gets the value of the voidedBy property.
     * 
     */
    public int getVoidedBy() {
        return voidedBy;
    }

    /**
     * Sets the value of the voidedBy property.
     * 
     */
    public void setVoidedBy(int value) {
        this.voidedBy = value;
    }

    /**
     * Gets the value of the dateVoided property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateVoided() {
        return dateVoided;
    }

    /**
     * Sets the value of the dateVoided property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateVoided(XMLGregorianCalendar value) {
        this.dateVoided = value;
    }

    /**
     * Gets the value of the patientUuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientUuid() {
        return patientUuid;
    }

    /**
     * Sets the value of the patientUuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientUuid(String value) {
        this.patientUuid = value;
    }

    /**
     * Gets the value of the datimId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatimId() {
        return datimId;
    }

    /**
     * Sets the value of the datimId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatimId(String value) {
        this.datimId = value;
    }

    /**
     * Gets the value of the visitUuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisitUuid() {
        return visitUuid;
    }

    /**
     * Sets the value of the visitUuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisitUuid(String value) {
        this.visitUuid = value;
    }

}
