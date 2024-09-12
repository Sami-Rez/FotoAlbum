package at.spengergasse.FotoAlbum.presentation.api;


import at.spengergasse.FotoAlbum.domain.Album;
import at.spengergasse.FotoAlbum.domain.AlbumType;
import at.spengergasse.FotoAlbum.persistence.exception.DataQualityException;
import at.spengergasse.FotoAlbum.service.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static at.spengergasse.FotoAlbum.TestFixtures.album;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlbumRestController.class)
class AlbumRestControllerTest {

    private @Autowired MockMvc mockMvc;
    private @MockBean AlbumService albumService;

    @BeforeEach
    void setup() {
        assumeThat(mockMvc).isNotNull();
        assumeThat(albumService).isNotNull();
    }

    @Test
    void ensureGetApiAlbumsWorks() throws Exception {
        // given
        Album album = album("My existing Album");
        when(albumService.fetchAlbums(any())).thenReturn(List.of(album));

        // expect
        var request = get("/api/albums").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$[0].name").value("My existing Album"))
               .andExpect(jsonPath("$[0].type").value("DIGITAL"))
               .andExpect(jsonPath("$[0].owner.lastName").value("Chwatal"))
               .andDo(print());
    }

    @Test
    void ensureGetApiAlbumsWithEmptyResponseReturnNoContent() throws Exception {
        // given
        Album album = album("My existing Album");
        when(albumService.fetchAlbums(any())).thenReturn(Collections.emptyList());

        // expect
        var request = get("/api/albums").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
               .andExpect(status().isNoContent())
               .andDo(print());
    }

    @Test
    void ensureGetApiAlbumsReturnsInternalServerErrorOnDataQualityException() throws Exception {
        // given
        when(albumService.fetchAlbums(any())).thenThrow(DataQualityException.forInvalidEnumDBValue("X", AlbumType.class));

        // expect
        var request = get("/api/albums").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
               .andExpect(status().isInternalServerError())
               .andDo(print());
    }

}