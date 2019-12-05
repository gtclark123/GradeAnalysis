public class ViewFactory {

    static public View[] createViews(Data data) {
        return new View[]{
            new DataView(data) //,
            // OtherView(data)
            // add a new view here...
        };
    }

}
