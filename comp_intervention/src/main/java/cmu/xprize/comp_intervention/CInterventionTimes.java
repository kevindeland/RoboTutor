package cmu.xprize.comp_intervention;

import android.util.Log;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.HashMap;

/**
 * RoboTutor
 * <p>
 * Created by kevindeland on 9/13/19.
 */

public class CInterventionTimes {

    private static HashMap<String, Float> timesById;
    private static CInterventionTimes singleton;

    private static final String TAG = "TIMEDATA";

    /**
     * private constructor for singleton
     * @param filename location of file
     */
    private CInterventionTimes(String filename) {

        Log.i(TAG, "filename = " + filename);
        timesById = new HashMap<String, Float>();

        try {
            CSVReader reader = new CSVReader(new FileReader(filename));
            String[] nextLine;
            boolean skippedHeader = false;
            while ((nextLine = reader.readNext()) != null) {

                // skip the header
                if (!skippedHeader) {
                    skippedHeader = true;
                    continue;
                }
                // nextLine[] is an array of values
                try {
                    String id = nextLine[0];
                    float time = Float.parseFloat(nextLine[1]);

                    Log.v(TAG, "id = " + id + "; time = " + time);
                    timesById.put(id, time);
                } catch (Exception e) {
                    Log.v(TAG, "skipped this row");
                }
            }


        } catch (java.io.IOException e) {
            Log.e(TAG, "error = " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * getter for singleton
     * @param filename location of file
     * @return singleton
     */
    static public CInterventionTimes getTimesMap(String filename) {

        if(singleton == null) {
            singleton = new CInterventionTimes(filename);
        }

        return singleton;
    }

    /**
     * look up time by id
     * @param id tutor id
     * @return float
     */
    public float getTimeByTutorId(String id) {

        return timesById.get(id);
    }
}
