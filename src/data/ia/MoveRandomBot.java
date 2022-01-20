/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/ia/MoveLeftPhantom.java 2015-03-11 buixuan.
 * ******************************************************/
package data.ia;

import specifications.BotService;
import tools.Position;

public class MoveRandomBot implements BotService {
    private Position position;

    public MoveRandomBot(Position p) {
        position = p;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public MOVE getAction() {
        int random = (int) (Math.floor(Math.random()) * 4);
        switch (random) {
            case 0:
                return MOVE.LEFT;
            case 1:
                return MOVE.RIGHT;
            case 2:
                return MOVE.UP;
            default:
                return MOVE.DOWN;
        }
    }

    @Override
    public void setPosition(Position p) {
        position = p;
    }
}
