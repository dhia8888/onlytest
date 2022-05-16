package tn.esprit.spring.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Entreprise;
import org.junit.Assert;
import tn.esprit.spring.entities.Departement;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseTest {
        private List<Departement> departementList;
        @Autowired
        private IEntrepriseService serviceEntreprise;
        @Autowired
        private IDepartementService serviceDepartement;

        private static final Logger log = LogManager.getLogger(EntrepriseTest.class);
        @Test
        @Order(1)
        public void testAjouterEntreprise() {
            log.info("TESTING ADD ENTREPRISE IN PROGRESS");
            Entreprise ent0 = new Entreprise("vermeg", "banquesoftware");
            Entreprise ent1 = new Entreprise("business", "data");
            Entreprise ent2 = new Entreprise("sofrecom", "software");
            serviceEntreprise.ajouterEntreprise(ent1);
            serviceEntreprise.ajouterEntreprise(ent2);
            int id = serviceEntreprise.ajouterEntreprise(ent0);
            Assert.assertNotNull(serviceEntreprise.getEntrepriseById(id));
            log.info("Entreprise added successfully");
        }

        @Test
        @Order(2)
        public void testgetEntrepriseById() {
            log.info("TESTING GET ENTREPRISE BY ID IN PROGRESS");
            Entreprise ent = new Entreprise("SecuriteLab", "SOC");
            int id = serviceEntreprise.ajouterEntreprise(ent);
            System.out.println("get id test verification"+id);
            Entreprise e1 = serviceEntreprise.getEntrepriseById(id);
                Assert.assertEquals(ent.getId(),e1.getId());
            }

        @Test
        @Order(3)
        public void testajouterDepartement()  {
            log.info("TESTING ADD DEPARTMENT IN PROGRESS");
            Departement dep =new Departement("Embarque");
            int a=serviceEntreprise.ajouterDepartement(dep);
            assertNotNull(a);
            assertNotEquals(a,0);
            log.info("added successfully");
        }

        @Test
        @Order(4)
        public void testgetAllDepartementsByEntreprise()  {
            log.info("TESTING GET ALL DEPARTMENT BY ENTREPRISE IN PROGRESS");
            try{
            List<String> depnames=serviceEntreprise.getAllDepartementsNamesByEntreprise(58);
            assertNotNull(depnames);
            }catch (Exception e) {
                log.error("could not get list of Depratment ! " + e.getMessage());
            }
        }

        @Test
        @Order(5)
        public void testgetAllDepartements() {
                log.info("TESTING GET ALL DEPRATMENTS IN PROGRESS");
            try{
                departementList = serviceDepartement.getAllDepartements();
            if(departementList.isEmpty()) {
                log.warn("departments not found");
            }
            }catch (Exception e) {
                    log.error("Please add a departement ! " + e.getMessage());
                }
        }

        @Test
        @Order(6)
        public void testaffecterDepartementAEntreprise() {
            log.info("TESTING ADD DEPARTMENT TO ENTREPRISE IN PROGRESS");
            Entreprise ent = new Entreprise("Espritt", "Education");
            int idEntrep = serviceEntreprise.ajouterEntreprise(ent);
            Departement dep = new Departement("Web");
            int idDep = serviceEntreprise.ajouterDepartement(dep);
            serviceEntreprise.affecterDepartementAEntreprise(idDep, idEntrep);
            Assert.assertNotEquals(idDep,idEntrep);

        }
        @Test
        @Order(7)
        public void testdeleteEntrepriseById() {
            log.info("TESTING DELETE ENTREPRISE BY ID IN PROGRESS");

            Entreprise ent = new Entreprise("Soc", "DEV");
            int id = serviceEntreprise.ajouterEntreprise(ent);
            serviceEntreprise.deleteEntrepriseById(id);
        }
        @Test
        @Order(8)
        public void testdeleteDepartementById() {
            log.info("TESTING DELETE DEPARTMENT BY ID IN PROGRESS");
            Departement dep = new Departement("Info");
            int id = serviceEntreprise.ajouterDepartement(dep);
            serviceEntreprise.deleteDepartementById(id);

        }
}
