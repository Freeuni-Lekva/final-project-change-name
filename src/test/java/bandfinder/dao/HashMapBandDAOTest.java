package bandfinder.dao;

import org.junit.jupiter.api.BeforeEach;

public class HashMapBandDAOTest extends BandDAOTest{
    @Override
    @BeforeEach
    public void setUp() {
        this.dao = new HashmapBandDAO();
    }
}
