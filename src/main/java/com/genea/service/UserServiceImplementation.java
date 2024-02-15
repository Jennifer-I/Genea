package com.genea.service;

import com.genea.dto.*;
import com.genea.email.EmailService;
import com.genea.entity.User;
import com.genea.entity.UserAccountToken;
import com.genea.enums.Role;
import com.genea.enums.TokenType;
import com.genea.exception.UserNotFoundException;
import com.genea.repository.TokenRepository;
import com.genea.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service

public class UserServiceImplementation implements UserService {

    private  final String sender;

    private  final String adminEmail;

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;

    public UserServiceImplementation(@Value("${spring.mail.username}") String sender, @Value("${admin_email}") String adminEmail, UserRepository userRepository, EmailService emailService, TokenRepository tokenRepository) {
        this.sender = sender;
        this.adminEmail = adminEmail;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
    }



    private User createUserFromRequest(RegistrationRequestDto request) {
        return User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .fullName(request.getFirstName() + " " + request.getLastName())
                .password(request.getPassword())
                .isVerified(false)
                .isActive(false)
                .role(Role.CUSTOMER)
                .build();

    }

    private UserAccountToken createVerificationToken(Long userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return UserAccountToken.builder()
                    .user(user)
                    .createdAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusMinutes(3))
                    .confirmedAt(null)
                    .confirmationToken(UUID.randomUUID().toString())
                    .build();
        } else {
            throw new UserNotFoundException("User not found");
        }
    }


    public ApiResponse<UserResponseDto> createCustomer(RegistrationRequestDto request) throws InterruptedException, UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "User already exists", null, HttpStatus.BAD_REQUEST);
        }
        User newUser = createUserFromRequest(request);
        userRepository.save(newUser);

        UserAccountToken userAccountToken = createVerificationToken(newUser.getId());
        userAccountToken.setTokenType(TokenType.VERIFICATION_TOKEN);
        tokenRepository.save(userAccountToken);

        sendVerificationEmail(request, userAccountToken.getConfirmationToken());

        return new ApiResponse<>(HttpStatus.OK.value(), newUser.getFirstName() + " welcome to Genea! Please check your email to confirm your account");
    }


    private void sendVerificationEmail(RegistrationRequestDto registrationRequest, String confirmationToken) throws InterruptedException {

        String emailContent = "Thank you for registering with Genea. Please click on the link below to verify your account \n"
                + "http://localhost:1999/api/v1/auth/verifyEmail?confirmationToken=" + confirmationToken;
        EmailDetailsRequest emailDetailsRequest = EmailDetailsRequest.builder()
                .mailFrom(sender)
                .mailTo(registrationRequest.getEmail())
                .subject("Genea Stores Account Verification")
                .text(emailContent)
                .build();
        emailService.sendEmailAsync(emailDetailsRequest);

    }


    @Override
    public ApiResponse<String> verifyEmail(String confirmationToken) {
        Optional<UserAccountToken> optionalVerificationToken = tokenRepository.findByConfirmationToken(confirmationToken);
        if (optionalVerificationToken.isPresent()) {
            UserAccountToken userAccountToken = optionalVerificationToken.get();
            if (isTokenExpired(userAccountToken)) {
                return new ApiResponse<>(HttpStatus.OK.value(), "token has expired", null, HttpStatus.BAD_REQUEST);
            }
            if (userAccountToken.getConfirmedAt() != null) {
                return new ApiResponse<>(HttpStatus.OK.value(), "Email already verified", null, HttpStatus.BAD_REQUEST);
            }
            Optional<User> userOptional = userRepository.findByEmail(userAccountToken.getUser().getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                log.info("User retrieved: {}", user);
                user.setIsVerified(true);
                user.setIsVerified(true);
                userRepository.save(user);
                userAccountToken.setConfirmedAt(LocalDateTime.now());
                tokenRepository.save(userAccountToken);

                return new ApiResponse<>(HttpStatus.OK.value(), "OTP verified successfully", null, HttpStatus.OK);
            } else {
                return new ApiResponse<>(HttpStatus.OK.value(), "User not in database", null, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ApiResponse<>(HttpStatus.OK.value(), "The provided confirmation token is invalid or expired!", null, HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isTokenExpired(UserAccountToken userAccountToken) {
        return userAccountToken.getExpiresAt().isBefore(LocalDateTime.now());
    }


    @Override
    public ApiResponse<String> resendVerificationToken(String email) throws InterruptedException {
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            Optional<UserAccountToken> optionalToken = tokenRepository.findByUserId(user.getId());
            UserAccountToken userAccountToken;
            if (optionalToken.isPresent()) {
                userAccountToken = optionalToken.get();
                userAccountToken.setConfirmationToken(UUID.randomUUID().toString());
                userAccountToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
                tokenRepository.save(userAccountToken);
                try {
                    sendVerificationEmail(RegistrationRequestDto.builder().email(email).build(), userAccountToken.getConfirmationToken());
                } catch (InterruptedException e) {
                    return new ApiResponse<>(HttpStatus.OK.value(), "Failed to send email", null, HttpStatus.BAD_REQUEST);
                }
                return new ApiResponse<>(HttpStatus.OK.value(), "Verification token sent successfully", null, HttpStatus.OK);
            }
            return new ApiResponse<>(HttpStatus.OK.value(), "Please  check your email to verify your account", null, HttpStatus.OK);

        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    private void sendPasswordResetEmail(ForgotPasswordRequest forgotPasswordRequest, String confirmationToken) throws InterruptedException {
        String emailContent = "Hello," + "\n" + "Please use the onetime passCode to reset your password\n" + confirmationToken;
        EmailDetailsRequest emailDetailsRequest = EmailDetailsRequest.builder()
                .mailFrom(sender)
                .mailTo(forgotPasswordRequest.getEmail())
                .subject("Genea Stores Password Reset")
                .text(emailContent)
                .build();
        emailService.sendEmailAsync(emailDetailsRequest);

    }

    @Override
    public ApiResponse<String> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws InterruptedException {
        Optional<User> user = userRepository.findByEmail(forgotPasswordRequest.getEmail());
        if (user.isPresent()) {
            UserAccountToken userAccountToken = createVerificationToken(user.get().getId());
            userAccountToken.setTokenType(TokenType.PASSWORD_RESET_TOKEN);
            tokenRepository.save(userAccountToken);
            sendPasswordResetEmail(ForgotPasswordRequest.builder()
                            .email(forgotPasswordRequest.getEmail())
                            .build(),
                    userAccountToken.getConfirmationToken());
            return new ApiResponse<>(HttpStatus.OK.value(), "Password reset link sent to your email", null, HttpStatus.OK);
        }
        return new ApiResponse<>(HttpStatus.OK.value(), "User not found", null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ApiResponse<String> resetPassword(String confirmationToken, ResetPasswordRequest resetPasswordRequest) {
        Optional<UserAccountToken> passwordResetToken = tokenRepository.findByConfirmationToken(confirmationToken);
        if (passwordResetToken.isPresent()) {
            UserAccountToken userAccountToken = passwordResetToken.get();
            if (isTokenExpired(userAccountToken)) {
                return new ApiResponse<>(HttpStatus.OK.value(), "token has expired", null, HttpStatus.BAD_REQUEST);
            }
            if (userAccountToken.getConfirmedAt() != null) {
                return new ApiResponse<>(HttpStatus.OK.value(), "Token is invalid", null, HttpStatus.BAD_REQUEST);
            }
            Optional<User> userOptional = userRepository.findByEmail(userAccountToken.getUser().getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setPassword(resetPasswordRequest.getPassword());
                userRepository.save(user);
                userAccountToken.setConfirmedAt(LocalDateTime.now());
                tokenRepository.save(userAccountToken);
                return new ApiResponse<>(HttpStatus.OK.value(), "Password reset successfully", null, HttpStatus.OK);
            } else {
                return new ApiResponse<>(HttpStatus.OK.value(), "User not in database", null, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ApiResponse<>(HttpStatus.OK.value(), "The provided confirmation token is invalid or expired!", null, HttpStatus.BAD_REQUEST);
        }


    }

    @Override
    public ApiResponse<String> createAdmin(RegistrationRequestDto registrationRequestDto) throws InterruptedException {
        Optional<User> optionalUser = userRepository.findByEmail(registrationRequestDto.getEmail());
        if (optionalUser.isPresent()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "User already exists", null, HttpStatus.BAD_REQUEST);
        }
        User admin = User.builder()
                .firstName(registrationRequestDto.getFirstName())
                .lastName(registrationRequestDto.getLastName())
                .email(registrationRequestDto.getEmail())
                .role(Role.ADMIN)
                .password(registrationRequestDto.getPassword())
                .fullName(registrationRequestDto.getFirstName() + " " + registrationRequestDto.getLastName())
                .build();
        if (registrationRequestDto.getEmail().equals(adminEmail)) {
            userRepository.save(admin);
            UserAccountToken userAccountToken = createVerificationToken(admin.getId());
            userAccountToken.setTokenType(TokenType.VERIFICATION_TOKEN);
            tokenRepository.save(userAccountToken);

            sendVerificationEmail(registrationRequestDto, userAccountToken.getConfirmationToken());

            return new ApiResponse<>(HttpStatus.OK.value(), "Admin created successfully", admin.getFirstName() + " " + admin.getLastName() + " is now an admin", HttpStatus.OK);
        } else {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Email is not recognized", null, HttpStatus.BAD_REQUEST);
        }

    }


}
