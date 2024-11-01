package com.example.wallet.controller;

import com.example.wallet.AbstractTestController;
import com.example.wallet.StringTestUtils;
import com.example.wallet.dto.Type;
import com.example.wallet.dto.WalletRequest;
import com.example.wallet.dto.WalletResponse;
import com.example.wallet.entity.Wallet;
import com.example.wallet.service.WalletService;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WalletControllerTest extends AbstractTestController {

    @MockBean
    private WalletService walletService;

    @Test
    public void whenFindAllThenReturnAllWallets() throws Exception {

        List<Wallet> wallets = new ArrayList<>();
        wallets.add(createWallet(1, BigDecimal.valueOf(50000)));
        wallets.add(createWallet(2, BigDecimal.valueOf(70000)));

        Mockito.when(walletService.getAll()).thenReturn(wallets);

        String actualResponse = mockMvc.perform(get("/api/v1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/find_all_wallets_response.json");

        Mockito.verify(walletService, Mockito.times(1)).getAll();

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void getBalance() throws Exception {
        Wallet wallet = createWallet(1, BigDecimal.valueOf(50000));
        Mockito.when(walletService.getBalance(UUID.fromString("8d57b660-835b-4cf7-9c6b-0bdc3b1627e1")))
                .thenReturn(wallet);

        String actualResponse = mockMvc.perform(get("/api/v1/wallets/8d57b660-835b-4cf7-9c6b-0bdc3b1627e1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/get_balance_of_wallet.json");

        Mockito.verify(walletService, Mockito.times(1)).getBalance(UUID.fromString("8d57b660-835b-4cf7-9c6b-0bdc3b1627e1"));

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void depositMoneyOnBalance() throws Exception {

        WalletRequest walletRequest = createWalletRequest(UUID.fromString("8d57b660-835b-4cf7-9c6b-0bdc3b1627e1"), Type.DEPOSIT, BigDecimal.valueOf(20000));
        WalletResponse walletResponse = createWalletResponse("Operation complete", UUID.fromString("8d57b660-835b-4cf7-9c6b-0bdc3b1627e1"), BigDecimal.valueOf(70000));

        Mockito.when(walletService.putGetMoney(walletRequest))
                .thenReturn(walletResponse);

        String actualResponse = mockMvc.perform(post("/api/v1/wallet").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(walletRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/get_wallet_with_deposit_balance.json");

        Mockito.verify(walletService, Mockito.times(1)).putGetMoney(walletRequest);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void withdrawMoneyFromBalance() throws Exception {

        WalletRequest walletRequest = createWalletRequest(UUID.fromString("8d57b660-835b-4cf7-9c6b-0bdc3b1627e1"), Type.WITHDRAW, BigDecimal.valueOf(20000));
        WalletResponse walletResponse = createWalletResponse("Operation complete", UUID.fromString("8d57b660-835b-4cf7-9c6b-0bdc3b1627e1"), BigDecimal.valueOf(30000));

        Mockito.when(walletService.putGetMoney(walletRequest))
                .thenReturn(walletResponse);

        String actualResponse = mockMvc.perform(post("/api/v1/wallet").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(walletRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/get_wallet_with_withdraw_balance.json");

        Mockito.verify(walletService, Mockito.times(1)).putGetMoney(walletRequest);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void notEnoughMoneyForWithdraw() throws Exception {

        WalletRequest walletRequest = createWalletRequest(UUID.fromString("8d57b660-835b-4cf7-9c6b-0bdc3b1627e1"), Type.WITHDRAW, BigDecimal.valueOf(800000));
        WalletResponse walletResponse = createWalletResponse("Not enough money on the account");

        Mockito.when(walletService.putGetMoney(walletRequest))
                .thenReturn(walletResponse);

        String actualResponse = mockMvc.perform(post("/api/v1/wallet").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(walletRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/not_enough_money.json");

        Mockito.verify(walletService, Mockito.times(1)).putGetMoney(walletRequest);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void wrongIdForDepositWithdraw() throws Exception {

        WalletRequest walletRequest = createWalletRequest(UUID.fromString("8d57b660-835b-4cf7-9c6b-0bdc3b1627e7"), Type.WITHDRAW, BigDecimal.valueOf(800000));
        WalletResponse walletResponse = createWalletResponse("Account with id: 8d57b660-835b-4cf7-9c6b-0bdc3b1627e7 doesn't exist");

        Mockito.when(walletService.putGetMoney(walletRequest))
                .thenReturn(walletResponse);

        String actualResponse = mockMvc.perform(post("/api/v1/wallet").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(walletRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/wrong_id.json");

        Mockito.verify(walletService, Mockito.times(1)).putGetMoney(walletRequest);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

}
