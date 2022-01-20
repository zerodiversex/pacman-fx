package engine;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import levels.Maze;
import specifications.CreateMapService;
import specifications.DataService;
import specifications.EngineService;
import specifications.RequireDataService;
import tools.Game;
import tools.Player;
import tools.User;
import userinterface.Viewer;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static tools.Game.STATUS.LOSE;
import static tools.Game.STATUS.VICTORY;

public class Engine implements EngineService, RequireDataService, CreateMapService {
    @FXML
    private Label scoreLabel;

    @FXML
    private Label levelLabel;

    @FXML
    private Label gameOverLabel;

    @FXML
    private Viewer viewer;

    private DataService data;

    private Timer engineClock;

    public Engine() {
    }

    @Override
    public void bindDataService(DataService service) {
        this.data = service;
    }

    public void initialize() {
        this.viewer.bindReadService(data);
        viewer.init();
        this.createMap();
        this.update(User.Direction.NONE);
    }

    @Override
    public void start() {
        data.setGameStatus(Game.STATUS.ON_GOING);
        this.gameOverLabel.setText("");
        this.engineClock = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(() -> update(data.getCurrentDirection()));
            }
        };
        this.engineClock.schedule(timerTask, 0L, 210L);
    }

    @Override
    public void stop() {
        data.setGameStatus(Game.STATUS.PAUSED);
        this.gameOverLabel.setText("PAUSED");
        this.engineClock.cancel();
    }

    private void update(User.Direction direction) {
        this.updateDirection(direction);
        this.viewer.update();
        this.scoreLabel.setText(String.format("Score: %d", this.data.getScore()));
        if (data.getGameStatus() == Game.STATUS.LOSE) {
            stop();
            this.gameOverLabel.setText("DEFAITE");
        }
        if (data.getGameStatus() == Game.STATUS.VICTORY) {
            stop();
            this.gameOverLabel.setText("GAGNE");
        }
        if (data.getMode() == Player.MODE.SUPERPACMAN) {
            if (data.getSpecialModeTimer() == 0) {
                data.setMode(Player.MODE.NORMAL);
            }
            int timer = data.getSpecialModeTimer();
            data.setSpecialModeTimer(timer--);
        }
    }

    @Override
    public void init() {
        // Do nothing since we will initialize the controller in fxml by initialize method
    }

    public void createMap() {
        int pacmanRow = 0;
        int pacmanColumn = 0;
        int botRow = 0;
        int botColumn = 0;
        data.setNbCols(Maze.board[0].length);
        data.setNbRows(Maze.board.length);
        data.setBoard(new Game.Cell[data.getNbRows()][data.getNbCols()]);
        for (int i = 0; i < Maze.board.length; i++) {
            for (int j = 0; j < Maze.board[0].length; j++) {
                Game.Cell thisValue;
                switch (Maze.board[i][j]) {
                    case 1:
                        thisValue = Game.Cell.WALL;
                        break;
                    case 0:
                        thisValue = Game.Cell.YELLOWDOT;
                        data.incrNbDots(1);
                        break;
                    case 2:
                        thisValue = Game.Cell.GREENDOT;
                        data.incrNbDots(1);
                        break;
                    case 4:
                        thisValue = Game.Cell.PURLEDOT;
                        data.incrNbDots(1);
                        break;
                    case 5:
                        thisValue = Game.Cell.REDDOT;
                        data.incrNbDots(1);
                        break;
                    case 8:
                        thisValue = Game.Cell.BOTPOINT;
                        botRow = j;
                        botColumn = i;
                        break;
                    case 9:
                        thisValue = Game.Cell.PLAYERPOINT;
                        pacmanRow = j;
                        pacmanColumn = i;
                        break;
                    default:
                        thisValue = Game.Cell.EMPTY;
                        break;
                }
                data.setBoardCell(thisValue, i, j);
            }
        }
        data.setPlayerLocation(new Point2D(pacmanRow, pacmanColumn));
        data.setPlayerVitesse(new Point2D(0, 0));
        data.setBotLocation(new Point2D(botRow, botColumn));
        Random random = new Random();
        data.setBotVitesse(changeVitesseXY(changeDirection(random.nextInt() * 4)));
        data.setPlayerCurrentDirection(User.Direction.NONE);
        data.setLastDirection(User.Direction.NONE);
    }

    @Override
    public void setPlayerCommand(User.COMMAND command) {
        User.Direction direction = User.Direction.NONE;
        switch (command) {
            case LEFT:
                direction = User.Direction.LEFT;
                break;
            case RIGHT:
                direction = User.Direction.RIGHT;
                break;
            case UP:
                direction = User.Direction.UP;
                break;
            case DOWN:
                direction = User.Direction.DOWN;
                break;
        }
        this.data.setPlayerCurrentDirection(direction);
    }

    public Point2D[] moveAI(Point2D velocity, Point2D location) {
        // TODO: Move this to random walker
        Random generator = new Random();
        if (data.getMode() == Player.MODE.NORMAL) {
            if (location.getY() == data.getLocationPlayer().getY()) {
                if (location.getX() > data.getLocationPlayer().getX()) {
                    velocity = changeVitesseXY(User.Direction.UP);
                } else {
                    velocity = changeVitesseXY(User.Direction.DOWN);
                }
            } else if (location.getX() == data.getLocationPlayer().getX()) {
                if (location.getY() > data.getLocationPlayer().getY()) {
                    velocity = changeVitesseXY(User.Direction.LEFT);
                } else {
                    velocity = changeVitesseXY(User.Direction.RIGHT);
                }
            }
        }
        if (data.getMode() == Player.MODE.SUPERPACMAN) {
            if (location.getY() == data.getLocationPlayer().getY()) {
                if (location.getX() > data.getLocationPlayer().getX()) {
                    velocity = changeVitesseXY(User.Direction.DOWN);
                } else {
                    velocity = changeVitesseXY(User.Direction.UP);
                }
            } else if (location.getX() == data.getLocationPlayer().getX()) {
                if (location.getY() > data.getLocationPlayer().getY()) {
                    velocity = changeVitesseXY(User.Direction.RIGHT);
                } else {
                    velocity = changeVitesseXY(User.Direction.LEFT);
                }
            }
        }
        Point2D potentialLocation = location.add(velocity);
        while (data.getBoard()[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == Game.Cell.WALL) {
            int randomNum = generator.nextInt(4);
            User.Direction direction = changeDirection(randomNum);
            velocity = changeVitesseXY(direction);
            potentialLocation = location.add(velocity);
        }
        location = potentialLocation;
        return new Point2D[]{velocity, location};
    }

    public void moveBots() {
        Point2D[] botData = moveAI(data.getVitesseBot(), data.getLocationBot());
        data.setBotVitesse(botData[0]);
        data.setBotLocation(botData[1]);
    }

    public Point2D changeVitesseXY(User.Direction direction) {
        if (direction == User.Direction.LEFT) {
            return new Point2D(0, -1);
        }
        if (direction == User.Direction.RIGHT) {
            return new Point2D(0, 1);
        }
        if (direction == User.Direction.UP) {
            return new Point2D(-1, 0);
        }
        if (direction == User.Direction.DOWN) {
            return new Point2D(1, 0);
        } else {
            return new Point2D(0, 0);
        }
    }

    public void movePlayer(User.Direction direction) {
        Point2D potentialPlayerVelocity = changeVitesseXY(direction);
        Point2D potentialPlayerLocation = data.getLocationPlayer().add(potentialPlayerVelocity);

        boolean isPlayerAtWall = data.getBoard()[(int) potentialPlayerLocation.getX()][(int) potentialPlayerLocation.getY()] == Game.Cell.WALL;
        if (direction.equals(data.getLastDirection())) {
            if (isPlayerAtWall) {
                data.setPlayerVitesse(changeVitesseXY(User.Direction.NONE));
            } else {
                data.setPlayerVitesse(potentialPlayerVelocity);
                data.setPlayerLocation(potentialPlayerLocation);
            }
        } else {
            if (isPlayerAtWall) {
                potentialPlayerVelocity = changeVitesseXY(data.getLastDirection());
                potentialPlayerLocation = data.getLocationPlayer().add(potentialPlayerVelocity);
                isPlayerAtWall = data.getBoard()[(int) potentialPlayerLocation.getX()][(int) potentialPlayerLocation.getY()] == Game.Cell.WALL;
                if (isPlayerAtWall) {
                    data.setPlayerVitesse(changeVitesseXY(User.Direction.NONE));
                    data.setLastDirection(direction);
                } else {
                    data.setPlayerVitesse(changeVitesseXY(data.getLastDirection()));
                    data.addLocationPlayer(data.getVitessePlayer());
                }
            } else {
                data.setPlayerVitesse(potentialPlayerVelocity);
                data.setPlayerLocation(potentialPlayerLocation);
                data.setLastDirection(direction);
            }
        }
    }

    private void handlePlayerGoIntoPointCell(Game.Cell pacmanLocationCell) {
        data.getBoard()[(int) data.getLocationPlayer().getX()][(int) data.getLocationPlayer().getY()] = Game.Cell.EMPTY;
        if (pacmanLocationCell == Game.Cell.YELLOWDOT) {
            data.addScore(10);
            data.decrNbDots(1);
        }
        // Change to green dot, can eat bot
        if (pacmanLocationCell == Game.Cell.GREENDOT) {
            data.setMode(Player.MODE.SUPERPACMAN);
            data.addScore(20);
            data.setSpecialModeTimer(50);
            data.decrNbDots(1);
        }

        // TODO: Make bot invisible
        if (pacmanLocationCell == Game.Cell.PURLEDOT) {
            data.setMode(Player.MODE.INVISIBLE);
            data.addScore(20);
            data.decrNbDots(1);
        }

        // TODO: Change map
        if (pacmanLocationCell == Game.Cell.REDDOT) {
            data.setMode(Player.MODE.LABYRINTHE);
            data.addScore(20);
            data.decrNbDots(1);
        }
    }

    public User.Direction changeDirection(int x) {
        if (x == 0) {
            return User.Direction.LEFT;
        } else if (x == 1) {
            return User.Direction.RIGHT;
        } else if (x == 2) {
            return User.Direction.UP;
        } else {
            return User.Direction.DOWN;
        }
    }


    public void updateDirection(User.Direction direction) {
        this.movePlayer(direction);
        Game.Cell pacmanLocationCell = data.getBoard()[(int) data.getLocationPlayer().getX()][(int) data.getLocationPlayer().getY()];
        handlePlayerGoIntoPointCell(pacmanLocationCell);
        if (data.getMode() == Player.MODE.SUPERPACMAN) {
            if (data.getLocationPlayer().equals(data.getLocationBot())) {
                resetBot();
                data.addScore(100);
            }
        }
        // game over if PacMan is eaten by a bot
        else {
            if (data.getLocationPlayer().equals(data.getLocationBot())) {
                data.setPlayerVitesse(new Point2D(0, 0));
                stop();
                data.setGameStatus(LOSE);
                return;
            }
        }
        this.moveBots();
        if (data.getMode() == Player.MODE.SUPERPACMAN) {
            if (data.getLocationPlayer().equals(data.getLocationBot())) {
                resetBot();
                data.addScore(100);
            }
        }
        // game over if PacMan is eaten by a bot
        else {
            if (data.getLocationPlayer().equals(data.getLocationBot())) {
                data.setPlayerVitesse(new Point2D(0, 0));
                stop();
                data.setGameStatus(LOSE);
                return;
            }
        }
        if (this.isFinished()) {
            data.setGameStatus(VICTORY);
        }
    }

    public boolean isFinished() {
        return data.getNbDots() == 0;
    }

    public void resetBot() {
        for (int row = 0; row < data.getNbRows(); row++) {
            for (int column = 0; column < data.getNbCols(); column++) {
                if (data.getBoard()[row][column] == Game.Cell.BOTPOINT) {
                    data.setBotLocation(new Point2D(row, column));
                }
            }
        }
        data.setBotVitesse(new Point2D(-1, 0));
    }

    @Override
    public void releasePlayerCommand(User.COMMAND command) {
        // Do nothing in pacman game
    }
}
