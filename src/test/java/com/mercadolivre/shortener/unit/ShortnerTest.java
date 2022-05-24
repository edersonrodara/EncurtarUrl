package com.mercadolivre.shortener.unit;

import com.mercadolivre.shortener.dto.UrlDTO;
import com.mercadolivre.shortener.entities.Url;
import com.mercadolivre.shortener.repositories.ShortenerRepositorie;
import com.mercadolivre.shortener.services.ShortenerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShortnerTest {
    @Mock
    private ShortenerRepositorie shortenerRepositorie;

    @InjectMocks
    private ShortenerService shortenerService;

    @Test
    @DisplayName("Testa se é salvo com sucesso")
    public void testaSeSalvaComSucesso() {
        UrlDTO urlDtoFake = createUrlDtoFake();

        when(shortenerRepositorie.findByUrlShort(Mockito.anyString())).thenReturn(new ArrayList<>());

        shortenerService.createShortUrl(urlDtoFake.getUrl());

        Mockito.verify(shortenerRepositorie, Mockito.times(1)).save(Mockito.any(Url.class));
    }

    @Test
    @DisplayName("Testa se busca corretamente")
    public void testaSeBuscaCorretamente() {
        List<Url> urlListFake = new ArrayList<>();
        Url urlFake = createUrlFake();
        urlListFake.add(urlFake);

        when(shortenerRepositorie.findByUrlShort(anyString())).thenReturn(urlListFake);

        shortenerService.getUrl(Mockito.anyString());

        Mockito.verify(shortenerRepositorie, Mockito.times(1)).findByUrlShort("");
    }

    private UrlDTO createUrlDtoFake() {
        UrlDTO urlDTO = new UrlDTO();
        urlDTO.setUrl("www.qualquercoisa.com.br/sdfhajshdkfjahsdjhfk.jpg");
        return urlDTO;
    }

    private Url createUrlFake() {
        return Url.builder()
                .updateDate(LocalDate.parse("2022-01-01"))
                .createDate(LocalDate.parse("2022-01-01"))
                .urlShort("AAAAAA")
                .urlOriginal("qualquerUrl")
                .id(1L)
                .numberVisitants(1)
                .build();
    }
}
