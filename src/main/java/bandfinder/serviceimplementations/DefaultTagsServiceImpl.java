package bandfinder.serviceimplementations;

import bandfinder.dao.BandDAO;
import bandfinder.dao.TagDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.ServiceValueSetter;
import bandfinder.models.Tag;
import bandfinder.services.DefaultTagsService;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.List;

public class DefaultTagsServiceImpl implements DefaultTagsService {
    private List<Tag> tags;

    @AutoInjectable
    private TagDAO tagDAO;

    public DefaultTagsServiceImpl() { //loads tags in website
        System.out.println("INIITT");
        ServiceValueSetter.setAutoInjectableFieldValues(this);
        tags = tagDAO.getAll();
    }

    @Override
    public List<Tag> getTags() {
        System.out.println("STARGINGGGG");
        for(Tag tg : tags){
            System.out.println(tg.getName());
        }
        return tags;
    }
}
