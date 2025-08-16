package com.ecommerce.store.controllers;

import com.ecommerce.store.entities.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController
{

    @RequestMapping("/hello")
    public Message sayHello()
    {
        return new Message("hello world") ;
    }
}
