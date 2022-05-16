package tn.esprit.spring.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.spring.entities.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimesheetServiceImplTest {
    @Autowired
    private ITimesheetService timesheetService;
    @Autowired
    private IEmployeService sEmploye;
    @Autowired
    private IEntrepriseService sEntreprise;
    @Autowired
    private EmployeRepository employeRepository;
    @Autowired
    private TimesheetRepository timesheetRepository;

    private static final Logger l = LogManager.getLogger(TimesheetServiceImplTest.class);

    @Test
    public void testAjouterMission() {
        l.info("test methode AjouterMission");
        Mission mission = new Mission("FBI", "TUNIS");
        int id = timesheetService.ajouterMission((mission));
        assertNotNull(timesheetService.getMissionById(id));
    }

    @Test
    public void testGetMissionById() {
        l.info("test testGetMissionById");
        Mission mission1 = new Mission("alfa", "b");
        int id = timesheetService.ajouterMission(mission1);

        Mission mission2 = timesheetService.getMissionById(id);
        assertNotNull(mission2);

        Mission mission3 = timesheetService.getMissionById(213232);
        assertNull(mission3);
    }

    @Test
    public void testAffecterMissionADepartement() {
        l.info("test  Affecter MissionA Departement");
        Mission mission = new Mission("FBI", "TUNIS");
        int idMission = timesheetService.ajouterMission(mission);
        Departement departement = new Departement("AI");
        int idDepartement = sEntreprise.ajouterDepartement(departement);
        int idDepAffecte = timesheetService.affecterMissionADepartement(idMission, idDepartement);
        assertEquals(idDepAffecte, idDepartement);
    }

    @Test
    public void testgetAllEmployeByMission() {
        l.info("methode getAllEmployeByMission");
        Mission mission = new Mission("FBI", "TUNIS");
        int idMission = timesheetService.ajouterMission(mission);
        try {
            List<Employe> listEmpl = timesheetRepository.getAllEmployeByMission(idMission);
            assertNotNull(listEmpl);
            l.info(listEmpl);
        } catch (Exception e) {
            l.error("could not get list employes" + e.getMessage());
        }
    }

    @Test
    public void testValiderTimesheet() {
        l.info(" ValiderTimesheet");
        Mission mission = new Mission("FBI", "TUNIS");
        timesheetService.ajouterMission(mission);
        Employe ingenieur = new Employe("DHIA", "HAN", "dhia.han@esprit.tn", true, Role.INGENIEUR);
        Employe chef = new Employe("ABDKADER", "ARF", "dhia.han@esprit.tn", true, Role.CHEF_DEPARTEMENT);
        Date dateDebut = new Date(System.currentTimeMillis());
        Date dateFin = new Date(System.currentTimeMillis());
        sEmploye.addOrUpdateEmploye(ingenieur);
        sEmploye.addOrUpdateEmploye(chef);
        assertEquals(0, timesheetService.validerTimesheet(mission.getId(), ingenieur.getId(), dateDebut, dateFin, chef.getId()));
        assertEquals(-1, timesheetService.validerTimesheet(mission.getId(), ingenieur.getId(), dateDebut, dateFin, ingenieur.getId()));
    }

}
