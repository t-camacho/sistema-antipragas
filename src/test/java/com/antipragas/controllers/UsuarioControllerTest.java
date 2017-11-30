package com.antipragas.controllers;

import com.antipragas.models.Endereco;
import com.antipragas.models.Telefone;
import com.antipragas.models.Usuario;
import com.antipragas.models.enums.Nivel;
import com.antipragas.models.enums.Sexo;
import com.antipragas.models.enums.Status;
import com.antipragas.services.UsuarioService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
 * @author Ricardo Henrique Brunetto
 * 30 de Novembro de 2017
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PragaController.class)
@AutoConfigureMockMvc(secure=false)
public class UsuarioControllerTest {


    private Usuario usr_cliente, usr_funcionario, usr_admin;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UsuarioService usuarioServiceMock;

    @Autowired
    private UsuarioController usuarioController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Before
    public void setUpProduct() throws Exception {
        usr_cliente = new Usuario();
        usr_cliente.setId((long)0);
        usr_cliente.setNome("Pedro de Alcântara");
        usr_cliente.setStatus(Status.STATUS_ATIVADA);
        usr_cliente.setNivel(Nivel.NIVEL_CLIENTE);
        usr_cliente.setSexo(Sexo.M);
        usr_cliente.setCPF("97536063709");
        Set<Endereco> enderecos_cliente = new HashSet<Endereco>();
        enderecos_cliente.add(new Endereco("784555-000", "Rua da Macedonia", "Bairro NotTeached", "Cidade Nova", 1, "", "MT"));
        usr_cliente.setEnderecos(enderecos_cliente);
        usr_cliente.setSenha(new BCryptPasswordEncoder().encode("senha123"));
        usr_cliente.setEmail("email01@email.com");
        usr_cliente.setTelefone(new Telefone("9489489489", "48949849494"));

//
//        usr_funcionario = new Usuario();
//        usr_funcionario.setId((long)1);
//        usr_funcionario.setNome("Bibiana Francisca Xavier de Paula");
//        usr_funcionario.setStatus(Status.STATUS_ATIVADA);
//        usr_funcionario.setNivel(Nivel.NIVEL_FUNCIONARIO);
//        usr_funcionario.setSexo(Sexo.F);
//        usr_funcionario.setCPF("08971120673");
//        Set enderecos_func = new HashSet<Endereco>();
//        enderecos_func.add(new Endereco("78425-022", "", "Desertado", "Questo", 7, "D", "ES"));
//        usr_funcionario.setEnderecos(enderecos_func);
//        usr_funcionario.setSenha(new BCryptPasswordEncoder().encode("senha123"));
//        usr_funcionario.setEmail("email02@email.com");
//        usr_funcionario.setTelefone(new Telefone("77777777777", "7777777777"));
//
//        usr_admin = new Usuario();
//        usr_admin.setId((long)3);
//        usr_admin.setNome(" João Carlos Leopoldo");
//        usr_admin.setStatus(Status.STATUS_ATIVADA);
//        usr_admin.setNivel(Nivel.NIVEL_ADMINISTRADOR);
//        usr_admin.setSexo(Sexo.M);
//        usr_admin.setCPF("46838395711");
//        Set enderecos_admin = new HashSet<Endereco>();
//        enderecos_admin.add(new Endereco("754122-022", "", "Desnecessario", "Tecnolandia", 93, "Apto 90", "PE"));
//        usr_admin.setEnderecos(enderecos_admin);
//        usr_admin.setSenha(new BCryptPasswordEncoder().encode("senha123"));
//        usr_admin.setEmail("email03@email.com");
//        usr_admin.setTelefone(new Telefone("77712312377", "71231237"));
    }

    @Test
    public void usethis() throws Exception{
        StringBuilder strb = new StringBuilder();
        for(Endereco e : usr_cliente.getEnderecos()){
            strb.append(e.getCep());
            strb.append("/");
            strb.append(e.getRua());
            strb.append("/");
            strb.append(e.getBairro());
            strb.append("/");
            strb.append(e.getCidade());
            strb.append("/");
            strb.append(e.getNumero());
            if(!e.getComplemento().isEmpty()){
                strb.append("/");
                strb.append(e.getComplemento());
            }
            strb.append("/");
            strb.append(e.getUf());
        }
        mockMvc.perform(post("/usuario/registrar")
                .param("nome", usr_cliente.getNome())
                .param("email",  usr_cliente.getEmail())
                .param("dnascimento",  usr_cliente.getDataDeNascimento())
                .param("cpf",  usr_cliente.getCPF())
                .param("sexo",  usr_cliente.getSexo().getSexo())
                .param("senha", usr_cliente.getSenha())
                .param("telefone",  usr_cliente.getTelefone().getTelefone())
                .param("cell",  usr_cliente.getTelefone().getCelular())
                .param("endereco",  strb.toString())
                .param("nivel",  usr_cliente.getNivel().getNivel()))
                .andExpect(redirectedUrl("/usuario/registrar"))
                .andDo(print());
    }
}
