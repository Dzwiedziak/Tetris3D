package Model;

import java.util.List;

public class Menu {
    List<String> options;
    int selected;
    public Menu(List<String> options){
        this.options = options;
        selected = 0;
    }

    public void nextOption(){
        selected = (selected + 1) % options.size();
    }
    public void previousOption(){
        selected = Math.floorMod(selected - 1, options.size());
    }
    public List<String> getOptions(){
        return options;
    }
    public int getSelected(){
        return selected;
    }
    public int getSize(){
        return options.size();
    }
    public String getIndex(int index){
        return options.get(index);
    }
    public boolean selectedEquals(String string){
        return options.get(selected).equals(string);
    }
}
