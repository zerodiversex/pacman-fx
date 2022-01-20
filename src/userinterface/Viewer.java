package userinterface;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.ViewerService;
import tools.Player;
import tools.User;

import java.util.Objects;

import static data.Data.CELL_WIDTH;
import static tools.Game.Cell;

public class Viewer extends Group implements ViewerService, RequireReadService {


    @FXML
    private int nbRows;
    @FXML
    private int nbCols;
    private Image botImage;
    private Image weakBotImage;
    private Image murImage;
    private Image yellowDotImage;
    private Image greenDotImage;
    private Image purpleDotImage;
    private Image redDotImage;
    private Image rightPlayerImage;
    private Image upPlayerImage;
    private Image downPlayerImage;
    private Image leftPacmanImage;
    private ImageView[][] map;

    private ReadService data;


    /**
     * Initializes the values of the image instance variables from files
     */
    public Viewer() {
    }

    @Override
    public void init() {
        this.rightPlayerImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pacmanRight.gif")));
        this.upPlayerImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pacmanUp.gif")));
        this.downPlayerImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pacmanDown.gif")));
        this.leftPacmanImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pacmanLeft.gif")));
        this.botImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/redghost.gif")));
        this.weakBotImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/weakBot.gif")));
        this.murImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/wall.png")));
        this.yellowDotImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/yellowdot.png")));
        this.greenDotImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/greendot.png")));
        this.purpleDotImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/purpledot.png")));
        this.redDotImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/reddot.png")));
        this.initializeGrid();
    }

    @Override
    public void bindReadService(ReadService service) {
        this.data = service;
    }

    private void initializeGrid() {
        this.map = new ImageView[this.nbRows][this.nbCols];
        for (int i = 0; i < this.nbRows; i++) {
            for (int j = 0; j < this.nbCols; j++) {
                // Add to group table
                this.getChildren().add(initImageView(i, j));
            }
        }
    }

    private ImageView initImageView(int row, int column) {
        ImageView imageView = new ImageView();
        imageView.setX((double) column * CELL_WIDTH);
        imageView.setY((double) row * CELL_WIDTH);
        imageView.setFitWidth(CELL_WIDTH);
        imageView.setFitHeight(CELL_WIDTH);
        this.map[row][column] = imageView;
        return imageView;
    }

    public void update() {
        for (int row = 0; row < this.nbRows; row++) {
            for (int column = 0; column < this.nbCols; column++) {
                updateCellViewImage(row, column);
                placePlayerImage(row, column);
                updateBotImage(row, column);
            }
        }
    }

    private void updateCellViewImage(int row, int column) {
        Cell value = data.getCellValue(row, column);
        switch (value) {
            case WALL:
                this.map[row][column].setImage(this.murImage);
                break;
            case YELLOWDOT:
                this.map[row][column].setImage(this.yellowDotImage);
                break;
            case GREENDOT:
                this.map[row][column].setImage(this.greenDotImage);
                break;
            case PURLEDOT:
                this.map[row][column].setImage(this.purpleDotImage);
                break;
            case REDDOT:
                this.map[row][column].setImage(this.redDotImage);
                break;
            default:
                this.map[row][column].setImage(null);
                break;
        }
    }

    private void placePlayerImage(int row, int column) {
        if (row == data.getLocationPlayer().getX() && column == data.getLocationPlayer().getY()) {
            if (data.getLastDirection() == User.Direction.DOWN) {
                this.map[row][column].setImage(this.downPlayerImage);
            } else if (data.getLastDirection() == User.Direction.UP) {
                this.map[row][column].setImage(this.upPlayerImage);
            } else if (data.getLastDirection() == User.Direction.LEFT) {
                this.map[row][column].setImage(this.leftPacmanImage);
            } else {
                // For initial case
                this.map[row][column].setImage(this.rightPlayerImage);
            }
        }
    }


    private void updateBotImage(int row, int column) {
        if (row == data.getLocationBot().getX() && column == data.getLocationBot().getY()) {
            if (data.getMode() == Player.MODE.SUPERPACMAN) {
                this.map[row][column].setImage(this.weakBotImage);
            } else {
                this.map[row][column].setImage(this.botImage);
            }
        }
    }

    public int getNbRows() {
        return this.nbRows;
    }

    public void setNbRows(int nbRows) {
        this.nbRows = nbRows;
    }

    public int getNbCols() {
        return this.nbCols;
    }

    public void setNbCols(int nbCols) {
        this.nbCols = nbCols;
    }
}
