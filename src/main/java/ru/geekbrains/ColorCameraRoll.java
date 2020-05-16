package ru.geekbrains;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("colorCameraRoll")
@Scope("prototype")
public class ColorCameraRoll implements CameraRoll {
    @Override
    public String toString() {
        return "Color roll";
    }
}
