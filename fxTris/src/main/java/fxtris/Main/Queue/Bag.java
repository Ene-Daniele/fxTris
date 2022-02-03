package fxtris.Main.Queue;

import fxtris.Main.Minoes.Tetromino;
import fxtris.Main.Minoes.Tetrominoes.*;

import java.util.ArrayList;

class Bag {

    private final ArrayList <Tetromino> bag;

    public Bag(){
        this.bag = new ArrayList<>();

        bag.add(new Z());
        bag.add(new J());
        bag.add(new L());
        bag.add(new O());
        bag.add(new S());
        bag.add(new T());
        bag.add(new I());
    }

    public ArrayList <Tetromino> getBag() {
        return bag;
    }
}
