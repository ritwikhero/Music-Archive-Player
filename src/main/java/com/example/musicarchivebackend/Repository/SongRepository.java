package com.example.musicarchivebackend.Repository;

import com.example.musicarchivebackend.Model.Song;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends MongoRepository<Song,String> {
    boolean existsSongByFileNameEquals(String fileName);
    boolean existsSongByTitleEquals(String title);
}
