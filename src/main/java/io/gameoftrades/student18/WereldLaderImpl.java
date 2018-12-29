package io.gameoftrades.student18;

import io.gameoftrades.model.Wereld;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.kaart.Terrein;
import io.gameoftrades.model.kaart.TerreinType;

import io.gameoftrades.model.lader.WereldLader;

import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;
import io.gameoftrades.model.markt.Handelswaar;
import io.gameoftrades.model.markt.Markt;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WereldLaderImpl implements WereldLader {

    private Scanner m;
    private InputStream is;
   
    private List<Stad> steden;
    private List<Handel> handel;
    private ArrayList<String> list;

    private Kaart kaart;
    private Handel trade;
    private Markt markt;
    private Coordinaat coordinaatstad;
    private HandelType handeltyp;
    private Stad stad;
    private Wereld wereld;

    private int x;
    private int y;
    private int aantalsteden;
    private int aantalmarkten;

    private String saantalmarkten;
    private String saantalsteden;

    @Override
    public Wereld laad(String resource) {

        x = 0;
        y = 0;

        aantalsteden = 0;
        aantalmarkten = 0;

        steden = new ArrayList<>();
        handel = new ArrayList<>();
        list = new ArrayList<>();

        is = this.getClass().getResourceAsStream(resource);
        m = new Scanner(is);
//        zet txt in een list
        while (m.hasNextLine()) {
            String string = m.nextLine().trim();
            list.add(string);
        }

        m.close();

        SelecteerdNummers();
        MaakKaart();
        MaakTerrein();
        MaakSteden();
        MaakMarkten();

        markt = new Markt(handel);
        wereld = new Wereld(kaart, steden, markt);

        return wereld;
    }

    public void SelecteerdNummers() {
//        pakt de x en de y van de list
//        en de aantal steden en markten
//        verwijderd de integers die die word gepakt uit de list
        try {
            String string = list.get(0);
            String[] parts = string.split(",");
            x = Integer.parseInt(parts[0]);
            y = Integer.parseInt(parts[1]);

            saantalsteden = list.get(1 + y);
            aantalsteden = Integer.parseInt(saantalsteden);

            saantalmarkten = list.get(2 + y + aantalsteden);
            aantalmarkten = Integer.parseInt(saantalmarkten);

            list.remove(2 + y + aantalsteden);
            list.remove(1 + y);
            list.remove(0);
        } catch (IndexOutOfBoundsException s) {
            System.out.println(s);
        }
    }

    public void MaakKaart() {
        kaart = new Kaart(x, y);
    }

    public void MaakTerrein() {
//        split de string met de hulp van een substring
//        en maak alle objecten aan die nodig zijn voor de 
//        constructor van terrein
//        
        for (int i = 0; i < y; i++) {
            if (x == list.get(i).length()) {
                for (int j = 0; j < x; j++) {

                    String map = list.get(i);

                    String type = map.substring(j, j + 1);

                    Coordinaat coordinaat = Coordinaat.op(j, i);

                    char c = type.charAt(0);
                    TerreinType tp = TerreinType.fromLetter(c);

                    Terrein terrein = new Terrein(kaart, coordinaat, tp);

                  
                }
            } else {
                throw new IllegalArgumentException("x coord komt niet overeen");
            }
        }
    }

    public void MaakSteden() {
//        split de string op de komma
//        maak alle objecten aan die nodig zijn voor 
//        het aanmaken van een stad

        for (int i = y; i < y + aantalsteden; i++) {
            String tmp = list.get(i);

            //stdn.add(m.next());
            // de string van de array naar int 
            String[] split = tmp.split(",");
            String xvanstadz = split[0];
            int xvanstad = Integer.parseInt(xvanstadz);
            String yvanstadz = split[1];
            int yvanstad = Integer.parseInt(yvanstadz);
            String stadnaam = split[2];
            if (yvanstad < 1 || yvanstad > y || xvanstad < 1 || xvanstad > x) {
                throw new IllegalArgumentException("coord out of bounds");
            }

    

            coordinaatstad = Coordinaat.op(xvanstad - 1, yvanstad - 1);

            stad = new Stad(coordinaatstad, stadnaam);

            steden.add(stad);
        }
    }

    public void MaakMarkten() {
//        split op de komma 
//        en maak alle objecten aan die nodig zijn voor handel
        
        for (int i = y + aantalsteden; i < list.size(); i++) {

            String mrkt = list.get(i);

            String[] split = mrkt.split(",");
            String stadnaammarkt = split[0];
            String handeltype = split[1];
            String naam = split[2];
            String strprijs = split[3];

            int prijs = Integer.parseInt(strprijs);

            handeltyp = HandelType.valueOf(handeltype);

            Handelswaar hw = new Handelswaar(naam);

            stad = zoekstad(stadnaammarkt);
      

            handel.add(trade = new Handel(stad, handeltyp, hw, prijs));
        }
    }

    public Stad zoekstad(String naam) {
//        kijkt of de stad in de list zijn
        for (Stad p : steden) {
            if (p.getNaam().equals(naam)) {
                return p;
            }
        }
        throw new IllegalArgumentException("de stad van de mark niet gevonden");
    }
}
