package ua.mem4ik.eshop.src.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GuestService {

    public String generateGuestToken() {
        return UUID.randomUUID().toString();
    }
}

