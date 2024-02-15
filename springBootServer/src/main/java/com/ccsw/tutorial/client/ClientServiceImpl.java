package com.ccsw.tutorial.client;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;

import jakarta.transaction.Transactional;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Client get(Long id) {

        return this.clientRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> findAll() {

        return (List<Client>) this.clientRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, ClientDto dto) {

        Client client;

        if (id == null) {
            client = new Client();
        } else {
            client = this.get(id);
        }

        client.setName(dto.getName());

        this.clientRepository.save(client);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        this.clientRepository.deleteById(id);
    }

    public Optional<Client> findOne(Long id) throws Exception {
        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        Optional<Client> client = this.clientRepository.findById(id);

        if (client.isEmpty()) {
            throw new Exception("Not find");
        }

        return client;

    }

}