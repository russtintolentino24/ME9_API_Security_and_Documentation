package com.example.webservice.services;

import com.example.webservice.models.AlbumSong;
import com.example.webservice.models.Albums;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Collection;


public interface AlbumService extends CrudRepository<Albums, Integer> {
    @Query(value="Select albums.name as albumName, songs.name as songName, songs.length from Songs songs INNER JOIN Albums albums on Songs.album_id = albums.ID WHERE albums.name = ?1", nativeQuery = true)
    Collection<AlbumSong> getSongsByAlbum(String album);
}
