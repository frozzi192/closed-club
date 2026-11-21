
package com.club.closed_club;

import com.club.closed_club.entity.IMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.postgresql.PostgreSQLContainer;
import com.jayway.jsonpath.JsonPath;
import org.testcontainers.utility.DockerImageName;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.club.closed_club.entity.Member;


import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@AutoConfigureMockMvc
@Import(ClosedClubApplicationTests.PostgresTestConfiguration.class)
class ClosedClubApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private IMemberRepository memberRepository;


	@Test
	void shouldCreateMemberAndSaveToDatabase() throws Exception {

		String lastName = "Test-" +
				java.util.UUID.randomUUID().toString().substring(0, 8);

		String requestBody = """
                {
                    "firstName": "Integration",
                    "lastName": "%s",
                    "middleName": "Tester"
                }
                """.formatted(lastName);

		mockMvc.perform(post("/api/members")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstName").value("Integration"))
				.andExpect(jsonPath("$.lastName").value(lastName));

		assertTrue(memberRepository
				.findByFirstNameAndLastName("Integration", lastName)
				.isPresent());
	}

	@Test
	void shouldRejectMemberWithBlankFirstName() throws Exception {



		String requestBody = """
                {
                    "firstName": "",
                    "lastName": "Test",
                    "middleName": "Tester"
                }
                """;

		mockMvc.perform(post("/api/members")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isBadRequest());


	}

	@Test
	void shouldInvalidateQrTokenAfterUse() throws Exception {
		Member member = memberRepository.save(
				new Member("QrTest", "OneTime", "Tester")
		);

		String response = mockMvc.perform(
						post("/api/qr-codes/{memberId}", member.getId())
				)
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		String qrToken = JsonPath.read(response, "$.qrToken");

		mockMvc.perform(post("/api/qr-codes/use/{qrToken}", qrToken))
				.andExpect(status().isOk());

		mockMvc.perform(post("/api/qr-codes/use/{qrToken}", qrToken))
				.andExpect(status().isNotFound());
	}


	@Test
	void shouldGetMemberById() throws Exception {

		String lastName = "Test-" +
				UUID.randomUUID().toString().substring(0, 8);

		Member member = new Member(
				"Integration",
				lastName,
				"Tester"
		);

		Member savedMember = memberRepository.save(member);

		mockMvc.perform(get("/api/members/{id}", savedMember.getId()))

				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(savedMember.getId()))
				.andExpect(jsonPath("$.firstName").value("Integration"))
				.andExpect(jsonPath("$.lastName").value(lastName));
	}
	@Test
	void shouldFilterMembersAndApplyPagination() throws Exception {
		String prefix = "Page-" + UUID.randomUUID().toString().substring(0, 8);

		memberRepository.save(new Member("First", prefix + "-A", "Test"));
		memberRepository.save(new Member("Second", prefix + "-B", "Test"));
		memberRepository.save(new Member("Other", "Unrelated", "Test"));

		mockMvc.perform(get("/api/members")
						.param("lastName", prefix)
						.param("page", "0")
						.param("size", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalElements").value(2))
				.andExpect(jsonPath("$.size").value(1))
				.andExpect(jsonPath("$.numberOfElements").value(1));
	}

	@Test
	void shouldReturnNotFoundWhenMemberDoesNotExist() throws Exception {

		mockMvc.perform(get("/api/members/{id}", Long.MAX_VALUE))
				.andExpect(status().isNotFound());
	}
	@Test
	void shouldCreateQrCodeForExistingMember() throws Exception {
		Member member = memberRepository.save(
				new Member("QrTest", "Integration", "Tester")
		);

		mockMvc.perform(post("/api/qr-codes/{memberId}", member.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.qrToken").exists());
	}


	@TestConfiguration(proxyBeanMethods = false)
	static class PostgresTestConfiguration {

		@Bean
		@ServiceConnection
		PostgreSQLContainer postgresContainer() {
			return new PostgreSQLContainer(
					DockerImageName.parse("postgres:16-alpine"));
		}
	}

}
