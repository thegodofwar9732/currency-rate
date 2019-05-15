package converter;

import javafx.scene.image.Image;

public enum Status {
    UP("img/up.png"),
    DOWN("img/down.png"),
    UNCHANGED("img/same.png");

    private Image image;

    Status(String imgPath) {
        this.image = new Image(imgPath);
    }

    public Image getImage() {
        return image;
    }
}