package com.facu.altisima.service.api;

import com.facu.altisima.model.PlayerData;
import com.facu.altisima.model.User;
import com.facu.altisima.service.utils.ServiceResult;

import java.util.List;

public interface DataServiceAPI {
    ServiceResult<PlayerData> get(String username);

    ServiceResult<List<PlayerData>> getAll();

    ServiceResult<String> update();
}
