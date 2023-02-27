package com.example.webservice.controller.AlbumController;

import com.example.webservice.models.AlbumSong;
import com.example.webservice.models.Albums;
import com.example.webservice.models.Songs;
import com.example.webservice.services.AlbumService;
import lombok.var;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@EnableAutoConfiguration
@CrossOrigin(origins="*")
public class AlbumController {
    @Autowired
    AlbumService albumService;
    Songs CurrentSong;
    Songs CurrentLongestSong;

    @GetMapping(value = "/album")
    public Iterable<Albums> getAlbums() {
        return albumService.findAll();
    }

    @PostMapping(path = "/album")
    public @ResponseBody
    String addAlbum(@RequestBody Albums albums) {
        Albums albumToInsert = new Albums();
        albumToInsert.setName(albums.getName());
        albumToInsert.setBand_id(albums.getBand_id());
        albumToInsert.setRelease_year(albums.getRelease_year());
        albumService.save(albumToInsert);
        return "Album Saved";
    }

    @PostMapping(path = "/album/update")
    public @ResponseBody
    String updateAlbum(@RequestBody Albums albums) {
        Albums albumToInsert = new Albums();
        albumToInsert.setId(albums.getId());
        albumToInsert.setName(albums.getName());
        albumToInsert.setBand_id(albums.getBand_id());
        albumToInsert.setRelease_year(albums.getRelease_year());
        albumService.save(albumToInsert);
        return "Album Updated";
    }

    @DeleteMapping(path = "/album")
    public @ResponseBody
    String deleteAlbum(@RequestBody Albums albums) {
        albumService.deleteById(albums.getId());
        return "Album Deleted";
    }

    @GetMapping(path="/song")
    public @ResponseBody
    Collection<AlbumSong> getSongPerAlbum(@RequestParam String album) {
        return albumService.getSongsByAlbum(album);
    }

    /*@GetMapping(path = "/song")
    public @ResponseBody
    Songs getMaxLengthPerAlbum(@RequestParam String album) {

        Iterable<Albums> albumList = getAlbums();
        Collection<AlbumSong> songsList = getSongPerAlbum(album);
        List<JSONObject> JsonList = new ArrayList<JSONObject>();

        //for(AlbumSong albumSong : getSongPerAlbum(album)) {
        for(Albums albums: albumList) {

            for (AlbumSong albumSong : songsList) {

                CurrentSong = (Songs)albumSong;
                //CurrentLongestSong = CurrentSong;
                CurrentLongestSong = null;
                if (CurrentSong.getLength() > CurrentLongestSong.getLength()) {
                    CurrentLongestSong = CurrentSong;
                }

            }

            System.out.println(CurrentLongestSong.getLength());
            System.out.println(albums.getRelease_year());
            System.out.println(CurrentLongestSong);

        }
        return CurrentLongestSong;
    }
     */




    public @ResponseBody
    JSONObject getLongestSongPerAlbum(@RequestParam String album) throws JSONException {

        Iterable<Albums> albumList = getAlbums();
        Collection<AlbumSong> songsList = getSongPerAlbum(album);
        List<JSONObject> JsonList = new ArrayList<JSONObject>();

        JSONObject LongestSongOut = new JSONObject();
        //for(AlbumSong albumSong : getSongPerAlbum(album)) {
        for(Albums albums: albumList) {

            for (AlbumSong albumSong : songsList) {

                CurrentSong = (Songs)albumSong;

                if (CurrentSong.getLength() > CurrentLongestSong.getLength()) {
                    CurrentLongestSong = CurrentSong;
                }

            }

            //System.out.println(CurrentLongestSong.getLength());
            //System.out.println(albums.getRelease_year());
            //System.out.println(CurrentLongestSong);
            LongestSongOut.put("Song Length", CurrentLongestSong.getLength());
            LongestSongOut.put("Release Year", albums.getRelease_year());
            LongestSongOut.put("Song", CurrentLongestSong);



        }
        return LongestSongOut;
    }



   /* @GetMapping(path = "/song")
    public @ResponseBody

    Integer getNumberOfSongsPerBand(@RequestParam Integer band_id) {
        int i = 0;
        Iterable<Albums> albumList = getAlbums();

        for(Albums BandAlbums : albumList) {

            //for (int i = band_id; i == BandAlbums.getBand_id(); string AlbumName = BandAlbums.getName())
           int BandVerify = BandAlbums.getBand_id();

           if(BandVerify == band_id){

              String BandAlbumName = BandAlbums.getName();
            Collection <AlbumSong> BandSongs = getSongPerAlbum(BandAlbumName);
            for(AlbumSong Counter: BandSongs){
                i++;

              }
           }
        }

        return i;
    }
    */



    public @ResponseBody

    List <JSONObject> getSongNumberPerBand(Integer band_id) throws JSONException {
        int i = 0;
        Iterable<Albums> albumList = getAlbums();

        List<JSONObject> JsonList = new ArrayList<JSONObject>();

        for(Albums BandAlbums : albumList) {

            //for (int i = band_id; i == BandAlbums.getBand_id(); string AlbumName = BandAlbums.getName())
            int BandVerify = BandAlbums.getBand_id();

            if(BandVerify == band_id){

                String BandAlbumName = BandAlbums.getName();
                Collection <AlbumSong> BandSongs = getSongPerAlbum(BandAlbumName);
                JSONObject BandOut = new JSONObject();

                for(AlbumSong Counter: BandSongs){

                    i++;




                }


                BandOut.put("Band ID", BandVerify);
                BandOut.put("Number of Songs", i);
                JsonList.add(i, BandOut);
            }
        }


        return JsonList;
    }





}
