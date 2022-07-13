package bandfinder.dao;

import bandfinder.models.Band;
import bandfinder.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BandDAOTest {

    private BandDAO bandDao;

    @BeforeEach
    void setUp() {
        bandDao = new HashmapBandDAO();
    }

    @Test
    void testPack1() {
        //this testpack tests the general functions of the dao class:
        //create,update,delete,getByID,getall

        Band band = new Band("Blink-182");

        //testing dao.create
        bandDao.create(band);
        int id = band.getId();
        Assertions.assertEquals(bandDao.getById(id),band);

        //testing dao.update
        band.setName("Quchis bichebi");
        bandDao.update(band);
        Assertions.assertEquals(bandDao.getById(id).getName(),"Quchis bichebi");

        //testing dao.delete
        Assertions.assertFalse(bandDao.delete(id-1)); //such band should not exist in datastore
        Assertions.assertTrue(bandDao.delete(id));
        Assertions.assertNull(bandDao.getById(id)); //quchis bichebi should not be in the datastore anymore

        //testing dao getall
        Assertions.assertEquals(bandDao.getAll().size(),0);

        bandDao.create(new Band("Messhugah"));
        bandDao.create(new Band("Lamb of god"));
        Band lastband = bandDao.create(new Band("Korn"));
        bandDao.delete(lastband.getId());

        Assertions.assertEquals(bandDao.getAll().size(),2);
    }

    @Test
    void testPack2() {
        //this testpack tests user-band managament
        Band band1 = new Band("Blink-182");
        Band band2 = new Band("Erisioni");

        User user1 = new User(1,"mail1", "abcd", "Tope", "top123", "stagetop");
        User user2 = new User(2,"mail2", "abcde", "Tanju", "tanj123", "tanjeks");

        bandDao.create(band1);
        bandDao.create(band2);

        Assertions.assertEquals(0,bandDao.getAllBandIDsForUser(user1.getId()).size()); //user 1 is in no bands
        Assertions.assertFalse(bandDao.isUserInBand(user1.getId(),band1.getId())); //check for each combination below
        Assertions.assertFalse(bandDao.isUserInBand(user2.getId(),band1.getId()));
        Assertions.assertFalse(bandDao.isUserInBand(user1.getId(),band2.getId()));
        Assertions.assertFalse(bandDao.isUserInBand(user2.getId(),band2.getId()));

        bandDao.addMemberToBand(user1.getId(),band1.getId());
        Assertions.assertTrue(bandDao.isUserInBand(user1.getId(),band1.getId())); //user1 now should be in band1
        bandDao.addMemberToBand(user2.getId(),band1.getId());
        Assertions.assertEquals(2,bandDao.getBandMemberIDs(band1.getId()).size()); //band1 now should have user1 AND user 2
        Assertions.assertTrue(bandDao.removeMemberFromBand(user1.getId(),band1.getId())); //user1 should be succesfuly removed from band1
        Assertions.assertFalse(bandDao.addMemberToBand(user2.getId(),band1.getId())); //user2 should not be added to band1, as its already there
        Assertions.assertEquals(1,bandDao.getBandMemberIDs(band1.getId()).size()); //band1 only has user2


        Assertions.assertFalse(bandDao.removeMemberFromBand(user1.getId(),band2.getId())); //User1 should not be able to be removed from band2, cause its not there
        Assertions.assertTrue(bandDao.addMemberToBand(user1.getId(),band2.getId()));
        Assertions.assertTrue(bandDao.addMemberToBand(user2.getId(),band2.getId()));

        Assertions.assertEquals(1,bandDao.getAllBandIDsForUser(user1.getId()).size()); //user 1 is in band 2
        Assertions.assertEquals(2,bandDao.getAllBandIDsForUser(user2.getId()).size()); //user 2 is in band 1 and band 2

    }
}