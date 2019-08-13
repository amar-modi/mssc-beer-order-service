package guru.sfg.beer.order.service.services;

import guru.sfg.beer.order.service.web.model.BeerDto;

import java.util.Optional;
import java.util.UUID;

public interface BeerOrderRestTemplateService {

    BeerDto getBeerDetails(UUID beerId);
    Optional<BeerDto> getBeerByUpc(String upc);
}
