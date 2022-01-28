package fxtris.Main.Queue;

import fxtris.Main.Minoes.Tetromino;

import java.util.ArrayList;
import java.util.Random;

public class Queue {

    private static ArrayList <Tetromino> list = new ArrayList <Tetromino> (7);
    private static Bag bag = new Bag();
    private static Random random = new Random();

    public static ArrayList<Tetromino> getList() {
        return list;
    }

    public static void cycleList(){
        list.remove(0);

        int selectedMino = random.nextInt(0, bag.getBag().size());
        list.add(bag.getBag().get(selectedMino));
        bag.getBag().remove(selectedMino);

        if (bag.getBag().isEmpty()){
            bag = new Bag();
        }
    }

    public static void loadFirstQueue(){

        for (int i = 0; i < 7; i++){
            int selectedMino = random.nextInt(0, bag.getBag().size());
            list.add(bag.getBag().get(selectedMino));
            bag.getBag().remove(selectedMino);
        }

        bag = new Bag();
    }
}
