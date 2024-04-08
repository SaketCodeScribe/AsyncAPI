package com.learn.asyncapi.Controller;

import com.learn.asyncapi.Service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RestController
public class Controller {
    int i = 0, j = 0;
    @Autowired
    private AsyncService asyncService;

    @Autowired
    private Executor executor;
    @GetMapping("/home")
    public String getHome(){
        try {
            Thread.sleep(10000);
            System.out.println("first call");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return Thread.currentThread().getName()+(j++);
    }

    @GetMapping("/voidType")
    public void getTask(){
        asyncService.performTask();
    }

    @GetMapping("/returnType")
    public CompletableFuture<String> getAnyTask() throws Exception{
        CompletableFuture<String> var = asyncService.performAnyTask(); // this will run in background thread
        CompletableFuture<String> var1 = asyncService.performAnyTask("a"); // this will run in background thread
        for(i=0; i<5; i++)          // this will execute in concurrency with the above threads.
            System.out.println(i);
        return CompletableFuture.completedFuture(var.get()+var1.get());
    }
    @GetMapping("/homes")
    public String getHomes(){
        return Thread.currentThread().getName();
    }
}
