package guru.sfg.beer.order.service.services;

import guru.sfg.beer.order.service.web.model.BeerDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@ConfigurationProperties(prefix = "sfg.beerservice", ignoreUnknownFields = false)
public class BeerOrderRestTemplateServiceImpl implements BeerOrderRestTemplateService {

    private static final String API_PATH= "/api/v1/beer/{beerId}";
    private static final String UPC_PATH= "/api/v1/beerUpc/";
    private final RestTemplate restTemplate;
    private String beerOrderHostname;

    public String getBeerOrderHostname() {
        return beerOrderHostname;
    }

    public void setBeerOrderHostname(String beerOrderHostname) {
        this.beerOrderHostname = beerOrderHostname;
    }

    public BeerOrderRestTemplateServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    @Override
    public BeerDto getBeerDetails(UUID beerId) {
        ResponseEntity<BeerDto> responseEntity = restTemplate
                .exchange(beerOrderHostname + API_PATH, HttpMethod.GET, null,
                        new ParameterizedTypeReference<BeerDto>(){}, (Object) beerId);
        return responseEntity.getBody();
    }

    @Override
    public Optional<BeerDto> getBeerByUpc(String upc) {
        System.out.println( "URL IS " + beerOrderHostname+UPC_PATH + upc);
        BeerDto responseEntity = restTemplate.getForObject(beerOrderHostname+UPC_PATH + upc, BeerDto.class);
        return Optional.of(responseEntity);
    }
}
