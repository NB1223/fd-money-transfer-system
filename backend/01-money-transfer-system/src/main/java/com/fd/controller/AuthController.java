package com.fd.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fd.dto.AdminResponse;
import com.fd.dto.AuthRequest;
import com.fd.dto.LoginResponseDTO;
import com.fd.exception.DuplicateUsernameException;
import com.fd.model.Account;
import com.fd.model.AccountStatus;
import com.fd.model.UserEntity;
import com.fd.repository.IAccountRepository;
import com.fd.repository.UserRepository;
import com.fd.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final UserRepository userRepository;
    private final IAccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          IAccountRepository accountRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public long register(@RequestBody AuthRequest request) {

        // UserEntity user = new UserEntity();
        // user.setUsername(request.getUsername());
        // user.setPassword(passwordEncoder.encode(request.getPassword()));

        // userRepository.save(user);
        // Account account = new Account(request.getUsername(), 1000.0); // Initial balance
        // account.setUser(user);

        // accountRepository.save(account);

        // return account.getAccountId();
         try {
            UserEntity user = new UserEntity();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            userRepository.save(user);
            Optional<Account> existingAccount = accountRepository.findByHolderName(request.getUsername());
        
            Account account;
            if (existingAccount.isPresent()) {
                // Use existing account and set the user
                account = existingAccount.get();
                account.setUser(user);
                account.setAccountStatus(AccountStatus.ACTIVE); // Set account status to ACTIVE
            } else {
                // Create new account if it doesn't exist
                // account = new Account(request.getUsername(), 5000.0); // Initial balance
                // account.setUser(user);
                 throw new RuntimeException("Account not created. Please contact admin.");
            }

            accountRepository.save(account);

            return account.getAccountId();
        } catch (DataIntegrityViolationException e) {
            // Check if the error is related to duplicate username
            if (e.getMessage().contains("Duplicate") || e.getMessage().contains("UK_gf144p6ms89434vl96d7xiwwh")) {
                throw new DuplicateUsernameException("Username already exists. Please choose a different username.");
            }
            throw e;
        }
    }

    @PostMapping("admin/register")
    public long registerAdmin(@RequestBody AuthRequest request) {

        
         try {
            UserEntity user = new UserEntity();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole("ADMIN");

            userRepository.save(user);
            return user.getId();
        } catch (DataIntegrityViolationException e) {
            // Check if the error is related to duplicate username
            if (e.getMessage().contains("Duplicate") || e.getMessage().contains("UK_gf144p6ms89434vl96d7xiwwh")) {
                throw new DuplicateUsernameException("Username already exists. Please choose a different username.");
            }
            throw e;
        }
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword())
        );

        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(request.getUsername());
        // return Map.of("token", token);
        return new LoginResponseDTO(user.getAccount().getAccountId(), request.getUsername(), token);
    }

    @PostMapping("admin/login")
    public AdminResponse loginAdmin(@RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword())
        );

        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(request.getUsername());
        // return Map.of("token", token);
        return new AdminResponse(user.getId(), request.getUsername(), token);
    }

}