package com.facu.altisima.service.impl;

import com.facu.altisima.model.User;
import com.facu.altisima.repository.PlayerRepository;
import com.facu.altisima.model.Player;
import com.facu.altisima.repository.UserRepository;
import com.facu.altisima.service.api.PlayerServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerServiceImpl implements PlayerServiceAPI {

    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    public PlayerServiceImpl(PlayerRepository playerRepository, UserRepository userRepository) {
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
    }
    @Override
    public ServiceResult<List<Player>> getAll() {
        List<Player> allPlayers = playerRepository.findAll();

        if (allPlayers.size() != 0) {
            return ServiceResult.success(allPlayers);
        } else {
            return ServiceResult.error("No se pudieron recuperar los jugadores");
        }
    }

    @Override
    public ServiceResult<List<Player>> getUnregistered(){
        List<Player> allPlayers = playerRepository.findAll();
        List<User> allUsers = userRepository.findAll();

        List<Player> filteredPlayers = getFilteredPlayers(allPlayers, allUsers);
        return ServiceResult.success(filteredPlayers);
    }

    @NotNull
    private static List<Player> getFilteredPlayers(List<Player> allPlayers, List<User> allUsers) {
        Set<String> userUsernames = new HashSet<>();
        for (User user : allUsers) {
            userUsernames.add(user.getUsername());
        }
        List<Player> filteredPlayers = new ArrayList<>();
        for (Player player : allPlayers) {
            if (!userUsernames.contains(player.getUsername())) {
                filteredPlayers.add(player);
            }
        }
        return filteredPlayers;
    }

    @Override
    public ServiceResult<Player> save(Player player) {
        Optional<Player> playerInDb = playerRepository.findByUsername(player.getUsername());
        if (playerInDb.isPresent()) {
            return ServiceResult.error("El nombre de usuario ya existe");
        } else {
            Player savedPlayer = playerRepository.save(player);
            return ServiceResult.success(savedPlayer);
        }
    }

    @Override
    public ServiceResult<Player> get(String username) {
        Optional<Player> player = playerRepository.findByUsername(username);
        if(player != null && player.isPresent()) {
            return ServiceResult.success(player.get());
        } else {
            return ServiceResult.error("El nombre de usuario no existe");
        }
    }

    @Override
    public ServiceResult<List<Player>> getAssociatedPlayers(String username){
        List<Player> allAssociatedPlayers = playerRepository.findAll();
        List<User> allUsers = userRepository.findAll();
        filterPlayers(allAssociatedPlayers, username);
        List<Player> associatedPlayers = getFilteredPlayers(allAssociatedPlayers, allUsers);
        return ServiceResult.success(associatedPlayers);
    }

    private static void filterPlayers(List<Player> players, String createdBy) {
        players.removeIf(player -> !player.getCreatedBy().equals(createdBy));
    }
}
