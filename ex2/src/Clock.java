public abstract class Clock implements Advanceable<Integer> {

    protected int hour;

    Clock(int initialHour) {
        this.hour = initialHour;
    }

    public int getHour() {
        return this.hour;
    }
    public static Clock buildClock(int initialHour, final int totalHours) {
        if (totalHours == 12 || totalHours == 24) {
            return new Clock(initialHour) {
                @Override
                public void advance(Integer units) {
                    this.hour = ((initialHour + units) % totalHours);
                }
            };
        }
        return null;
    }

    public static void main(String[] args) {
        Clock clock12 = buildClock(10, 12);
        Clock clock24 = buildClock(10, 24);
        System.out.println(clock12.getHour()); // prints 10
        System.out.println(clock24.getHour()); // prints 10
        clock12.advance(5);
        clock24.advance(5);
        System.out.println(clock12.getHour()); // prints 3
        System.out.println(clock24.getHour()); // prints 15
        System.out.println("Hello World");
    }

//
//    @Override
//    public void advance(Integer units) {
//
//    }
}
