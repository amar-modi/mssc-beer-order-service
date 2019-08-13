package guru.sfg.beer.order.service.web.mappers;


import guru.sfg.beer.order.service.domain.BeerOrderLine;
import guru.sfg.beer.order.service.services.BeerOrderRestTemplateService;
import guru.sfg.beer.order.service.web.model.BeerDto;
import guru.sfg.beer.order.service.web.model.BeerOrderLineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper {

    private BeerOrderRestTemplateService beerOrderRestTemplateService;
    private BeerOrderLineMapper mapper;

    @Autowired
    public void setMapper(BeerOrderRestTemplateService beerOrderRestTemplateService) {
        this.beerOrderRestTemplateService = beerOrderRestTemplateService;
    }

    @Autowired
    @Qualifier("delegate")
    public void setBeerOrderLineMapper(BeerOrderLineMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line){
        Optional<BeerDto> beerByUpc = beerOrderRestTemplateService.getBeerByUpc(line.getUpc());
        BeerOrderLineDto beerOrderLineDto = mapper.beerOrderLineToDto(line);

        beerByUpc.ifPresent( beerDto -> {
            beerOrderLineDto.setBeerStyle(beerDto.getBeerStyle().name());
            beerOrderLineDto.setPrice(beerDto.getPrice());
            beerOrderLineDto.setBeerName(beerDto.getBeerName());
            beerOrderLineDto.setBeerId(beerDto.getId());
        });

        return beerOrderLineDto;
    }


}
