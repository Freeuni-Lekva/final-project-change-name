package bandfinder.services;

import bandfinder.models.Tag;

import java.util.List;

public interface DefaultTagsService {
    List<Tag> getUserTags();
    List<Tag> getBandTags();
}
