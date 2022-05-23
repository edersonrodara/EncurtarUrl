package com.mercadolivre.shortener.services;

import com.mercadolivre.shortener.dto.ResponseUrlDTO;
import com.mercadolivre.shortener.dto.UrlDTO;
import com.mercadolivre.shortener.dto.UrlStatisticDTO;
import com.mercadolivre.shortener.entities.Url;
import com.mercadolivre.shortener.exceptions.NotFoundException;
import com.mercadolivre.shortener.repositories.ShortenerRepositorie;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ShortenerService {

    private final ShortenerRepositorie shortenerRepositorie;

    public ShortenerService(ShortenerRepositorie shortenerRepositorie) {
        this.shortenerRepositorie = shortenerRepositorie;
    }

    public ResponseUrlDTO createShortUrl(String url) {

        String urlLINK = "http://localhost:8080/me.li/";

        ResponseUrlDTO responseUrlDTO = new ResponseUrlDTO();
        int QUANTITY = 6;
        String sixCharacters = "";

        List<Url> byUrlOriginal = shortenerRepositorie.findByUrlOriginal(url);
        if (!byUrlOriginal.isEmpty()) {
            responseUrlDTO.setUrlShort(urlLINK + byUrlOriginal.get(0).getUrlShort());
            return responseUrlDTO;
        }

        while (sixCharacters.isEmpty()) {

            String generateString = generateString(QUANTITY);

            boolean isEmpty = shortenerRepositorie.findByUrlShort(generateString).isEmpty();

            if (!isEmpty) {
                break;
            }
            sixCharacters = generateString;
        }

        Url urlResult = Url.builder()
                .urlOriginal(url)
                .urlShort(sixCharacters)
                .createDate(LocalDate.now())
                .updateDate(LocalDate.now()).build();

        shortenerRepositorie.save(urlResult);

        responseUrlDTO.setUrlShort(urlLINK + sixCharacters);

        return responseUrlDTO;
    }

    public String getUrl(String url) {
        List<Url> byUrlShort = shortenerRepositorie.findByUrlShort(url);
        if (byUrlShort.isEmpty()) {
            return "Url nao cadastrada";
        }

        byUrlShort.get(0).setUpdateDate(LocalDate.now());
        byUrlShort.get(0).setNumberVisitants(byUrlShort.get(0)
                .getNumberVisitants() + 1);

        shortenerRepositorie.save(byUrlShort.get(0));

        return byUrlShort.get(0).getUrlOriginal();
    }

    public Stream<UrlStatisticDTO> getStatistic() {
        List<Url> all = shortenerRepositorie.findAll();
        Stream<UrlStatisticDTO> urlStatisticDTOStream = all.stream()
                .map(url -> UrlStatisticDTO.builder()
                        .url(url.getUrlOriginal())
                        .numberVisitants(url.getNumberVisitants()).build());

        return urlStatisticDTOStream;
    }

    public UrlStatisticDTO getTargetStatistic(UrlDTO urlShort) {

        List<Url> url = shortenerRepositorie.findByUrlShort(urlShort.getUrl());

        if (url.isEmpty()) {
            throw new NotFoundException("Url não encontrada!");
        }

        return UrlStatisticDTO.builder()
                .url(url.get(0).getUrlOriginal())
                .numberVisitants(url.get(0).getNumberVisitants())
                .build();
    }

    // Gerador de string aleatório
    public String generateString(int quantity) {
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder string = new StringBuilder(quantity);

        for (int i = 0; i < quantity; i++) {
            int index = (int) (alphaNumericString.length() * Math.random());
            string.append(alphaNumericString.charAt(index));
        }
        return string.toString();
    }
}
