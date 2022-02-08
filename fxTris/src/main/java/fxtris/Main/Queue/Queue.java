package fxtris.Main.Queue;

import fxtris.Main.Minoes.Tetromino;
import fxtris.Main.Stages.GameStage;

import java.util.ArrayList;
import java.util.Random;

import static fxtris.Main.Others.GlobalValues.TILE;

/**
 * CLass that handles the queue
 */
public class Queue {

    private static final ArrayList <Tetromino> list = new ArrayList<>(7);
    private static Bag bag = new Bag();

    private static final Random random = new Random();

    public static ArrayList<Tetromino> getList() {
        return list;
    }

    /**
     * Removes the tetromino in position 0, picks a new one from the bag, and makes a new bag if needed
     */
    public static void cycleList() {
        list.remove(0);

        int selectedMino = random.nextInt(bag.getBag().size());
        list.add(bag.getBag().get(selectedMino));
        bag.getBag().remove(selectedMino);

        try {
            GameStage.root.getChildren().add(list.get(list.size() - 2).getMinoCentral());
            GameStage.root.getChildren().add(list.get(list.size() - 2).getMinoA());
            GameStage.root.getChildren().add(list.get(list.size() - 2).getMinoB());
            GameStage.root.getChildren().add(list.get(list.size() - 2).getMinoC());
        } catch (Exception ignored){}

        for (Tetromino i : list){
            i.getMinoCentral().setY(i.getMinoCentral().getY() - TILE * 3);
            if (i.isO()){
                i.getMinoCentral().setY(i.getMinoCentral().getY() - 5);
            }
            i.update();
        }

        if (bag.getBag().isEmpty()){
            bag = new Bag();
        }
    }

    /**
     * Loads 7 tetrominos into the list
     */
    public static void loadFirstQueue() {

        bag = new Bag();
        list.clear();

        for (int i = 0; i < 7; i++){
            int selectedMino = random.nextInt(bag.getBag().size());
            list.add(bag.getBag().get(selectedMino));
            bag.getBag().remove(selectedMino);
            for (Tetromino t : list){
                t.getMinoCentral().setY(t.getMinoCentral().getY() - TILE * 3);
                if (t.isO()){
                    t.getMinoCentral().setY(t.getMinoCentral().getY() - 5);
                }
                t.update();
            }

            try {
                GameStage.root.getChildren().add(list.get(list.size() - 2).getMinoCentral());
                GameStage.root.getChildren().add(list.get(list.size() - 2).getMinoA());
                GameStage.root.getChildren().add(list.get(list.size() - 2).getMinoB());
                GameStage.root.getChildren().add(list.get(list.size() - 2).getMinoC());
            } catch (Exception ignored){}
        }

        bag = new Bag();
    }
}
