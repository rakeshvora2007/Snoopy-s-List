package com.snoopyslist.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Simple object model for pet detail page
 *      Mandatory:
 *          Thumbnail
 *          ContractInfo
 *          Description
 */
@XmlRootElement
public class PetDetail {
    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) { this.thumbnail = thumbnail;  }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ContractInfo getContractInfo() {
        return contractInfo;
    }

    public void setContractInfo(ContractInfo contractInfo) {
        this.contractInfo = contractInfo;
    }

    @XmlElement(required=true)
    private Thumbnail thumbnail;
    private String description;
    @XmlElement(required=true)
    private ContractInfo contractInfo;

    public PetDetail () {}

    public PetDetail (Thumbnail thumbnail, ContractInfo info, String desc) {
        this.thumbnail = thumbnail;
        this.contractInfo = info;
        this.description = desc;
    }
}
