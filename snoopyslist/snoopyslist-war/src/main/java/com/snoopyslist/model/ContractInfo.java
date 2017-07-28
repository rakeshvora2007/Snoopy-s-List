package com.snoopyslist.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Contract Info contain:
 *      Mandatory:
 *          phoneNumber - String
 *          address - String
 *          city - String
 *          zipCode - String
 *          email - String
 */
@XmlRootElement
public class ContractInfo {
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement(required=true)
    private String phoneNumber;
    @XmlElement(required=true)
    private String address;
    @XmlElement(required=true)
    private String city;
    @XmlElement(required=true)
    private String zipCode;
    @XmlElement(required=true)
    private String email;

    public ContractInfo () {}

    public ContractInfo (String phoneNumber, String address, String city, String zipCode, String email) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.email = email;
    }
}
