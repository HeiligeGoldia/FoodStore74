package com.se.kientruc.nhom74.user.service;

import org.springframework.stereotype.Service;

@Service
public class SlowService {

    public String slowMethod(){
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Slow service invoked successfully";
    }
}