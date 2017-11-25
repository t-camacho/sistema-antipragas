package com.antipragas.controllers;

import com.antipragas.models.Praga;
import com.antipragas.services.PragaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Thais Camacho
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PragaController.class)
@AutoConfigureMockMvc(secure=false)
public class PragaControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private PragaService pragaServiceMock;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private Praga praga1;
    private Praga praga2;
    private Praga praga3;

    @Before
    public void setUpProduct() throws Exception{
        praga1 = new Praga();
        praga1.setId((long)1);
        praga1.setNome("Barata");

        praga2 = new Praga();
        praga2.setId((long)2);
        praga2.setNome("Cobra");

        praga3 = new Praga();
        praga3.setId((long)3);
        praga3.setNome("Rato");
    }

    @Test
    public void goPragas() throws Exception {

        when(pragaServiceMock.findAll()).thenReturn(Arrays.asList(praga1, praga2, praga3));

        mockMvc.perform(post("/praga/visualizar"))
                .andExpect(status().isOk())
                .andExpect(view().name("/outros/gerenciar_pragas"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pragas"))
                .andExpect(model().attribute("pragas", hasSize(3)))
                .andExpect(model().attribute("pragas", hasItem(
                        allOf(
                                hasProperty("id", is((long)1)),
                                hasProperty("nome", is("Barata"))
                        )
                )))
                .andExpect(model().attribute("pragas", hasItem(
                        allOf(
                                hasProperty("id", is((long)2)),
                                hasProperty("nome", is("Cobra"))
                        )
                )))
                .andExpect(model().attribute("pragas", hasItem(
                        allOf(
                                hasProperty("id", is((long)3)),
                                hasProperty("nome", is("Rato"))
                        )
                )))
                .andDo(print());
    }

    @Test
    public void adicionarPraga() throws Exception {
        assertThat(this.pragaServiceMock).isNotNull();
        mockMvc.perform(post("/praga/adicionar")
                .param("nome", "Aranha"))
                .andExpect(redirectedUrl("/praga/visualizar?sucesso"))
                .andDo(print());
    }

    @Test
    public void deletarPragaComFalha() throws Exception {
        assertThat(this.pragaServiceMock).isNotNull();
        when(pragaServiceMock.findById((long)1)).thenReturn(praga1);
        mockMvc.perform(post("/praga/deletar")
                .param("id", "ab"))
                .andExpect(redirectedUrl("/praga/visualizar?error_excluir"))
                .andDo(print());
    }

    @Test
    public void deletarPragaSemFalha() throws Exception {
        assertThat(this.pragaServiceMock).isNotNull();
        when(pragaServiceMock.findById((long)1)).thenReturn(praga1);
        mockMvc.perform(post("/praga/deletar")
                .param("id", "1"))
                .andExpect(redirectedUrl("/praga/visualizar?sucesso"))
                .andDo(print());
    }

    @Test
    public void alterarPragaSemFalha() throws Exception {
        assertThat(this.pragaServiceMock).isNotNull();
        when(pragaServiceMock.findById((long)1)).thenReturn(praga1);
        mockMvc.perform(post("/praga/alterar")
                .param("idAltPraga", "1")
                .param("nomePraga", "Rato"))
                .andExpect(redirectedUrl("/praga/visualizar?sucesso"))
                .andDo(print());
    }

    @Test
    public void alterarPragaComFalha() throws Exception {
        assertThat(this.pragaServiceMock).isNotNull();
        when(pragaServiceMock.findById((long)1)).thenReturn(praga1);
        mockMvc.perform(post("/praga/alterar")
                .param("idAltPraga", "ab")
                .param("nomePraga", "Rato"))
                .andExpect(redirectedUrl("/praga/visualizar?error"))
                .andDo(print());
    }
}