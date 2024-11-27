package br.com.fiap.soat07.techchallenge.cozinha;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Utils {

    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

}