package com.ajith.security.user.service;

import com.ajith.security.config.JwtServiceImpl;
import com.ajith.security.exceptions.MissingUserAddressException;
import com.ajith.security.user.dto.AddressRequest;
import com.ajith.security.user.dto.BasicResponse;
import com.ajith.security.user.iservice.IAddressService;
import com.ajith.security.user.model.Address;
import com.ajith.security.user.model.User;
import com.ajith.security.user.repository.AddressRepository;
import com.ajith.security.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService {

   private final AddressRepository addressRepository;
   private final JwtServiceImpl jwtService;
   private final UserRepository userRepository;
    @Override
    public ResponseEntity < BasicResponse > addAddress (AddressRequest request,String authHeader) {
        try {
            User user = jwtService.extractUserFromAuthHeader ( authHeader );
            Address existingAddress = user.getAddress ();
            Address newAddress;

            if(existingAddress != null){
                newAddress = updateOldAddressWithNewOne (existingAddress ,request);
            }else{
                 newAddress = mappAddressRequestToAddress ( request );
            }

            Address savedAddress = addressRepository.save ( newAddress );
            user.setAddress ( savedAddress );
            userRepository.save ( user );
            String message;
            if (existingAddress != null) {
                message = "Address updated successfully for user " + user.getFullName();
            } else {
                message = "Address added to user " + user.getFullName() + " successfully";
            }
            return ResponseEntity.status ( HttpStatus.CREATED ).body ( BasicResponse.builder()
                            .description ("you are working with address")
                            .message (message)
                            .timestamp ( LocalDateTime.now () )
                    .build() );
        } catch (Exception e){
            throw new RuntimeException ( e.getMessage () );
        }
    }

    @Override
    public ResponseEntity < Address > getAddress (String authHeader) {
        try {
            User user = jwtService.extractUserFromAuthHeader ( authHeader );
            Address address = user.getAddress ();
            System.out.println (address + "---------------------------------------------------address" );
            if(address == null){
                throw new MissingUserAddressException ("user does not have any address he need to add");
            }
            return ResponseEntity.status ( HttpStatus.OK ).body ( user.getAddress () );
        } catch (MissingUserAddressException e){
            System.out.println ("catch close excicuted" );
            throw new MissingUserAddressException ( e.getMessage () );
        }
    }

    @Override
    public ResponseEntity < BasicResponse > deleteAddress (String authHeader) {
        try {
            User user = jwtService.extractUserFromAuthHeader ( authHeader );
            Address address = user.getAddress ();
            if(address == null){
                throw new MissingUserAddressException ("user does not have any address he need to add");
            }
            user.setAddress ( null );
            addressRepository.delete ( address );
            userRepository.save ( user );
            return ResponseEntity.status ( HttpStatus.OK ).body ( BasicResponse.builder()
                    .description ( "Address deleted from user  "+ user.getFullName () + "  successfully")
                    .message ( "Address deleted successfully" )
                    .timestamp ( LocalDateTime.now () )
                    .build() );
        } catch (Exception e){
            throw new RuntimeException ( e.getMessage () );
        }
    }


    private Address updateOldAddressWithNewOne (Address oldAddress, AddressRequest newAddress) {
        oldAddress.setCity ( newAddress.getCity ( ) );
        oldAddress.setCountry ( newAddress.getCountry ( ) );
        oldAddress.setDistrict ( newAddress.getDistrict ( ) );
        oldAddress.setPostalCode ( newAddress.getPostalCode ( ) );
        oldAddress.setHouseName ( newAddress.getHouseName ( ) );
        return oldAddress;
    }

    private Address mappAddressRequestToAddress (AddressRequest request) {
        return Address.builder ( )
                .houseName ( request.getHouseName () )
                .city ( request.getCity () )
                .country ( request.getCountry () )
                .district ( request.getDistrict ( ) )
                .postalCode ( request.getPostalCode () )
                .build ( );
    }
}
