package pl.mateusz.redosz.task;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RepositoryIntegrationTest {

    static WireMockServer wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());

    static {
        wireMockServer.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("github.url", wireMockServer::baseUrl);
    }

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        wireMockServer.resetAll();
    }

    @Test
    void shouldReturnRepositories() throws Exception {

        wireMockServer.stubFor(
                WireMock.get("/users/Mredosz/repos")
                        .willReturn(
                                WireMock.okJson("""
                                        [
                                          {
                                            "name": "JAZ-Project",
                                            "fork": false,
                                            "owner": {
                                              "login": "Mredosz"
                                            }
                                          }
                                        ]
                                        """)
                        )
        );

        wireMockServer.stubFor(
                WireMock.get("/repos/Mredosz/JAZ-Project/branches")
                        .willReturn(
                                WireMock.okJson("""
                                        [
                                          {
                                            "name": "main",
                                            "commit": {
                                              "sha": "123abc"
                                            }
                                          }
                                        ]
                                        """)
                        )
        );

        mockMvc.perform(get("/users/Mredosz/repositories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].RepositoryName").value("JAZ-Project"))
                .andExpect(jsonPath("$[0].OwnerLogin").value("Mredosz"))
                .andExpect(jsonPath("$[0].branches[0].name").value("main"))
                .andExpect(jsonPath("$[0].branches[0].sha").value("123abc"));
    }

    @Test
    void shouldReturn404() throws Exception {

        wireMockServer.stubFor(
                WireMock.get("/users/unknown/repos")
                        .willReturn(
                                WireMock.notFound()
                        )
        );

        mockMvc.perform(get("/users/unknown/repositories"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User repositories not found"))
                .andExpect(jsonPath("$.status").value(404));
    }
}