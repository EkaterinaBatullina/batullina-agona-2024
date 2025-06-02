package com.technokratos.fileupload;

public class FIleTask {

    public static void main(String[] args) {
        int filesCount = 5;
        for (int i = 1; i <= filesCount; i++) {
            int fileId = i;
            Runnable task = () -> {
                for (int progress = 0; progress <= 100; progress += 10) {
                    System.out.println("File " + fileId + ": " + progress + "%");
                }
                System.out.println("File " + fileId + " uploaded!");
            };
            Thread thread = new Thread(task);
            thread.start();
        }
    }
}