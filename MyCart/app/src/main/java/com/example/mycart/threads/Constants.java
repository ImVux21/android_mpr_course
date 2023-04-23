package com.example.mycart.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Constants {
    public static final ExecutorService executor = Executors.newFixedThreadPool(4);
}
