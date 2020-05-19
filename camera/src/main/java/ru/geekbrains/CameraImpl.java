package ru.geekbrains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("camera")
@Scope("prototype")
public class CameraImpl implements Camera {
    @Setter(onMethod = @__({@Autowired, @Qualifier("colorCameraRoll")}))
    @Getter
    private CameraRoll cameraRoll;

    @Value("false")
    @Getter
    private boolean broken;

    public void shot() {
        if (isBroken()) System.out.println("Camera is broken");
        else System.out.println("Camera shooting using " + cameraRoll);
    }

    public void broke() {
        broken = true;
    }

    @PostConstruct
    public void ready() {
        System.out.println("Camera is ready");
    }

    @PreDestroy
    public void putOff() {
        System.out.println("Camera have destroyed");
    }
}
