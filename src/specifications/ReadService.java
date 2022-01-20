/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/ReadService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications;

import javafx.geometry.Point2D;
import tools.Game;
import tools.Player;
import tools.Sound;
import tools.User;

import java.util.ArrayList;

public interface ReadService {
  public int getNbRows();

  public Game.Cell getCellValue(int row, int column);

  public int getSpecialModeTimer();

  public Player.MODE getMode();

  public Game.STATUS getGameStatus();

  public int getNbCols();

  public Point2D getLocationPlayer();

  public User.Direction getCurrentDirection();

  public User.Direction getLastDirection();

  public Point2D getLocationBot();

  public Point2D getVitesseBot();

  public Point2D getVitessePlayer();

  public int getScore();

  public int getNbDots();

  public Game.Cell[][] getBoard();
}
