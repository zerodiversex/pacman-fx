/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/EngineService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications;

import javafx.scene.input.KeyEvent;
import tools.User;

public interface EngineService {
  public void init();

  public void start();

  public void stop();

  public void setPlayerCommand(User.COMMAND command);

  public void releasePlayerCommand(User.COMMAND command);

}
