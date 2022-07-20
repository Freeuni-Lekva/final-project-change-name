package bandfinder.dao;

import bandfinder.models.Band;
import bandfinder.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

abstract class BandDAOTest {

    protected BandDAO dao;
    
    public abstract void setUp();

    @Test
    void testPack1() {
        //this testpack tests the general functions of the dao class:
        //create,update,delete,getByID,getall

        Band band = new Band("Blink-182");

        //testing dao.create
        dao.create(band);
        int id = band.getId();
        Assertions.assertEquals(dao.getById(id),band);

        //testing dao.update
        band.setName("Quchis bichebi");
        dao.update(band);
        Assertions.assertEquals(dao.getById(id).getName(),"Quchis bichebi");

        //testing dao.delete
        Assertions.assertFalse(dao.delete(id-1)); //such band should not exist in datastore
        Assertions.assertTrue(dao.delete(id));
        Assertions.assertNull(dao.getById(id)); //quchis bichebi should not be in the datastore anymore

        //testing dao getall
        Assertions.assertEquals(dao.getAll().size(),0);

        dao.create(new Band("Messhugah"));
        dao.create(new Band("Lamb of god"));
        Band lastband = dao.create(new Band("Korn"));
        dao.delete(lastband.getId());

        Assertions.assertEquals(dao.getAll().size(),2);
    }

    @Test
    void testPack2() {
        //this testpack tests user-band managament
        Band band1 = new Band("Blink-182");
        Band band2 = new Band("Erisioni");

        User user1 = new User(1,"mail1", "abcd", "Tope", "top123", "stagetop");
        User user2 = new User(2,"mail2", "abcde", "Tanju", "tanj123", "tanjeks");

        dao.create(band1);
        dao.create(band2);

        Assertions.assertEquals(0,dao.getAllBandIDsForUser(user1.getId()).size()); //user 1 is in no bands
        Assertions.assertFalse(dao.isUserInBand(user1.getId(),band1.getId())); //check for each combination below
        Assertions.assertFalse(dao.isUserInBand(user2.getId(),band1.getId()));
        Assertions.assertFalse(dao.isUserInBand(user1.getId(),band2.getId()));
        Assertions.assertFalse(dao.isUserInBand(user2.getId(),band2.getId()));

        dao.addMemberToBand(user1.getId(),band1.getId());
        Assertions.assertTrue(dao.isUserInBand(user1.getId(),band1.getId())); //user1 now should be in band1
        dao.addMemberToBand(user2.getId(),band1.getId());
        Assertions.assertEquals(2,dao.getBandMemberIDs(band1.getId()).size()); //band1 now should have user1 AND user 2
        Assertions.assertTrue(dao.removeMemberFromBand(user1.getId(),band1.getId())); //user1 should be succesfuly removed from band1
        Assertions.assertFalse(dao.addMemberToBand(user2.getId(),band1.getId())); //user2 should not be added to band1, as its already there
        Assertions.assertEquals(1,dao.getBandMemberIDs(band1.getId()).size()); //band1 only has user2


        Assertions.assertFalse(dao.removeMemberFromBand(user1.getId(),band2.getId())); //User1 should not be able to be removed from band2, cause its not there
        Assertions.assertTrue(dao.addMemberToBand(user1.getId(),band2.getId()));
        Assertions.assertTrue(dao.addMemberToBand(user2.getId(),band2.getId()));

        Assertions.assertEquals(1,dao.getAllBandIDsForUser(user1.getId()).size()); //user 1 is in band 2
        Assertions.assertEquals(2,dao.getAllBandIDsForUser(user2.getId()).size()); //user 2 is in band 1 and band 2

    }
}