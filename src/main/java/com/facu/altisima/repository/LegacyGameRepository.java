package com.facu.altisima.repository;

import com.facu.altisima.model.Game;
import com.facu.altisima.repository.dto.GameDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LegacyGameRepository implements GameRepository {

    MongoLegacyGameRepository mongoLegacyGameRepository;

    public LegacyGameRepository(MongoLegacyGameRepository mongoLegacyGameRepository) {
        this.mongoLegacyGameRepository = mongoLegacyGameRepository;
    }

    @Override
    public List<Game> findAll() {
        List<GameDto> gamesDto = mongoLegacyGameRepository.findAll();
        List<GameDto> filteredDtos = gamesDto.stream().filter(gameDto -> gameDto.getResults().size() > 0).collect(Collectors.toList());
        return filteredDtos.stream().map((gameDto) ->
                gameDto.toDomain()
        ).collect(Collectors.toList());
    }

    @Override
    public Game save(Game game) {
        GameDto gameDto = GameDto.from(game);
        mongoLegacyGameRepository.save(gameDto);
        return game;
    }

    @Override
    public Optional<Game> findById(String id) {
        Optional<GameDto> dtoOptional = mongoLegacyGameRepository.findById(id);
        if (dtoOptional.isPresent()) {
            Game game = dtoOptional.get().toDomain();
            return Optional.of(game);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(String id) {

    }
}
