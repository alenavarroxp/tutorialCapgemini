package com.ccsw.tutorial.client;

import java.util.List;
import java.util.Optional;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;

/**
 * @author ccsw
 *
 */
public interface ClientService {

    /**
     * Recupera una {@link Client} a partir de su ID
     *
     * @param id PK de la entidad
     * @return {@link Client}
     */
    Client get(Long id);

    /**
     * Método para recuperar todas las {@link Client}
     *
     * @return {@link List} de {@link Client}
     */
    List<Client> findAll();

    /**
     * Método para crear o actualizar una {@link Client}
     *
     * @param id  PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, ClientDto dto);

    /**
     * Método para borrar una {@link Client}
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;

    Optional<Client> findOne(Long id) throws Exception;

}