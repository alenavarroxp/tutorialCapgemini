package com.ccsw.tutorial.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.config.ResponsePage;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loans.model.LoanDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class LoanIT {
    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan";

    private static final Long EXISTS_GAME_ID = 1L;
    private static final Long EXISTS_CLIENT_ID = 1L;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<ResponsePage<LoanDto>> responseTypePage = new ParameterizedTypeReference<ResponsePage<LoanDto>>() {
    };

    public static final LocalDate END_DATE = LocalDate.parse("2023-01-03");
    public static final LocalDate START_DATE = LocalDate.parse("2023-01-01");

    @Test
    public void saveCorrectLoanShouldCreateNew() {
        LoanDto loanDto = new LoanDto();
        GameDto gameDto = new GameDto();
        ClientDto clientDto = new ClientDto();

        gameDto.setId(EXISTS_GAME_ID);
        clientDto.setId(EXISTS_CLIENT_ID);

        loanDto.setGame(gameDto);
        loanDto.setClient(clientDto);
        loanDto.setStartDate(START_DATE);
        loanDto.setEndDate(END_DATE);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(loanDto), responseTypePage);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    public static final LocalDate END_DATE_BEFORE = LocalDate.parse("2023-01-04");
    public static final LocalDate START_DATE_AFTER = LocalDate.parse("2023-01-10");

    @Test
    public void saveEndBeforeStartShouldThrowException() {
        LoanDto loanDto = new LoanDto();
        GameDto gameDto = new GameDto();
        ClientDto clientDto = new ClientDto();

        gameDto.setId(EXISTS_GAME_ID);
        clientDto.setId(EXISTS_CLIENT_ID);

        loanDto.setGame(gameDto);
        loanDto.setClient(clientDto);
        loanDto.setStartDate(START_DATE_AFTER);
        loanDto.setEndDate(END_DATE_BEFORE);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(loanDto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    public static final LocalDate END_DATE_14 = LocalDate.parse("2023-01-24");
    public static final LocalDate START_DATE_14 = LocalDate.parse("2023-01-01");

    @Test
    public void saveWithMore14DaysShouldThrowException() {
        LoanDto loanDto = new LoanDto();
        GameDto gameDto = new GameDto();
        ClientDto clientDto = new ClientDto();

        gameDto.setId(EXISTS_GAME_ID);
        clientDto.setId(EXISTS_CLIENT_ID);

        loanDto.setGame(gameDto);
        loanDto.setClient(clientDto);
        loanDto.setStartDate(START_DATE_14);
        loanDto.setEndDate(END_DATE_14);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(loanDto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    public static final LocalDate END_DATE_BUSY = LocalDate.parse("2023-01-24");
    public static final LocalDate START_DATE_BUSY = LocalDate.parse("2023-01-01");
    public static final Long OTHER_GAME_ID = 3L;

    @Test
    public void save2LoansInTheseDaysShouldThrowException() {
        LoanDto loanDto = new LoanDto();
        GameDto gameDto = new GameDto();
        ClientDto clientDto = new ClientDto();

        gameDto.setId(OTHER_GAME_ID);
        clientDto.setId(EXISTS_CLIENT_ID);

        loanDto.setGame(gameDto);
        loanDto.setClient(clientDto);
        loanDto.setStartDate(START_DATE_BUSY);
        loanDto.setEndDate(END_DATE_BUSY);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(loanDto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    public static final LocalDate END_DATE_WITH_GAME = LocalDate.parse("2020-01-04");
    public static final LocalDate START_DATE_WITH_GAME = LocalDate.parse("2020-01-01");
    public static final Long CLIENT_ID_WITHOUT_GAME = 2L;

    @Test
    public void saveWithGameBorrowedShouldThrowException() {
        LoanDto loanDto = new LoanDto();
        GameDto gameDto = new GameDto();
        ClientDto clientDto = new ClientDto();

        gameDto.setId(EXISTS_GAME_ID);
        clientDto.setId(CLIENT_ID_WITHOUT_GAME);

        loanDto.setGame(gameDto);
        loanDto.setClient(clientDto);
        loanDto.setStartDate(START_DATE_WITH_GAME);
        loanDto.setEndDate(END_DATE_WITH_GAME);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(loanDto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
