package bandfinder.dao;

import bandfinder.models.Band;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashmapBandDAOTest {

    @Test
    void testPack1() {
        //this test tests the general functions of the dao class:
        //create,update,delete,getByID,getall

        BandDAO bandDao = new HashmapBandDAO();
        Band band = new Band("Blink-182");

        //testing dao.create
        bandDao.create(band);
        int id = band.getId();
        assertEquals(bandDao.getById(id),band);

        //testing dao.update
        band.setName("Quchis bichebi");
        bandDao.update(band);
        assertEquals(bandDao.getById(id).getName(),"Quchis bichebi");

        //testing dao.delete
        assertFalse(bandDao.delete(id-1)); //such band should not exist in datastore
        assertTrue(bandDao.delete(id));
        assertNull(bandDao.getById(id)); //quchis bichebi should not be in the datastore anymore

        //testing dao getall
        assertEquals(bandDao.getAll().size(),0);

        bandDao.create(new Band("Messhugah"));
        bandDao.create(new Band("Lamb of god"));
        Band lastband = bandDao.create(new Band("Korn"));
        bandDao.delete(lastband.getId());

        assertEquals(bandDao.getAll().size(),2);
    }

    @Test
    void isUserInBand() {
    }

    @Test
    void addMemberToBand() {
    }

    @Test
    void removeMemberFromBand() {
    }

    @Test
    void getBandMembers() {
    }

    @Test
    void getAllBandsForUser() {
    }
}