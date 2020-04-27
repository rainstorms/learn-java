package rain.designpattern.observer;

public interface Public {
    void addSubcribe(Subcribe subcribe);

    void deleteSubcribe(Subcribe subcribe);

    void notifySubcribes();
}
