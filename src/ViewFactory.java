public class ViewFactory {

    static public View[] createViews(Data data) {
        return new View[]{
                new DataView(data) //,
//                new DISTView(data)
                // add a new view here...
        };
    }

}

