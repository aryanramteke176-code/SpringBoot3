package com.cfs.SpringBoot3.Repository;

import com.cfs.SpringBoot3.Entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByPath(String path);

    boolean existsByPath(String path);

    @Query("SELECT u.path FROM Url u")
    List<String> findAllPaths();
}