package com.mercadolivre.shortener.repositories;

import com.mercadolivre.shortener.entities.Url;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ShortenerRepositorie extends JpaRepository<Url, Long>{

//    @Query(value = "select u from Url u where u.urlShort = :urlShort")
    List<Url> findByUrlShort(String urlShort);

//    @Query(value = "select u from Url u where u.urlOriginal = :urlOriginal")
    List<Url> findByUrlOriginal(String urlOriginal);

    @Modifying
    @Query(value = "delete from Url u where u.updateDate <= current_date - (:days + 0)")
    void deleteAllInativeUrl(int days);

    // 10 20-15-5
}
