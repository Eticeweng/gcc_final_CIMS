package Structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class SequentialList<T> {
    private T[] list;
    private int length = 0;
    final int MAX = 100;

    public SequentialList(T[] list) {
        this.list = (T[]) new Object[MAX];
        int j = 0;
        if (list != null){
            for (int i = 0; i < list.length; i++){
                if (list[i] != null){
                    this.list[i] = list[i];
                    j++;
                }
            }
        }
        this.length = j;
    }

    public int size(){
        return this.length;
    }

    public T get(int index){
        if (index < 0 || index > list.length){
            return null;
        }
        return list[index];
    }

    public boolean insert(int index, T element){
        if (index < 0 || index > length || length + 1 > MAX){
           return false;
        }
        for (int i = length; i >= index; i--){
            list[i + 1] = list[i];
        }
        list[index] = element;
        length++;
        return true;
    }

    public T remove(int index){
        if (index < 0 || index > length){
            return null;
        }
        T cache = list[index];
        for (int i = index; i <= length; i++){
            list[i] = list[i + 1];
        }
        length--;
        return cache;
    }

    public boolean add(T element){
        if (length + 1 > MAX){
            return false;
        }
        list[length++] = element;
        return true;
    }

    public boolean update(int index, T element){
        if (index < 0 || index > length){
            return false;
        }
        list[index] = element;
        return true;
    }

    public int indexOf(T element){
        if (this.length == 0){
            return -1;
        }
        for (int i = 0; i < length; i++){
            if (list[i].equals(element)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder(length);
        for (T element : list){
            if (element != null){
                out.append(element.toString()).append("\n");
            }
        }
        return out.toString();
    }
}
