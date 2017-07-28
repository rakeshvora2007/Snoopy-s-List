package com.snoopyslist.DBservice;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by rui on 4/25/17.
 */
public class ShelterServiceTest {
    @PersistenceContext
    private EntityManager em;

//    @Test
//    public void listAllShelters() throws Exception {
//       List<Shelter> results =  em.createNamedQuery("Shelter.findAll", Shelter.class).getResultList();
//        for(Shelter s : results)
//            System.out.println(s);
//    }

//    @Test
//    public void getShelterById() throws Exception {
//
//    }
//
//    @Test
//    public void createShelter() throws Exception {
//
//    }
//
//    @Test
//    public void updateShelterNamebyId() throws Exception {
//
//    }
//
//    @Test
//    public void deleteShelterById() throws Exception {
//
//    }

}