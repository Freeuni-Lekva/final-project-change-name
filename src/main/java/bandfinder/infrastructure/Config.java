package bandfinder.infrastructure;

import bandfinder.serviceimplementations.HashingServiceImpl;
import bandfinder.services.HashingService;

public class Config {
    public static void ConfigureServices() {
        Injector.injectSingleton(HashingService.class, HashingServiceImpl.class);
    }
}
