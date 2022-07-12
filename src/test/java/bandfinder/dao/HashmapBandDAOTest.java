package bandfinder.dao;

import bandfinder.models.Band;
import bandfinder.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashmapBandDAOTest {

    @Test
    void testPack1() {
        //this testpack tests the general functions of the dao class:
        //create,update,delete,getByID,getall

        BandDAO bandDao = new HashmapBandDAO();
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
        Assertions.assertEquals(false,bandDao.delete(id-1)); //such band should not exist in datastore
        Assertions.assertEquals(true,bandDao.delete(id));
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
        BandDAO bandDao = new HashmapBandDAO();
        Band band1 = new Band("Blink-182");
        Band band2 = new Band("Erisioni");

        User user1 = new User(1,"mail1", "abcd", "Tope", "top123", "stagetop");
        User user2 = new User(2,"mail2", "abcde", "Tanju", "tanj123", "tanjeks");

        bandDao.create(band1);
        bandDao.create(band2);

        Assertions.assertEquals(0,bandDao.getAllBandsForUser(user1).size()); //user 1 is in no bands
        Assertions.assertEquals(false,bandDao.isUserInBand(user1,band1)); //check for each combination below
        Assertions.assertEquals(false,bandDao.isUserInBand(user2,band1));
        Assertions.assertEquals(false,bandDao.isUserInBand(user1,band2));
        Assertions.assertEquals(false,bandDao.isUserInBand(user2,band2));

        bandDao.addMemberToBand(user1,band1);
        Assertions.assertEquals(true,bandDao.isUserInBand(user1,band1)); //user1 now should be in band1
        bandDao.addMemberToBand(user2,band1);
        Assertions.assertEquals(2,bandDao.getBandMembers(band1).size()); //band1 now should have user1 AND user 2
        Assertions.assertEquals(true,bandDao.removeMemberFromBand(user1,band1)); //user1 should be succesfuly removed from band1
        Assertions.assertEquals(false,bandDao.addMemberToBand(user2,band1)); //user2 should not be added to band1, as its already there
        Assertions.assertEquals(1,bandDao.getBandMembers(band1).size()); //band1 only has user2


        Assertions.assertEquals(false,bandDao.removeMemberFromBand(user1,band2)); //User1 should not be able to be removed from band2, cause its not there
        Assertions.assertEquals(true,bandDao.addMemberToBand(user1,band2));
        Assertions.assertEquals(true,bandDao.addMemberToBand(user2,band2));

        Assertions.assertEquals(1,bandDao.getAllBandsForUser(user1).size()); //user 1 is in band 2
        Assertions.assertEquals(2,bandDao.getAllBandsForUser(user2).size()); //user 2 is in band 1 and band 2

    }
}