package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public abstract class CustomList<T> extends ArrayList<T> {
    public List<T> getIntersections(List<T> list) {
        List<T> commonElements = new ArrayList<>();
        Comparator<T> elementComparator = this.comparator();

        int thisIndex = 0;
        int listIndex = 0;

        while (thisIndex < this.size() && listIndex < list.size()) {
            T thisElement = this.get(thisIndex);
            T listElement = list.get(listIndex);

            int comparison = elementComparator.compare(thisElement, listElement);

            if (comparison < 0) {
                thisIndex++;
            } else if(comparison > 0){
                listIndex++;
            }
            else{
                commonElements.add(thisElement);
                thisIndex++;
                listIndex++;
            }
        }

        return commonElements;
    }
    public CustomList() {
        super();
    }
    public CustomList(Collection<? extends T> c) {
        super(c);
        this.sort(comparator());
    }

    @Override
    public boolean add(T t) {
        super.add(t);
        super.sort(comparator());
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        super.addAll(c);
        super.sort(comparator());
        return true;
    }

    @Override
    public boolean remove(Object o) {
        super.remove(o);
        super.sort(comparator());
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        super.removeAll(c);
        super.sort(comparator());
        return true;
    }

    public abstract Comparator<T> comparator();
}
