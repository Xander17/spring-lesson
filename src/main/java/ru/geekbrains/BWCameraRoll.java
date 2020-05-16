package ru.geekbrains;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("bwCameraRoll")
@Scope("prototype")
public class BWCameraRoll implements CameraRoll {
    @Override
    public String toString() {
        return "BW roll";
    }
}
