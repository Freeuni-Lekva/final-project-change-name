package bandfinder.serviceimplementations;

import bandfinder.dao.TagDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.ServiceValueSetter;
import bandfinder.models.Tag;
import bandfinder.services.AutoCompleteService;
import bandfinder.services.TagAutoComplete;

import java.util.List;

public class AllTagsAutocompleteServiceImpl implements TagAutoComplete {
    private List<Tag> tags;

    @AutoInjectable
    private TagDAO tagDAO;

    public AllTagsAutocompleteServiceImpl() { //loads tags in website
        ServiceValueSetter.setAutoInjectableFieldValues(this);
        tags = tagDAO.getAll();
    }

    @Override
    public List<Tag> get() {
        return tags;
    }
}
