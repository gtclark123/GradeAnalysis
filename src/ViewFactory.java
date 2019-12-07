public class ViewFactory {

    static public View[] createViews(Data data) {
        return new View[]{
                new DataView(data),
                new AnalysisView(data),
                new BarChartView(data),
                new ErrorView(data),
                new DISTView(data)
                // OtherView(data)
                // add a new view here...
        };
    }

}