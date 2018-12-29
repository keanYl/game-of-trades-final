package io.gameoftrades.student18;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author kean yew lee
 */
public class PadImpl implements Pad {

    private List<Coordinaat> Path = new ArrayList();

    private final int GCost;

    public PadImpl(List<Coordinaat> Path, int GCost) {
        this.Path = Path;
        this.GCost = GCost;
    }

    @Override
    public int getTotaleTijd() {
        return GCost;
    }

    @Override
    public Richting[] getBewegingen() {
        //de beweging bepalen tussen twee coordinaten
        Richting[] Directions = new Richting[Path.size() - 1];

        for (int i = 0; i < Path.size() - 1; i++) {
            Coordinaat Current = Path.get(i);
            Coordinaat Next = Path.get(i + 1);
            Directions[i] = Richting.tussen(Current, Next);
        }

        return Directions;
    }

    @Override
    public Pad omgekeerd() {

        List<Coordinaat> rPath = Path;

        Collections.reverse(rPath);
        PadImpl reversePath = new PadImpl(Path, GCost);

        return reversePath;
    }

    @Override
    public Coordinaat volg(Coordinaat start) {
        if (start.equals(Path.get(0))) {
            return Path.get(Path.size() - 1);
        }
        throw new IllegalArgumentException("the start cordinate is wrong");

    }
}
