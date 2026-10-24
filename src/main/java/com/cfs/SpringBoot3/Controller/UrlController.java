package com.cfs.SpringBoot3.Controller;

import com.cfs.SpringBoot3.Entity.Url;
import com.cfs.SpringBoot3.Service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/urlshortner")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(
            @RequestParam String originalUrl) {

        return ResponseEntity.ok(
                urlService.shortenUrl(originalUrl)
        );
    }

    @GetMapping
    public List<Url> getAllUrls() {
        return urlService.getAllUrls();
    }

    @GetMapping("/paths")
    public List<String> getAllPaths() {
        return urlService.getAllPaths();
    }

    @GetMapping("/paths/{path}")
    public ResponseEntity<Url> getByPath(
            @PathVariable String path) {

        return urlService.getByPath(path)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Url> getById(
            @PathVariable Long id) {

        return urlService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUrl(
            @PathVariable Long id) {

        if (urlService.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        urlService.deleteUrl(id);
        return ResponseEntity.noContent().build();
    }
}