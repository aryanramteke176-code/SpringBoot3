package com.cfs.SpringBoot3.Controller;

import com.cfs.SpringBoot3.Service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final UrlService urlService;

    @GetMapping("/{path:[a-zA-Z0-9]{6}}")
    public ResponseEntity<Void> redirect(
            @PathVariable String path) {

        return urlService.getByPath(path)
                .map(url -> ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(
                                HttpHeaders.LOCATION,
                                url.getOriginalUrl()
                        )
                        .<Void>build())
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }
}