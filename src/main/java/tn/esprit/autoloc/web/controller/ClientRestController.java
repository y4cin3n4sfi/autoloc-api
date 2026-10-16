package tn.esprit.autoloc.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.ClientService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/client")
public class ClientRestController {

    private final ClientService clientService;

    // http://localhost:8089/autoloc/client/retrieve-all-clients
    @GetMapping("/retrieve-all-clients")
    public List<Client> getClients() {
        return clientService.retrieveAllClients();
    }

    // http://localhost:8089/autoloc/client/retrieve-client/{client-id}
    @GetMapping("/retrieve-client/{client-id}")
    public Client retrieveClient(@PathVariable("client-id") Long clientId) {
        return clientService.retrieveClient(clientId);
    }

    // http://localhost:8089/autoloc/client/add-client
    @PostMapping("/add-client")
    public Client addClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    // http://localhost:8089/autoloc/client/modify-client
    @PutMapping("/modify-client")
    public Client modifyClient(@RequestBody Client client) {
        return clientService.modifyClient(client);
    }

    // http://localhost:8089/autoloc/client/remove-client/{client-id}
    @DeleteMapping("/remove-client/{client-id}")
    public void removeClient(@PathVariable("client-id") Long clientId) {
        clientService.removeClient(clientId);
    }

    // http://localhost:8089/autoloc/client/retrieve-client-by-email?email=test@example.com
    @GetMapping("/retrieve-client-by-email")
    public Client retrieveClientByEmail(@RequestParam String email) {
        return clientService.getClientByEmail(email);
    }

    // http://localhost:8089/autoloc/client/retrieve-client-by-permis?numPermis=TN123456
    @GetMapping("/retrieve-client-by-permis")
    public Client retrieveClientByPermis(@RequestParam String numPermis) {
        return clientService.getClientByNumPermis(numPermis);
    }
}
