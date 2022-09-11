package bandfinder.serviceimplementations;

import bandfinder.dao.BandDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.ServiceValueSetter;
import bandfinder.models.Band;
import bandfinder.services.BandAutoComplete;

import java.util.List;

public class AllBandsAutocompleteServiceImpl implements BandAutoComplete {
    private List<Band> bands;

    @AutoInjectable
    private BandDAO bandDAO;

    public AllBandsAutocompleteServiceImpl() { //loads bands in website
        ServiceValueSetter.setAutoInjectableFieldValues(this);
        bands = bandDAO.getAll();
    }

    @Override
    public List<Band> get() {
        return bands;
    }
}
