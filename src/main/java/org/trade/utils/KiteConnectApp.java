package org.trade.utils;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.SessionExpiryHook;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Margin;
import com.zerodhatech.models.User;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class KiteConnectApp {

    public static KiteConnect getKiteConnect(String userName, String password, String totpKey, String apiKey, String apiSecret) {
        String requestToken = KiteLoginApp.getRequestToken(userName, password, totpKey);
        if (Objects.isNull(requestToken))
            log.info("Please check the internet connection, it seems slow..");
        log.info("Request token received: {}", requestToken);
        KiteConnect kiteSdk = new KiteConnect(apiKey);
        kiteSdk.setUserId(userName);
        String url = kiteSdk.getLoginURL();
        log.info("URL to be hit: {}", url);
        User user = null;
        try {
            user = kiteSdk.generateSession(requestToken, apiSecret);
            Thread.sleep(200);
            log.info("Access token received: {}", user.accessToken);
            log.info("Public token received: {}", user.publicToken);
            kiteSdk.setAccessToken(user.accessToken);
            kiteSdk.setPublicToken(user.publicToken);

        } catch (KiteException | IOException | InterruptedException e) {
            log.error("Error occurred, Error:" + e.getMessage());
            e.printStackTrace();
        }

        kiteSdk.setSessionExpiryHook(() -> log.info("Kite connect session expired"));
        return kiteSdk;
    }
}
