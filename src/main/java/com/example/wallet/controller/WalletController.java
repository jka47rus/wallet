package com.example.wallet.controller;

import com.example.wallet.dto.WalletRequest;
import com.example.wallet.dto.WalletResponse;
import com.example.wallet.entity.Wallet;
import com.example.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/wallet")
    public ResponseEntity<WalletResponse> getPutMoney(@RequestBody WalletRequest request) {

        return ResponseEntity.ok(walletService.putGetMoney(request));
    }

    @GetMapping("/wallets/{WALLET_UUID}")
    public ResponseEntity<Wallet> getBalance(@PathVariable UUID WALLET_UUID) {

        return ResponseEntity.ok(walletService.getBalance(WALLET_UUID));
    }

    @GetMapping()
    public ResponseEntity<List<Wallet>> getAllWallets() {

        return ResponseEntity.ok(walletService.getAll());
    }


}
