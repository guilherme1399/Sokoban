package pt.iscte.poo.sokobanstarter;


import java.util.List;

import pt.iscte.poo.utils.Point2D;

public interface Targets {
    public Point2D getPosition();
    boolean isCovered(List<GameElement> gameList);}
