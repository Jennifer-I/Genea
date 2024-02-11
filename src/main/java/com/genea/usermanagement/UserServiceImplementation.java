package com.genea.usermanagement;

import com.genea.dto.ApiResponse;
import com.genea.dto.EmailDetailsRequest;
import com.genea.dto.RegistrationRequestDto;
import com.genea.dto.UserResponseDto;
import com.genea.email.EmailService;
import com.genea.entity.User;
import com.genea.entity.VerificationToken;
import com.genea.enums.Role;
import com.genea.exception.UserNotFoundException;
import com.genea.repository.TokenRepository;
import com.genea.repository.UserRepository;
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
    @Value("${spring.mail.username}")
    public String sender;

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;

    public UserServiceImplementation(UserRepository userRepository, EmailService emailService, TokenRepository tokenRepository) {
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
    private VerificationToken createVerificationToken(Long userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return VerificationToken.builder()
                    .user(user)
                    .createdAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusMinutes(15))
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

        VerificationToken verificationToken = createVerificationToken(newUser.getId());
        tokenRepository.save(verificationToken);

        sendVerificationEmail(request, verificationToken.getConfirmationToken());

        return new ApiResponse<>(HttpStatus.OK.value(), newUser.getFirstName() + " welcome to Genea! Please check your email to confirm your account");
    }






//    @Override
//    public ApiResponse<UserResponseDto> createCustomer(RegistrationRequestDto request) {
//        Optional<User> user = userRepository.findByEmail(request.getEmail());
//        if (user.isPresent()) {
//            return new ApiResponse<>(HttpStatus.OK.value(), "User already exists", null,HttpStatus.BAD_REQUEST);
//        }
//        User newUser = User.builder()
//                .email(request.getEmail())
//                .firstName(request.getFirstName())
//                .lastName(request.getLastName())
//                .password(request.getPassword())
//                .isVerified(false)
//                .role(Role.CUSTOMER)
//                .build();
//        userRepository.save(newUser);
//        VerificationToken verificationToken = VerificationToken.builder()
//                .user(newUser)
//                .expiresAt(LocalDateTime.now().plusMinutes(15))
//                .confirmedAt(null)
//                .token(UUID.randomUUID().toString())
//                .build();
//        tokenRepository.save(verificationToken);
//        try {
//            sendVerificationEmail(request, verificationToken.getToken());
//        } catch (InterruptedException e) {
//            throw new RuntimeException("Failed to send email");
//        }
//
//        return new ApiResponse<>(200, newUser.getFirstName() + " " + "welcome to Genea! please check your email to confirm your account");
//
//    }


    private void sendVerificationEmail(RegistrationRequestDto registrationRequest, String confirmationToken) throws InterruptedException {

        String emailContent = "Thank you for registering with Genea. Please click on the link below to verify your account \n"
                + "http://localhost:1999/api/v1/auth/confirmEmail?confirmationToken="+ confirmationToken;
        EmailDetailsRequest emailDetailsRequest = EmailDetailsRequest.builder()
                .mailFrom(sender)
                .mailTo(registrationRequest.getEmail())
                .subject("Genea Stores Account Verification")
                .text(emailContent)
                .build();
        emailService.sendEmailAsync(emailDetailsRequest);


    }

    @Override
    public ApiResponse<String> confirmEmail(String confirmationToken) {
        Optional<VerificationToken> optionalVerificationToken = tokenRepository.findByConfirmationToken(confirmationToken);
        if (optionalVerificationToken.isPresent()) {
            VerificationToken verificationToken = optionalVerificationToken.get();
            if (isTokenExpired(verificationToken)) {
                return new ApiResponse<>(HttpStatus.OK.value(), "token has expired", null, HttpStatus.BAD_REQUEST);
            }
            if (verificationToken.getConfirmedAt() != null) {
                return new ApiResponse<>(HttpStatus.OK.value(), "Email already verified", null, HttpStatus.BAD_REQUEST);
            }
            Optional<User> userOptional = userRepository.findByEmail(verificationToken.getUser().getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                log.info("User retrieved: {}", user);
                user.setIsVerified(true);
                user.setIsVerified(true);
                userRepository.save(user);
                verificationToken.setConfirmedAt(LocalDateTime.now());
                tokenRepository.save(verificationToken);

                return new ApiResponse<String>(HttpStatus.OK.value(), "OTP verified successfully", null, HttpStatus.OK);
            } else {
                return new ApiResponse<String>(HttpStatus.OK.value(), "User not in database", null, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ApiResponse<String>(HttpStatus.OK.value(), "The provided confirmation token is invalid or expired!", null, HttpStatus.BAD_REQUEST);
        }
    }


    private boolean isTokenExpired(VerificationToken verificationToken) {
        return verificationToken.getExpiresAt().isBefore(LocalDateTime.now());
    }

}
