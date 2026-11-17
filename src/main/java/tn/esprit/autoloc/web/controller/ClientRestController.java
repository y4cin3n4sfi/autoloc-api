package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.ClientService;

import java.util.List;

@Tag(name = "Clients", description = "Gestion des clients")
@RestController
@AllArgsConstructor
@RequestMapping("/client")
public class ClientRestController {

    private final ClientService clientService;

    // http://localhost:8089/autoloc/client/retrieve-all-clients
    @Operation(summary = "Récupérer la liste de tous les clients")
    @GetMapping("/retrieve-all-clients")
    public List<Client> getClients() {
        return clientService.retrieveAllClients();
    }

    // http://localhost:8089/autoloc/client/retrieve-client/{client-id}
    @Operation(summary = "Récupérer un client par son identifiant")
    @GetMapping("/retrieve-client/{client-id}")
    public Client retrieveClient(@PathVariable("client-id") Long clientId) {
        return clientService.retrieveClient(clientId);
    }

    // http://localhost:8089/autoloc/client/add-client
    @Operation(summary = "Ajouter un nouveau client")
    @PostMapping("/add-client")
    public Client addClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    // http://localhost:8089/autoloc/client/modify-client
    @Operation(summary = "Modifier les informations d'un client existant")
    @PutMapping("/modify-client")
    public Client modifyClient(@RequestBody Client client) {
        return clientService.modifyClient(client);
    }

    // http://localhost:8089/autoloc/client/remove-client/{client-id}
    @Operation(summary = "Supprimer un client par son identifiant")
    @DeleteMapping("/remove-client/{client-id}")
    public void removeClient(@PathVariable("client-id") Long clientId) {
        clientService.removeClient(clientId);
    }

    // http://localhost:8089/autoloc/client/retrieve-client-by-email?email=test@example.com
    @Operation(summary = "Rechercher un client par son adresse email")
    @GetMapping("/retrieve-client-by-email")
    public Client retrieveClientByEmail(@RequestParam String email) {
        return clientService.getClientByEmail(email);
    }

    // http://localhost:8089/autoloc/client/retrieve-client-by-permis?numPermis=TN123456
    @Operation(summary = "Rechercher un client par son numéro de permis")
    @GetMapping("/retrieve-client-by-permis")
    public Client retrieveClientByPermis(@RequestParam String numPermis) {
        return clientService.getClientByNumPermis(numPermis);
    }
}
