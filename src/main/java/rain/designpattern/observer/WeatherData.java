package rain.designpattern.observer;

import java.util.Vector;

public class WeatherData implements Public {
    Vector<Subcribe> subcribes;

    public WeatherData() {
        subcribes = new Vector<>();
    }

    public void addSubcribe(Subcribe subcribe) {
        subcribes.add(subcribe);
    }

    public void deleteSubcribe(Subcribe subcribe) {
        subcribes.remove(subcribe);
    }

    public void notifySubcribes() {
        for (Subcribe subcribe : subcribes) {
            subcribe.update();
        }
    }


    public static void main(String[] args) {
        Public weatherData = new WeatherData();

        Subscribe1 subscribe1 = new Subscribe1();
        weatherData.addSubcribe(subscribe1);

        Subscribe2 subscribe2 = new Subscribe2();
        weatherData.addSubcribe(subscribe2);

        weatherData.notifySubcribes();
    }
}
