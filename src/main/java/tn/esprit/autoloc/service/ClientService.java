package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Client;

import java.util.List;

public interface ClientService {

    Client saveClient(Client client);

    Client updateClient(Long idClient, Client client);

    Client getClientById(Long idClient);

    List<Client> getAllClients();

    void deleteClient(Long idClient);

    Client getClientByEmail(String email);

    Client getClientByNumPermis(String numPermis);
}
