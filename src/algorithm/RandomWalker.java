/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: algorithm/RandomWalker.java 2015-03-11 buixuan.
 * ******************************************************/
package algorithm;

import specifications.AlgorithmService;
import specifications.EngineService;
import specifications.RequireEngineService;

import java.util.Random;

public class RandomWalker implements AlgorithmService, RequireEngineService {
    private EngineService engine;
    private Random gen;

    public RandomWalker() {
    }

    @Override
    public void bindEngineService(EngineService service) {
        engine = service;
    }

    @Override
    public void init() {
        gen = new Random();
    }

    @Override
    public void activation() {
    }

    @Override
    public void stepAction() {
    }
}
