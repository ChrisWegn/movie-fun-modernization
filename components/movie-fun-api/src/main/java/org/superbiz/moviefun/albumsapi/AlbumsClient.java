package org.superbiz.moviefun.albumsapi;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.multipart.MultipartFile;
import org.superbiz.moviefun.blobstore.Blob;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class AlbumsClient {

    private final RestOperations restOperations;
    private final String albumsUrl;

    private static ParameterizedTypeReference<List<AlbumInfo>> albumListType = new ParameterizedTypeReference<List<AlbumInfo>>() {
    };

    public AlbumsClient(String albumsUrl, RestOperations restOperations) {
        this.restOperations = restOperations;
        this.albumsUrl = albumsUrl;
    }

    public void addAlbum(AlbumInfo album) {
        restOperations.postForEntity(albumsUrl, album, AlbumInfo.class);
    }

    public List<AlbumInfo> getAlbums() {
        return restOperations.exchange(albumsUrl, HttpMethod.GET, null, albumListType).getBody();
    }

    public AlbumInfo find(long id) {
        return restOperations.getForObject(albumsUrl + "/"+ id, AlbumInfo.class);
    }

    public void uploadCover(Long id, MultipartFile uploadedFile) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("file", uploadedFile);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        restOperations.exchange(albumsUrl + "/" + id + "/cover", HttpMethod.POST, requestEntity, String.class);
    }

    public Blob getCover(long id) {
        return restOperations.getForObject(albumsUrl + "/" + id + "/cover", Blob.class);
    }
}
