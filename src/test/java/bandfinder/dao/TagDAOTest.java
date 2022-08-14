package bandfinder.dao;

import bandfinder.models.Tag;
import bandfinder.serviceimplementations.HashMapTagDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class TagDAOTest {
    private TagDAO dao;

    @BeforeEach
    public void setUp() {
        this.dao = new HashMapTagDAO();
    }

    @Test
    public void testBasicTagOperations() {
        // create()
        Tag tag1 = new Tag("test tag");
        Assertions.assertEquals(tag1, dao.create(tag1));
        Tag tag2 = new Tag("test tag 2");
        Assertions.assertEquals(tag2, dao.create(tag2));

        int wrongId = -2;

        // update()
        String updatedTagName = "test updated tag";
        tag1.setName(updatedTagName);
        Assertions.assertEquals(updatedTagName, dao.update(tag1).getName());

        // getById()
        Assertions.assertEquals(tag1, dao.getById(tag1.getId()));
        Assertions.assertEquals(tag2, dao.getById(tag2.getId()));

        // getAll()
        List<Tag> tagList = dao.getAll();
        Assertions.assertEquals(2, tagList.size());
        Assertions.assertTrue(tagList.contains(tag1));
        Assertions.assertTrue(tagList.contains(tag2));

        // delete()
        Assertions.assertTrue(dao.delete(tag1.getId()));
        Assertions.assertFalse(dao.delete(tag1.getId()));
        Assertions.assertNull(dao.getById(tag1.getId()));
        Assertions.assertFalse(dao.delete(wrongId));
        tagList = dao.getAll();
        Assertions.assertEquals(1, tagList.size());
        Assertions.assertTrue(tagList.contains(tag2));

    }

    @Test
    public void testSingleBandCase() {
        int bandId = 1;

        int tag1Id = 123;
        Tag tag1 = new Tag(tag1Id, "test tag 1");

        int tag2Id = 255;
        Tag tag2 = new Tag(tag2Id, "test tag 2");

        int wrongTagId = 404;
        Tag wrongTag = new Tag(wrongTagId, "some tag that has not been added yet");

        // Addition
        dao.create(tag1);
        dao.create(tag2);

        Assertions.assertTrue(dao.addTagToBand(tag1Id, bandId));
        Assertions.assertTrue(dao.bandHasTag(tag1Id, bandId));

        List<Integer> bandTags = dao.getBandTagIDs(bandId);
        Assertions.assertEquals(1, bandTags.size());
        Assertions.assertEquals(tag1Id, bandTags.get(0));

        Assertions.assertTrue(dao.addTagToBand(tag2Id, bandId));
        Assertions.assertTrue(dao.bandHasTag(tag2Id, bandId));

        bandTags = dao.getBandTagIDs(bandId);
        Assertions.assertEquals(2, bandTags.size());
        Assertions.assertTrue(bandTags.contains(tag2Id));

        Assertions.assertFalse(dao.addTagToBand(tag1Id, bandId));
        Assertions.assertFalse(dao.addTagToBand(tag2Id, bandId));
        Assertions.assertFalse(dao.addTagToBand(wrongTagId, bandId));

        // Deletion
        Assertions.assertTrue(dao.removeTagFromBand(tag2Id, bandId));
        Assertions.assertFalse(dao.bandHasTag(tag2Id, bandId));

        Assertions.assertFalse(dao.removeTagFromBand(wrongTagId, bandId));
        bandTags = dao.getBandTagIDs(bandId);
        Assertions.assertEquals(1, bandTags.size());
    }

    @Test
    public void testSingleUserCase(){
        int userId = 1;

        int tag1Id = 123;
        Tag tag1 = new Tag(tag1Id, "test tag 1");

        int tag2Id = 255;
        Tag tag2 = new Tag(tag2Id, "test tag 2");

        int wrongTagId = 404;
        Tag wrongTag = new Tag(wrongTagId, "some tag that has not been added yet");

        // Addition
        dao.create(tag1);
        dao.create(tag2);

        Assertions.assertTrue(dao.addTagToUser(tag1Id, userId));
        Assertions.assertTrue(dao.userHasTag(tag1Id, userId));

        List<Integer> userTags = dao.getUserTagIDs(userId);
        Assertions.assertEquals(1, userTags.size());
        Assertions.assertEquals(tag1Id, userTags.get(0));

        Assertions.assertTrue(dao.addTagToUser(tag2Id, userId));
        Assertions.assertTrue(dao.userHasTag(tag2Id, userId));

        userTags = dao.getUserTagIDs(userId);
        Assertions.assertEquals(2, userTags.size());
        Assertions.assertTrue(userTags.contains(tag2Id));

        Assertions.assertFalse(dao.addTagToUser(tag1Id, userId));
        Assertions.assertFalse(dao.addTagToUser(tag2Id, userId));
        Assertions.assertFalse(dao.addTagToUser(wrongTagId, userId));

        // Deletion
        Assertions.assertTrue(dao.removeTagFromUser(tag2Id, userId));
        Assertions.assertFalse(dao.userHasTag(tag2Id, userId));

        Assertions.assertFalse(dao.removeTagFromUser(wrongTagId, userId));
        userTags = dao.getUserTagIDs(userId);
        Assertions.assertEquals(1, userTags.size());
    }
}