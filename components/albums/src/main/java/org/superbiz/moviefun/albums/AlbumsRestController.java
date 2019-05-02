package org.superbiz.moviefun.albums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.superbiz.moviefun.blobstore.Blob;
import org.superbiz.moviefun.blobstore.BlobStore;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/albums")
public class AlbumsRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AlbumsRepository albumsRepository;
    private final BlobStore blobStore;


    public AlbumsRestController(AlbumsRepository albumsRepository, BlobStore blobStore) {
        this.albumsRepository = albumsRepository;
        this.blobStore = blobStore;
    }

    @PostMapping
    public void addAlbum(@RequestBody Album album){
        albumsRepository.addAlbum(album);
    }


    @GetMapping("/{id}")
    public Album findAlbum(@PathVariable long id){
        return albumsRepository.find(id);
    }

    @GetMapping
    public List<Album> getAlbums(){
        return albumsRepository.getAlbums();
    }

    @PostMapping("/{id}/cover")
    public void uploadCover(@PathVariable Long id, @RequestBody MultipartFile file){

        logger.debug("Uploading cover for album with id {}", id);
        if (file.getSize() > 0) {
            try {
                tryToUploadCover(id, file);

            } catch (IOException e) {
                logger.warn("Error while uploading album cover", e);
            }
        }
    }

    @GetMapping("{id}/cover")
    public Blob getCover(@PathVariable long id) throws IOException {
        Optional<Blob> maybeCoverBlob = blobStore.get(getCoverBlobName(id));
        Blob coverBlob = maybeCoverBlob.orElseGet(this::buildDefaultCoverBlob);
        return coverBlob;
    }

    private Blob buildDefaultCoverBlob() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream input = classLoader.getResourceAsStream("default-cover.jpg");

        return new Blob("default-cover", input, MediaType.IMAGE_JPEG_VALUE);
    }

    private void tryToUploadCover(Long id, MultipartFile uploadedFile) throws IOException {
        Blob coverBlob = new Blob(
                getCoverBlobName(id),
                uploadedFile.getInputStream(),
                uploadedFile.getContentType()
        );

        blobStore.put(coverBlob);
    }

    private String getCoverBlobName(long albumId) {
        return format("covers/%d", albumId);
    }


}
