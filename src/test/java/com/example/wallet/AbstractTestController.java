package com.example.wallet;

import com.example.wallet.dto.Type;
import com.example.wallet.dto.WalletRequest;
import com.example.wallet.dto.WalletResponse;
import com.example.wallet.entity.Wallet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractTestController {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    protected Wallet createWallet(int id, BigDecimal sum) {
        Wallet wallet = new Wallet();
        wallet.setId(UUID.fromString("8d57b660-835b-4cf7-9c6b-0bdc3b1627e" + id));
        wallet.setAmount(sum);
        return wallet;
    }

    protected WalletRequest createWalletRequest(UUID id, Type type, BigDecimal sum) {
        WalletRequest walletRequest = new WalletRequest();
        walletRequest.setWalletId(id);
        walletRequest.setOperationType(type);
        walletRequest.setAmount(sum);
        return walletRequest;
    }

    protected WalletResponse createWalletResponse(String message, UUID id, BigDecimal sum) {
        WalletResponse walletResponse = new WalletResponse();
        walletResponse.setMessage(message);
        walletResponse.setId(id);
        walletResponse.setAmount(sum);

        return walletResponse;
    }

    protected WalletResponse createWalletResponse(String message) {
        WalletResponse walletResponse = new WalletResponse();
        walletResponse.setMessage(message);

        return walletResponse;
    }

}
