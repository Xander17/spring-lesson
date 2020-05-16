package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Camera camera = context.getBean("camera", Camera.class);
        camera.shot();
        camera.setCameraRoll(context.getBean("bwCameraRoll", CameraRoll.class));
        camera.shot();
        camera.broke();
        camera.shot();

        camera = context.getBean("camera", Camera.class);
        camera.shot();

    }
}
