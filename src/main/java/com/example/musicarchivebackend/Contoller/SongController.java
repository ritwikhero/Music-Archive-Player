package com.example.musicarchivebackend.Contoller;

import com.example.musicarchivebackend.Model.Song;
import com.example.musicarchivebackend.Repository.SongRepository;
import com.example.musicarchivebackend.Service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SongController {
    private final StorageService storageService;
    private final SongRepository songRepository;

    @Autowired
    public SongController(StorageService storageService, SongRepository songRepository) {
        this.storageService = storageService;
        this.songRepository = songRepository;
    }

    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getSongs(){
        return ResponseEntity.ok(songRepository.findAll());
    }

    @GetMapping("/song/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable String id){
        Optional<Song> song = songRepository.findById(id);

        if(song.isPresent()){
            return ResponseEntity.ok(song.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = "multipart/data")
    public ResponseEntity<?> createSong(@RequestParam("song") Song song, @RequestParam("file")MultipartFile file) throws IOException{
        if(songRepository.existsSongByFileNameEquals(file.getOriginalFilename()) || songRepository.existsSongByFileNameEquals(song.getTitle())){
            return ResponseEntity.badRequest().body("taken");
        }else{
            System.out.println("Uploading the file...");
            storageService.uploadSong(file);
        }
        //saving the song data into database
        song.setFileName(file.getOriginalFilename());
        Song insertedSong = songRepository.insert(song);
        return new ResponseEntity<>(insertedSong, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable String id, @RequestBody Song songData){

        Optional<Song> songOptional = songRepository.findById(id);

        if (songOptional.isPresent()){

            Song song = songOptional.get();

            if (songData.getTitle() != null){
                song.setTitle(songData.getTitle());
            }

            if (songData.getArtist() != null){
                song.setArtist(songData.getArtist());
            }

            song.setFavourite(songData.isFavourite());
            songRepository.save(song);

            return ResponseEntity.ok(song);
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}
