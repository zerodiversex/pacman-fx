/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/WriteService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications;

import javafx.geometry.Point2D;
import tools.Game;
import tools.Player;
import tools.User;

public interface WriteService {

  public void setGameStatus(Game.STATUS gameStatus);

  public void setPlayerCurrentDirection(User.Direction direction);

  public void setSpecialModeTimer(int specialModeTimer);

  public void addScore(int score);

  public void setMode(Player.MODE mode);

  public void setBotLocation(Point2D p);

  public void setBotVitesse(Point2D p);

  public void setPlayerVitesse(Point2D p);

  public void setPlayerLocation(Point2D p);

  public void incrNbRows(int value);

  public void incrNbCols(int value);

  public void incrNbDots(int value);

  public void decrNbDots(int dots);

  public void setNbDots(int dots);

  public void setBoard(Game.Cell[][] board);

  public void setBoardCell(Game.Cell cell, int row, int column);

  public void setNbCols(int nbCols);

  public void setNbRows(int nbRows);

  public void setLastDirection(User.Direction direction);

  public void addLocationPlayer(Point2D p);
}
