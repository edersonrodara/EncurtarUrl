package com.mercadolivre.shortener.controllers;

import com.mercadolivre.shortener.dto.ResponseUrlDTO;
import com.mercadolivre.shortener.dto.UrlDTO;
import com.mercadolivre.shortener.dto.UrlStatisticDTO;
import com.mercadolivre.shortener.services.ShortenerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.stream.Stream;

@RestController
public class ShortenerController {

    private final ShortenerService shortenerService;

    public ShortenerController(ShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @PostMapping("/me.li/")
    public ResponseEntity<ResponseUrlDTO> createShortUrl(@Valid @RequestBody UrlDTO url, HttpServletRequest request) {

        ResponseUrlDTO urlShort = shortenerService.createShortUrl(url.getUrl());

        return ResponseEntity.created(URI.create(request.getRequestURI())).body(urlShort);
    }

    @GetMapping("/me.li/{url}")
    public RedirectView getUrl(@PathVariable UrlDTO url) {
        String urlOriginal = shortenerService.getUrl(url.getUrl());

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(urlOriginal);

//        return redirectView;
        ResponseEntity.ok(redirectView);

        return redirectView;

    }

    @GetMapping("/me.li/statistics")
    public Stream<UrlStatisticDTO> getStatistic() {
        return shortenerService.getStatistic();
    }

    @GetMapping("/me.li/{urlShort}/statistics")
    public UrlStatisticDTO getTargetStatistic(@PathVariable UrlDTO urlShort) {
        return shortenerService.getTargetStatistic(urlShort);
    }

}
