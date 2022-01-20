package data;

import javafx.geometry.Point2D;
import specifications.DataService;
import tools.Game;
import tools.Player;
import tools.User;

import static tools.Game.Cell;
import static tools.Game.STATUS.ON_GOING;

public class Data implements DataService {
    public final static double CELL_WIDTH = 20.0;

    private int nbRows;
    private int nbCols;
    private Cell[][] board;
    private int score;
    private int nbDots;
    private Game.STATUS gameStatus;
    private Player.MODE mode;
    private Point2D locationPlayer;
    private Point2D vitessePlayer;
    private Point2D locationBot;
    private Point2D vitesseBot;
    private User.Direction lastDirection;
    private User.Direction currentDirection;
    private int specialModeTimer;

    /**
     * Start a new game upon initialization
     */
    public Data() {
        this.init();
    }

    public void init() {
        gameStatus = ON_GOING;
        mode = Player.MODE.NORMAL;
        nbDots = 0;
        nbRows = 0;
        nbCols = 0;
        score = 0;
        specialModeTimer = 0;
    }

    @Override
    public void setSpecialModeTimer(int value) {
        specialModeTimer = value;
    }

    @Override
    public int getSpecialModeTimer() {
        return specialModeTimer;
    }

    public Player.MODE getMode() {
        return mode;
    }

    public void setMode(Player.MODE ghostEatingModeBool) {
        mode = ghostEatingModeBool;
    }


    public Cell getCellValue(int row, int column) {
        return this.board[row][column];
    }

    public User.Direction getCurrentDirection() {
        return currentDirection;
    }

    public User.Direction getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(User.Direction direction) {
        lastDirection = direction;
    }

    @Override
    public void addLocationPlayer(Point2D p) {
        locationPlayer = locationPlayer.add(p);
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public Game.STATUS getGameStatus() {
        return gameStatus;
    }

    @Override
    public int getNbRows() {
        return nbRows;
    }

    @Override
    public int getNbDots() {
        return nbDots;
    }

    @Override
    public Cell[][] getBoard() {
        return board;
    }

    @Override
    public int getNbCols() {
        return nbCols;
    }

    @Override
    public Point2D getLocationPlayer() {
        return locationPlayer;
    }

    @Override
    public Point2D getLocationBot() {
        return locationBot;
    }

    @Override
    public Point2D getVitesseBot() {
        return vitesseBot;
    }

    @Override
    public Point2D getVitessePlayer() {
        return vitessePlayer;
    }

    @Override
    public void setBotLocation(Point2D p) {
        locationBot = p;
    }

    @Override
    public void setBotVitesse(Point2D p) {
        vitesseBot = p;
    }

    @Override
    public void setPlayerVitesse(Point2D p) {
        vitessePlayer = p;
    }

    @Override
    public void setPlayerLocation(Point2D p) {
        locationPlayer = p;
    }

    @Override
    public void incrNbRows(int value) {
        nbRows += value;
    }

    @Override
    public void incrNbCols(int value) {
        nbCols += value;
    }

    @Override
    public void setGameStatus(Game.STATUS gameStatus) {
        this.gameStatus = gameStatus;
    }

    @Override
    public void setPlayerCurrentDirection(User.Direction direction) {
        currentDirection = direction;
    }

    @Override
    public void addScore(int score) {
        this.score += score;
    }

    @Override
    public void incrNbDots(int dots) {
        this.nbDots += dots;
    }

    @Override
    public void decrNbDots(int dots) {
        this.nbDots -= dots;
    }

    @Override
    public void setNbDots(int dots) {
        this.nbDots = dots;
    }

    @Override
    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    @Override
    public void setBoardCell(Cell cell, int row, int column) {
        this.board[row][column] = cell;
    }

    @Override
    public void setNbCols(int nbCols) {
        this.nbCols = nbCols;
    }

    @Override
    public void setNbRows(int nbRows) {
        this.nbRows = nbRows;
    }
}
