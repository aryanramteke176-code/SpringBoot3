package com.cfs.SpringBoot3.Service;

import com.cfs.SpringBoot3.Entity.Url;
import com.cfs.SpringBoot3.Repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    private static final String BASE_URL = "http://localhost:8080/";
    private static final String CHARACTERS =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final SecureRandom RANDOM = new SecureRandom();

    public Url saveUrl(Url url) {
        return urlRepository.save(url);
    }

    public List<Url> getAllUrls() {
        return urlRepository.findAll();
    }

    public Optional<Url> getByPath(String path) {
        return urlRepository.findByPath(path);
    }

    public Optional<Url> getById(Long id) {
        return urlRepository.findById(id);
    }

    public List<String> getAllPaths() {
        return urlRepository.findAllPaths();
    }

    public void deleteUrl(Long id) {
        if (!urlRepository.existsById(id)) {
            throw new IllegalArgumentException("URL not found");
        }

        urlRepository.deleteById(id);
    }

    public String shortenUrl(String originalUrl) {

        if (originalUrl == null || originalUrl.isBlank()) {
            throw new IllegalArgumentException("URL cannot be empty");
        }

        if (originalUrl.length() > 2048) {
            throw new IllegalArgumentException("URL is too long");
        }

        try {
            URI uri = URI.create(originalUrl.trim());

            if (!("http".equalsIgnoreCase(uri.getScheme())
                    || "https".equalsIgnoreCase(uri.getScheme()))
                    || uri.getHost() == null) {
                throw new IllegalArgumentException(
                        "Enter a valid HTTP or HTTPS URL");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Enter a valid HTTP or HTTPS URL");
        }

        String path;

        // Retry if a generated code already exists.
        do {
            path = generateCode();
        } while (urlRepository.existsByPath(path));

        Url url = new Url();
        url.setOriginalUrl(originalUrl.trim());
        url.setPath(path);
        url.setSize(path.length());

        urlRepository.save(url);

        return BASE_URL + path;
    }

    private String generateCode() {
        StringBuilder code = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            code.append(
                    CHARACTERS.charAt(
                            RANDOM.nextInt(CHARACTERS.length())
                    )
            );
        }

        return code.toString();
    }
}