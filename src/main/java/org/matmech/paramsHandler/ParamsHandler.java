package org.matmech.paramsHandler;

import org.matmech.cache.Cache;

import java.util.HashMap;

public class ParamsHandler {
    private final Cache cache;

    private String testing() {
        return "Value testing";
    }

    public ParamsHandler(Cache cache) {
        this.cache = cache;
    }

    public String handler(long chatId) {
        HashMap<String, String> params = cache.getParams(chatId);

        return switch (params.get("processName")) {
            case "testing" -> testing();
            default -> throw new IllegalArgumentException("Нет такого процесса");
        };
    }
}
