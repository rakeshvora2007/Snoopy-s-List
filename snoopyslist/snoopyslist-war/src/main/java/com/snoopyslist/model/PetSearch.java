package com.snoopyslist.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Simple object of pet thumbnail result of searching
 *          Mandatory:
 *              SearchNumber (search result number) - Int
 *              List<Thumbnail>
 */
@XmlRootElement
public class PetSearch {
    public int getSearchNumber() {
        return searchNumber;
    }

    public void setSearchNumber(int searchNumber) {
        this.searchNumber = searchNumber;
    }

    public List<Thumbnail> getThumbnailList() {
        return thumbnailList;
    }

    public void setThumbnailList(List<Thumbnail> thumbnailList) {
        this.thumbnailList = thumbnailList;
    }

    // search result variables
    private int searchNumber;
    private List<Thumbnail> thumbnailList;

    public PetSearch () {}

    public PetSearch (int searchNumber, List<Thumbnail> thumbnailList) {
        this.searchNumber = searchNumber;
        this.thumbnailList = thumbnailList;
    }
}
