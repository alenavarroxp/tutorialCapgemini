package com.ccsw.tutorial.loans.model;

import java.time.LocalDate;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.GameDto;

public class LoanDto {
    private Long id;

    private GameDto game;

    private ClientDto client;

    private LocalDate startDate;

    private LocalDate endDate;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the game
     */
    public GameDto getGame() {
        return game;
    }

    /**
     * @param game the game to set
     */
    public void setGame(GameDto game) {
        this.game = game;
    }

    /**
     * @return the client
     */
    public ClientDto getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(ClientDto client) {
        this.client = client;
    }

    /**
     * @return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}
