package com.ajith.security.auth;

import com.ajith.security.config.JwtServiceImpl;
import com.ajith.security.exceptions.CustomBadCredentialException;
import com.ajith.security.exceptions.EmailAlreadyExistsException;
import com.ajith.security.exceptions.UserBlockedException;
import com.ajith.security.user.dto.BasicResponse;
import com.ajith.security.user.dto.LoginRequest;
import com.ajith.security.user.dto.LoginResponse;
import com.ajith.security.user.dto.RegistrationRequest;
import com.ajith.security.user.iservice.IUserService;
import com.ajith.security.roles.model.Role;
import com.ajith.security.user.model.User;
import com.ajith.security.roles.repository.RoleRepository;
import com.ajith.security.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final IUserService iUserService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtServiceImpl;
    public ResponseEntity< BasicResponse> register (RegistrationRequest request) {
        try{
            boolean existEmail = iUserService.isEmailExist(request.getEmail());
            if (existEmail) {
               throw new EmailAlreadyExistsException ("This is email already in use try to use another one !!");
            }else{
                User newUser = mapRequestToUser(request);
                userRepository.save ( newUser );
               return ResponseEntity.status ( HttpStatus.CREATED ).body ( getResponse (
                       "User registered successfully" ,
                        "user created") );
            }
        }catch (EmailAlreadyExistsException e){
            log.error ( e.getMessage(), e );
            throw new EmailAlreadyExistsException ( e.getMessage () );
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    getResponse ("An error occurred during registration","Please try again later."));
        }
    }

    private User mapRequestToUser (RegistrationRequest request) {
        var user = User.builder ( )
                .fullName ( request.getFullName () )
                .phoneNumber ( request.getPhoneNumber () )
                .email ( request.getEmail () )
                .password (passwordEncoder.encode ( request.getPassword ()))
                .role ( getUserRole() )
                .build ();
        return user;
    }

    public ResponseEntity<LoginResponse> login (LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken (
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException ("user not found"));

            if (user.isBlocked ()) {
                throw new UserBlockedException ("user is blocked");
            }

            var jwtToken = jwtServiceImpl.generateToken(user);

            return
                    ResponseEntity.status ( HttpStatus.OK ).body (LoginResponse.builder()
                            .access_token (jwtToken)
                            .username ( user.getUsername () )
                            .isLogged ( user.isActive () )
                            .build());
        }

        catch (BadCredentialsException e) {
            throw new CustomBadCredentialException ("email or password is incorrect");
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Worker not found");
        }
        catch (Exception e){
            throw new RuntimeException (e.getMessage ());
        }
    }

    private Role getUserRole() {
        Optional<Role> userRole = roleRepository.findAll().stream()
                .filter(role -> role.getRoleName().equals("ROLE_USER"))
                .findFirst();

        if (userRole.isPresent()) {
            return userRole.get();
        } else {
            Role newRole = new Role();
            newRole.setRoleName("ROLE_USER");
            return roleRepository.save(newRole);
        }
    }


    private static BasicResponse getResponse ( String message, String description ) {
        return BasicResponse.builder ( )
                .message ( message )
                .timestamp ( LocalDateTime.now ( ) )
                .description ( description )
                .build ( );
    }
}
