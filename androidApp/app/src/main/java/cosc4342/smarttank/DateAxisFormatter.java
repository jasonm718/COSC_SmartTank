package cosc4342.smarttank;


import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;


public class DateAxisFormatter implements IAxisValueFormatter {
    
    private List<String> dates;
    
    DateAxisFormatter(List<String> newDates){
        
        dates = newDates;
    }
    
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        
        //timestamp follows format of yyyy/dd
        String raw_timestamp = dates.get((int) value).split("\\w+-\\w+-\\w+", 2)[1];
        String timestamp;
        if(Integer.parseInt(raw_timestamp.substring(1,3)) > 12){
            timestamp = raw_timestamp + " pm";
        }else {
            timestamp = raw_timestamp + " am";
        }
        return timestamp;
    }
}
