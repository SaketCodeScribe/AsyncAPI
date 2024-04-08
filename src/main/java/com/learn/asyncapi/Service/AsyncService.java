package com.learn.asyncapi.Service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {
    @Async
    public void performTask(){
        try{
            Thread.sleep(5000);
            System.out.println("Task executed");
        }catch (Exception ex){
            System.out.println("Task not executed");
        }
    }

    @Async
    public CompletableFuture<String> performAnyTask(){
        try{
            Thread.sleep(5000);
            return CompletableFuture.completedFuture("a");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return CompletableFuture.completedFuture("b");
    }

    @Async
    public CompletableFuture<String> performAnyTask(String var){
        try{
            Thread.sleep(5000);
            return CompletableFuture.completedFuture("Task completed-"+var);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return CompletableFuture.completedFuture("Task not completed");
    }
}
