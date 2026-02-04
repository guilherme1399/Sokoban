package pt.iscte.poo.sokobanstarter;

public interface Removable {
    RemovableType getRemovableType();
    
    void destroy();
}