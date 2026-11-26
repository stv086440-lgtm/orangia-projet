
package com.orangia.dao.interfaces;

import com.orangia.model.Client;
import java.util.List;
import java.util.Optional;

public interface ClientDAO {

    Optional<Client> findById(Long id);

    List<Client> findAll();

    List<Client> findByNom(String nom);

    List<Client> findByStatut(String statut);

    void save(Client client);

    void update(Client client);

    void deactivate(Long idClient);
}
