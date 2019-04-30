package game.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import statVars.AjustesNave;

public class Nave {
    private int id;

    private Cursor orientation;

    private double posX;
    private double posY;

    private final int SPEED = 5;
    private int lifes;
    private final int MAX_LIFES = AjustesNave.MAX_LIFES;

    private BooleanProperty upPressed, downPressed, rightPressed, leftPressed;
    private BooleanBinding anyPressed;

    private ImageView imgNave;
    private Image imagenRotada;

    private Arma arma;

    private SnapshotParameters snapshotParameters;

    private GraphicsContext graphicsContext;

    private ImageView[] imgVidas;

    public Nave(GraphicsContext graphicsContext, Pane pane, int posX, int posY, int idNave, ImageView imgNave, BooleanProperty upPressed, BooleanProperty downPressed, BooleanProperty rightPressed, BooleanProperty leftPressed, BooleanBinding anyPressed) {
        imgVidas = new ImageView[MAX_LIFES];
        ///IMAGENES A LAS VIDAS
        for (int i = 0; i<MAX_LIFES; i++) {
            ImageView imagen = new ImageView("game/res/img/vida.png");
            imagen.setX(120 + (imagen.getImage().getWidth() + 5) * i+1);
            imagen.setY(110);
            pane.getChildren().add(imagen);
            imgVidas[i] = imagen;
        }

        lifes = AjustesNave.START_LIFES;

        this.id = idNave;

        arma = new Arma(graphicsContext, pane);
        orientation = new Cursor();

        this.graphicsContext = graphicsContext;

        this.posX = posX;
        this.posY = posY;

        this.upPressed = upPressed;
        this.downPressed = downPressed;
        this.rightPressed = rightPressed;
        this.leftPressed = leftPressed;
        this.anyPressed = anyPressed;

        this.imgNave = imgNave;

        this.snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);

    }

    public Arma getArma(){
        return arma;
    }

    public Cursor getOrientation() {
        return orientation;
    }

    public void setOrientation(double x, double y){
        orientation.setPosX(x);
        orientation.setPosY(y);
    }

    public void setImagenRotada(Image imagenRotada) {
        this.imagenRotada = imagenRotada;
    }

    public Image getImagenRotada() {
        return imagenRotada;
    }

    public ImageView getImgNave(){
        return imgNave;
    }
    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosX(double pos){
        posX = pos;
    }
    public void setPosY(double pos){
        posY = pos;
    }

    public int getId(){
        return id;
    }

    private void mover(){
        if (upPressed.get()) {
            posY = getPosY() - SPEED;
        }
        if (downPressed.get()) {
            posY = getPosY() + SPEED;
        }
        if (leftPressed.get()) {
            posX = getPosX() - SPEED;
        }
        if (rightPressed.get()) {
            posX = getPosX() + SPEED;
        }
    }

    public double getAngle(){
//        double centerX = posX + imgNave.getHeight()/2;
//        double centerY = posY + imgNave.getWidth()/2;
//
//
//        double cc = (centerX - orientation.getPosX());
//        double co = (centerY - orientation.getPosY());
//
//        return Math.toDegrees(Math.atan2(co, cc))-90;
        return Math.round(Math.toDegrees(
                Math.atan2(
                        ((posY + imagenRotada.getWidth()/2) - orientation.getPosY()),
                        ((posX + imagenRotada.getHeight()/2) - orientation.getPosX()))
                )
        )-90;
    }

    private void rotate(){
        imgNave.setRotate(getAngle());

        imagenRotada = imgNave.snapshot(snapshotParameters, null);
    }

    public void shoot(double cursorX, double cursorY) {

        //mediaPlayer.setOnEndOfMedia(()->mediaPlayer.stop());

        arma.shoot(
                (posX + imgNave.getImage().getWidth()/2),
                (posY + imgNave.getImage().getHeight()/2),
                (posX + imgNave.getImage().getWidth()/2) - cursorX,
                (posY + imgNave.getImage().getHeight()/2) - cursorY,
                getAngle());
    }

    public void update(double time){
        arma.update(time);
        if(anyPressed.get()) {
            mover();
        }
        rotate();
    }

    public void render(){
        graphicsContext.drawImage(imagenRotada, posX, posY);

        arma.render();
        for (int i = 0; i < MAX_LIFES; i++) {
            if(i< lifes) {
                imgVidas[i].setOpacity(1);
            }else{
                imgVidas[i].setOpacity(0.5);
            }
        }
    }

    public void subsLive(){
        lifes--;
    }

    public void addLive(){
        if(lifes != 5) {
            lifes++;
        }
    }

    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

}
