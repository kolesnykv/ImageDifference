package com.knubisfot;

import com.knubisfot.utils.ProcessingUtils;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        BufferedImage input1 = ImageIO.read(new File("src/main/resources/img_4.png"));
        BufferedImage input2 = ImageIO.read(new File("src/main/resources/img_3.png"));
        ImageIO.write(new ProcessingUtils().getDifferenceImage(input1, input2), "jpg", new File("src/main/resources/img_2.png"));
    }
}


