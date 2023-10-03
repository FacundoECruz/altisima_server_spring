package com.facu.altisima.service.impl;

import com.facu.altisima.repository.PlayerRepository;
import com.facu.altisima.model.Player;
import com.facu.altisima.service.api.PlayerServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerServiceAPI {

    @Autowired
    private PlayerRepository playerRepository;

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

    //EDITAR PLAYER PARA AGREGARLE DATA DE LA PARTIDA QUE JUGO
}
