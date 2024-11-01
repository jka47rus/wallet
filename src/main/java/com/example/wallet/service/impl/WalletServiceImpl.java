package com.example.wallet.service.impl;

import com.example.wallet.dto.Type;
import com.example.wallet.dto.WalletRequest;
import com.example.wallet.dto.WalletResponse;
import com.example.wallet.entity.Wallet;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.service.WalletService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public Wallet getBalance(UUID id) {

        return walletRepository.findById(id).get();
    }

    @Override
    public WalletResponse putGetMoney(WalletRequest wallet) {
        WalletResponse walletResponse = new WalletResponse();

        Optional<Wallet> optionalWallet = walletRepository.findById(wallet.getWalletId());
        if (optionalWallet.isEmpty()) {

            walletResponse.setMessage("Account with id: " + wallet.getWalletId() + " doesn't exist");
            return walletResponse;
        }
        Wallet getWallet = optionalWallet.get();

        if (wallet.getOperationType().equals(Type.DEPOSIT)) {
            getWallet.setAmount(getWallet.getAmount().add(wallet.getAmount()));
        }

        if (wallet.getOperationType().equals(Type.WITHDRAW)) {
            if (wallet.getAmount().compareTo(getWallet.getAmount()) > 0) {
                walletResponse.setMessage("Not enough money on the account");
                return walletResponse;
            }
            getWallet.setAmount(getWallet.getAmount().subtract(wallet.getAmount()));
        }
        walletRepository.save(getWallet);
        walletResponse.setMessage("Operation complete");
        walletResponse.setId(getWallet.getId());
        walletResponse.setAmount(getWallet.getAmount());

        return walletResponse;

    }

    @Override
    public List<Wallet> getAll() {

        return walletRepository.findAll();
    }
}
