package com.orangia.dao.interfaces;

import com.orangia.model.Client;
import java.util.List;
import java.util.Optional;

public interface ClientDAO {

    Optional<Client> findById(Long idClient);

    List<Client> findAll();

    List<Client> findByNom(String nom);

    List<Client> findByStatut(String statut);

    Client save(Client client);

    void update(Client client);

    void deactivate(Long idClient);
}
