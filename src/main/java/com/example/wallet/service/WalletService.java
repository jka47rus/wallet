package com.example.wallet.service;

import com.example.wallet.dto.WalletRequest;
import com.example.wallet.dto.WalletResponse;
import com.example.wallet.entity.Wallet;

import java.util.List;
import java.util.UUID;

public interface WalletService {

    Wallet getBalance(UUID id);

    WalletResponse putGetMoney(WalletRequest wallet);

    List<Wallet> getAll();
}
