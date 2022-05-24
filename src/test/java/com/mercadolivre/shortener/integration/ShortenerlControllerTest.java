package com.mercadolivre.shortener.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolivre.shortener.dto.UrlDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class ShortenerlControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Testa se cadastra com sucesso")
    public void testaSeCadastraComSucesso() throws Exception {
        UrlDTO urlDTOFake = createUrlDTOFake();

        String stringUrlDTOFake = objectMapper.writeValueAsString(urlDTOFake);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/me.li/")
                .contentType(MediaType.APPLICATION_JSON).content(stringUrlDTOFake))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.urlShort").exists())
                .andReturn();
    }

    @Test
    @DisplayName("Testa se cadastra a mesma url duas vezes")
    public void testaSeCadastraAMesmaUrlDuasVezes() throws Exception {
        UrlDTO urlDTOFake = createUrlDTOFake();

        String stringUrlDTOFake = objectMapper.writeValueAsString(urlDTOFake);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/me.li/")
                .contentType(MediaType.APPLICATION_JSON).content(stringUrlDTOFake))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/me.li/")
                .contentType(MediaType.APPLICATION_JSON).content(stringUrlDTOFake))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.urlShort").exists())
                .andReturn();
    }

    @Test
    @DisplayName("Testa se é possivel criar uma url com valor null")
    public void testaSeCadastraComValorNull() throws Exception {
        UrlDTO urlDTOFake = createUrlDTOFake();
        urlDTOFake.setUrl(null);

        String stringUrlDTOFake = objectMapper.writeValueAsString(urlDTOFake);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/me.li/")
                .contentType(MediaType.APPLICATION_JSON).content(stringUrlDTOFake))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Valor não pode ser null!")))
                .andReturn();
    }

    @Test
    @DisplayName("Testa se é possivel pegar todas as url cadastradas com quantidade de uso e urlOriginal")
    public void testaPegarRelatarioDeUsoDasUrls() throws Exception {
        UrlDTO urlDTOFake = createUrlDTOFake();

        String stringUrlDTOFake = objectMapper.writeValueAsString(urlDTOFake);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/me.li/")
                .contentType(MediaType.APPLICATION_JSON).content(stringUrlDTOFake))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders
                .get("/me.li/statistics")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].url").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numberVisitants").exists())
                .andReturn();
    }

    @Test
    @DisplayName("Testa se transforma uma url curta em uma longa")
    public void testaSeTransformaUmaUrlCurtaEmUmaLonga() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/me.li/AAAAAA")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers
                        .redirectedUrl("https://www.plazahoteis.com.br/wp-content/uploads/2019/07/gatinho-filhote-plaza-hoteis-jul19.jpg"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].url").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numberVisitants").exists())
                .andReturn();
    }

    @Test
    @DisplayName("Testa se retorna erro quando a url não esta cadastrada")
    public void testaQuandoBuscaUmaUrlNaoCadastrada() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/me.li/AAAAAA2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message").value("Url não cadastrada!"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numberVisitants").exists())
                .andReturn();
    }

    @Test
    @DisplayName("Testa se retorna erro ao tentar ver estatistica de uma url que não existe")
    public void testaSeRetornaErroDeNaoEncontrado() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/me.li/AAAAAAs/statistics")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message").value("Url não encontrada!"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numberVisitants").exists())
                .andReturn();
    }

    private static UrlDTO createUrlDTOFake() {
        String urlTest = "https://www.google.com/search?q=trem&rlz=1C5GCEM_enBR996BR996&source=lnms&tbm=isch&sa=X&ved=2ahUKEwiQ6tzc7un3AhUoBrkGHdOyAB4Q_AUoAnoECAIQBA&biw=1792&bih=939&dpr=2#imgrc=-CN9S74gWWZjLM2xxd";
        UrlDTO urlDTO = new UrlDTO();
        urlDTO.setUrl(urlTest);
        return urlDTO;
    }
}
