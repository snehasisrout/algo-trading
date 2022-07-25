package org.trade;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Margin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.trade.constants.TradeConstants;
import org.trade.utils.KiteConnectApp;

@SpringBootApplication
@Slf4j
public class AlgoTradingApplication {

    public static void main(String[] args) {
//		SpringApplication.run(AlgoTradingApplication.class, args);
        String userName = "USERNAME";
        String password = "PASSWORD";
        String totpKey = "TOTP KEY";
        String apiKey = "API KEY";
        String apiSecret = "API SECRET";

        try {

			KiteConnect kiteSdk = KiteConnectApp.getKiteConnect(userName, password, totpKey, apiKey, apiSecret);
			Margin margins = kiteSdk.getMargins("equity");
            log.info("Available cash Rs: {}{}",new String(TradeConstants.UTF8_BYTES,TradeConstants.UTF8),margins.available.cash);

        } catch (KiteException | Exception e) {
			log.error("Error occurred , Error:{}",e.getMessage());
            e.printStackTrace();
        }

    }

}
